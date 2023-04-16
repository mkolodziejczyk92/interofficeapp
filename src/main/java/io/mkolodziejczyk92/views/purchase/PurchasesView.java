package io.mkolodziejczyk92.views.purchase;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
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
    private Button emptyButton = new Button("EMPTY");

    public PurchasesView(PurchasesViewController purchasesViewController) {
        this.purchasesViewController = purchasesViewController;
        purchasesViewController.initView(this);

        Grid<Purchase> grid = new Grid<>(Purchase.class, false);
        grid.addColumn(purchase -> purchase.getClient().getFullName()).setHeader("Client");
        grid.addColumn(Purchase::getContractNumber).setHeader("Contract number");
        grid.addColumn(purchase -> purchase.getSupplier().getNameOfCompany()).setHeader("Supplier");
        grid.addColumn(Purchase::getNetAmount).setHeader("Net amount");
        grid.addColumn(Purchase::getCommodityType).setHeader("Commodity type");
        grid.addColumn(Purchase::getStatus).setHeader("Status");
        grid.addColumn(Purchase::getSupplierPurchaseNumber).setHeader("Supplier purchase number");
        grid.addColumn(Purchase::getComment).setHeader("Comment");
        grid.getColumns().forEach(purchaseColumn -> purchaseColumn.setAutoWidth(true));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setItems(filterDataProvider);

        GridContextMenu<Purchase> menu = grid.addContextMenu();
        menu.addItem("View", event -> {
        });
        menu.addItem("Edit", event -> {
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
        topButtonLayout.add(emptyButton);
        emptyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        emptyButton.getStyle().set("margin-left", "auto");
        topButtonLayout.getStyle().set("padding-right", "15px");
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");
        return topButtonLayout;
    }


}
