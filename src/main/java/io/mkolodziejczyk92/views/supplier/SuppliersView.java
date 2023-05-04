package io.mkolodziejczyk92.views.supplier;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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
import io.mkolodziejczyk92.data.controllers.SuppliersViewController;
import io.mkolodziejczyk92.data.entity.Supplier;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;


@PageTitle("Suppliers")
@Route(value = "suppliers", layout = MainLayout.class)
@PermitAll
public class SuppliersView extends Div {

    private final SuppliersViewController suppliersViewController;
    private SupplierFilter supplierFilter = new SupplierFilter();
    private SupplierDataProvider supplierDataProvider = new SupplierDataProvider();
    private ConfigurableFilterDataProvider<Supplier, Void, SupplierFilter> filterDataProvider
            = supplierDataProvider.withConfigurableFilter();
    private Button newSupplierButton = new Button("New Supplier");


    public SuppliersView(SuppliersViewController suppliersViewController) {
        this.suppliersViewController = suppliersViewController;
        suppliersViewController.initView(this);

        Grid<Supplier> grid = new Grid<>(Supplier.class, false);
        grid.addColumn(Supplier::getNameOfCompany).setHeader("Name of company");
        grid.addColumn(Supplier::getNip).setHeader("NIP");
        grid.getColumns().forEach(supplierColumn -> supplierColumn.setAutoWidth(true));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setItems(filterDataProvider);


        GridContextMenu<Supplier> menu = grid.addContextMenu();
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
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> {
            supplierFilter.setSearchTerm(e.getValue());
            filterDataProvider.setFilter(supplierFilter);
        });
        searchLayout.add(searchField);
        return searchLayout;
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        topButtonLayout.add(newSupplierButton);
        topButtonLayout.getStyle().set("padding-right", "15px");
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");
        newSupplierButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newSupplierButton.getStyle().set("margin-left", "auto");
        newSupplierButton.addClickListener(e -> UI.getCurrent().navigate(NewSupplierFormView.class));
        return topButtonLayout;
    }

}
