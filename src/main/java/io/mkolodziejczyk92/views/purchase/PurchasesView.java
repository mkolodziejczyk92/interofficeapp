package io.mkolodziejczyk92.views.purchase;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.mkolodziejczyk92.data.controllers.PurchasesViewController;
import io.mkolodziejczyk92.data.entity.Purchase;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("Purchases")
@Route(value = "purchase", layout = MainLayout.class)
@PermitAll
public class PurchasesView extends Div {

    private final PurchasesViewController purchasesViewController;
    private PurchaseFilter purchaseFilter = new PurchaseFilter();
    private PurchaseDataProvider purchaseDataProvider = new PurchaseDataProvider();
    private ConfigurableFilterDataProvider<Purchase, Void, PurchaseFilter> filterDataProvider
            = purchaseDataProvider.withConfigurableFilter();
    private Button newPurchase = new Button("New Purchase");

    public PurchasesView(PurchasesViewController purchasesViewController) {
        this.purchasesViewController = purchasesViewController;
        purchasesViewController.initView(this);

        Grid<Purchase> grid = new Grid<>(Purchase.class, false);
        grid.addColumn(purchase -> purchase.getClient().getFullName()).setHeader("Client");
        grid.addColumn(Purchase::getContractNumber).setHeader("Contract number");
        grid.addColumn(purchase -> purchase.getSupplier().getNameOfCompany()).setHeader("Supplier");
        grid.addColumn(Purchase::getNetAmount).setHeader("Net amount");
        grid.addColumn(purchase -> purchase.getCommodityType().getName()).setHeader("Commodity type");
        grid.addColumn(purchase -> purchase.getStatus().getStatusName()).setHeader("Status");
        grid.addColumn(Purchase::getSupplierPurchaseNumber).setHeader("Supplier purchase number");
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, purchase) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_PRIMARY);
                    button.addClickListener(e -> {
                        Dialog commentDialog = createCommentDialogLayout(purchase.getComment());
                        add(commentDialog);
                        commentDialog.open();
                    });
                    button.setIcon(new Icon(VaadinIcon.COMMENT));
                })).setHeader("Comment");
        grid.getColumns().forEach(purchaseColumn -> purchaseColumn.setAutoWidth(true));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setItems(filterDataProvider);

        GridContextMenu<Purchase> menu = grid.addContextMenu();
        menu.addItem("View", event -> {
        });
        menu.addItem("Edit", event -> {
            if(event.getItem().isPresent()){
                purchasesViewController.editPurchaseInformation(event.getItem().get().getId());
            } else menu.close();
        });
        menu.addItem("Delete", event -> {
        });

        add(createTopButtonLayout());
        add(createSearchLayout());
        add(grid);
    }

    private Component createSearchLayout() {
        HorizontalLayout searchLayout = new HorizontalLayout();
        searchLayout.addClassName("button-layout");

        TextField searchField = new TextField();
        searchField.getStyle().set("padding-left", "15px");
        searchField.setWidth("30%");
        searchField.setPlaceholder("Search by client, contract number, purchase number or supplier");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> {
            purchaseFilter.setSearchTerm(e.getValue());
            filterDataProvider.setFilter(purchaseFilter);
        });
        searchLayout.add(searchField);
        return searchLayout;
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        topButtonLayout.add(newPurchase);
        newPurchase.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newPurchase.getStyle().set("margin-left", "auto");
        newPurchase.addClickListener( e -> UI.getCurrent().navigate(NewPurchaseFormView.class));
        topButtonLayout.getStyle().set("padding-right", "15px");
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");
        return topButtonLayout;
    }

    private Dialog createCommentDialogLayout(String comment) {
        Dialog dialog = new Dialog();
        dialog.setModal(false);
        dialog.setDraggable(true);

        dialog.setHeaderTitle("Comment");
        dialog.add(comment);

        Button cancelButton = new Button("Close", event -> dialog.close());
        cancelButton.addClickShortcut(Key.ESCAPE);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.getFooter().add(cancelButton);
        return dialog;
    }


}
