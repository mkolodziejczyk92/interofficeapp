package io.mkolodziejczyk92.views.invoice;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import io.mkolodziejczyk92.data.entity.Invoice;
import io.mkolodziejczyk92.data.service.InvoiceService;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Invoice")
@Route(value = "Invoice/:invoiceID?/:action?(edit)", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class InvoiceView extends Div implements BeforeEnterObserver {

    private final String INVOICE_ID = "invoiceID";
    private final String INVOICE_EDIT_ROUTE_TEMPLATE = "Invoice/%s/edit";

    private final Grid<Invoice> grid = new Grid<>(Invoice.class, false);

    private TextField number;
    private TextField amount;
    private TextField contractNumber;
    private TextField client;
    private TextField issueDate;
    private TextField paymentTime;
    private TextField type;
    private Checkbox paid;
    private TextField paymentMethod;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<Invoice> binder;

    private Invoice invoice;

    private final InvoiceService invoiceService;

    public InvoiceView(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
        addClassNames("invoice-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("number").setAutoWidth(true);
        grid.addColumn("amount").setAutoWidth(true);
        grid.addColumn("contractNumber").setAutoWidth(true);
        grid.addColumn("client").setAutoWidth(true);
        grid.addColumn("issueDate").setAutoWidth(true);
        grid.addColumn("paymentTime").setAutoWidth(true);
        grid.addColumn("type").setAutoWidth(true);
        LitRenderer<Invoice> paidRenderer = LitRenderer.<Invoice>of(
                "<vaadin-icon icon='vaadin:${item.icon}' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: ${item.color};'></vaadin-icon>")
                .withProperty("icon", paid -> paid.isPaid() ? "check" : "minus").withProperty("color",
                        paid -> paid.isPaid() ? "var(--lumo-primary-text-color)" : "var(--lumo-disabled-text-color)");

        grid.addColumn(paidRenderer).setHeader("Paid").setAutoWidth(true);

        grid.addColumn("paymentMethod").setAutoWidth(true);
        grid.setItems(query -> invoiceService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(INVOICE_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(InvoiceView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Invoice.class);

        // Bind fields. This is where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.invoice == null) {
                    this.invoice = new Invoice();
                }
                binder.writeBean(this.invoice);
                invoiceService.update(this.invoice);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(InvoiceView.class);
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
        Optional<Long> invoiceId = event.getRouteParameters().get(INVOICE_ID).map(Long::parseLong);
        if (invoiceId.isPresent()) {
            Optional<Invoice> invoiceFromBackend = invoiceService.get(invoiceId.get());
            if (invoiceFromBackend.isPresent()) {
                populateForm(invoiceFromBackend.get());
            } else {
                Notification.show(String.format("The requested invoice was not found, ID = %s", invoiceId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(InvoiceView.class);
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
        number = new TextField("Number");
        amount = new TextField("Amount");
        contractNumber = new TextField("Contract Number");
        client = new TextField("Client");
        issueDate = new TextField("Issue Date");
        paymentTime = new TextField("Payment Time");
        type = new TextField("Type");
        paid = new Checkbox("Paid");
        paymentMethod = new TextField("Payment Method");
        formLayout.add(number, amount, contractNumber, client, issueDate, paymentTime, type, paid, paymentMethod);

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

    private void populateForm(Invoice value) {
        this.invoice = value;
        binder.readBean(this.invoice);

    }
}
