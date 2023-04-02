package io.mkolodziejczyk92.views.orders;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import io.mkolodziejczyk92.data.entity.Purchase;
import io.mkolodziejczyk92.data.enums.ECommodityType;
import io.mkolodziejczyk92.data.enums.EOrderStatus;
import io.mkolodziejczyk92.data.service.OrdersService;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Orders")
@Route(value = "orders", layout = MainLayout.class)
@PermitAll
public class OrdersView extends Div implements BeforeEnterObserver {

    private final String ORDERS_ID = "ordersID";
    private final String ORDERS_EDIT_ROUTE_TEMPLATE = "Orders/%s/edit";

    private final Grid<Purchase> grid = new Grid<>(Purchase.class, false);

    private ComboBox<ECommodityType> commodityType;
    private TextField netAmount;
    private TextField client;
    private TextField contractNumber;
    private ComboBox<EOrderStatus> status;
    private TextField supplier;
    private TextField supplierOrderNumber;
    private TextField comment;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<Purchase> binder;

    private Purchase purchase;

    private final OrdersService ordersService;

    public OrdersView(OrdersService ordersService) {
        this.ordersService = ordersService;
        addClassNames("orders-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("commodityType").setAutoWidth(true);
        grid.addColumn("netAmount").setAutoWidth(true);
        grid.addColumn("client").setAutoWidth(true);
        grid.addColumn("contractNumber").setAutoWidth(true);
        grid.addColumn("status").setAutoWidth(true);
        grid.addColumn("supplier").setAutoWidth(true);
        grid.addColumn("supplierOrderNumber").setAutoWidth(true);
        grid.addColumn("comment").setAutoWidth(true);
        grid.setItems(query -> ordersService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(ORDERS_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(OrdersView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Purchase.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(supplier)
                .bind(p -> p.getSupplier().getNameOfCompany(),
                (p, v) -> p.getSupplier().setNameOfCompany(v));
        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.purchase == null) {
                    this.purchase = new Purchase();
                }
                binder.writeBean(this.purchase);
                ordersService.update(this.purchase);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(OrdersView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> ordersId = event.getRouteParameters().get(ORDERS_ID).map(Long::parseLong);
        if (ordersId.isPresent()) {
            Optional<Purchase> ordersFromBackend = ordersService.get(ordersId.get());
            if (ordersFromBackend.isPresent()) {
                populateForm(ordersFromBackend.get());
            } else {
                Notification.show(String.format("The requested orders was not found, ID = %s", ordersId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(OrdersView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        commodityType = new ComboBox<>("Commodity Type");
        commodityType.setItemLabelGenerator(s->s.name());
        commodityType.setItems(ECommodityType.values());
        netAmount = new TextField("netAmount");
        client = new TextField("Client");
        contractNumber = new TextField("Contract Number");
        status = new ComboBox<>("Status");
        status.setItemLabelGenerator(s->s.name());
        status.setItems(EOrderStatus.values());
        supplier = new TextField("Supplier");
        supplierOrderNumber = new TextField("Supplier Order Number");
        comment = new TextField("Comment");
        formLayout.add(commodityType, netAmount, client, contractNumber, status, supplier, supplierOrderNumber, comment);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Purchase value) {
        this.purchase = value;
        binder.readBean(this.purchase);

    }
}
