package io.mkolodziejczyk92.views.supplier;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.router.*;
import io.mkolodziejczyk92.data.controllers.SuppliersViewController;
import io.mkolodziejczyk92.data.entity.Supplier;
import io.mkolodziejczyk92.data.entity.User;
import io.mkolodziejczyk92.utils.ComponentFactory;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import static io.mkolodziejczyk92.utils.ComponentFactory.*;


@PageTitle("Suppliers")
@Route(value = "suppliers", layout = MainLayout.class)
@PermitAll
public class SuppliersView extends Div {

    private final SuppliersViewController suppliersViewController;
    private final SupplierFilter supplierFilter = new SupplierFilter();
    private final SupplierDataProvider supplierDataProvider = new SupplierDataProvider();
    private final ConfigurableFilterDataProvider<Supplier, Void, SupplierFilter> filterDataProvider
            = supplierDataProvider.withConfigurableFilter();
    private final Button newSupplierButton = createStandardButton("New Supplier");
    private Grid<Supplier> grid = new Grid<>(Supplier.class, false);



    public SuppliersView(SuppliersViewController suppliersViewController) {
        this.suppliersViewController = suppliersViewController;

        grid.addColumn(Supplier::getNameOfCompany).setHeader("Name of company");
        grid.addColumn(Supplier::getNip).setHeader("NIP");
        grid.getColumns().forEach(supplierColumn -> supplierColumn.setAutoWidth(true));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setItems(filterDataProvider);


        GridContextMenu<Supplier> menu = grid.addContextMenu();
        menu.addItem("All purchases", event -> {
            if(event.getItem().isPresent()){
                suppliersViewController.showAllPurchasesForSupplier(event.getItem().get());
            } else {
                menu.close();
            }
        });
        menu.addItem("Delete", event -> {
            if (event.getItem().isPresent()) {
                Dialog confirmDialog = createDialogConfirmForDeleteSupplier(event.getItem().get());
                add(confirmDialog);
                confirmDialog.open();
            } else {
                menu.close();
            }
        });

        add(createTopButtonLayout());
        add(createSearchLayout());
        add(grid);
    }

    private Component createSearchLayout() {
        HorizontalLayout searchLayout = new HorizontalLayout();

        TextField searchField = createTextFieldForSearchLayout("Search");
        searchField.addValueChangeListener(e -> {
            supplierFilter.setSearchTerm(e.getValue());
            filterDataProvider.setFilter(supplierFilter);
        });
        searchLayout.add(searchField);
        return searchLayout;
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = ComponentFactory.createTopButtonLayout();
        topButtonLayout.add(newSupplierButton);
        newSupplierButton.addClickListener(e -> UI.getCurrent().navigate(NewSupplierFormView.class));
        return topButtonLayout;
    }

    private Dialog createDialogConfirmForDeleteSupplier(Supplier supplier) {
        Dialog dialog = new Dialog();
        dialog.add(String.format("Are you sure you want to delete this supplier: %s?", supplier.getNameOfCompany()));
        Button deleteButton = new Button("Delete", event ->
        {
            suppliersViewController.deleteSupplier(supplier);
            dialog.close();
        });
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.getStyle().set("margin-right", "auto");
        dialog.getFooter().add(deleteButton);

        Button cancelButton = new Button("Cancel", event -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        dialog.getFooter().add(cancelButton);
        return dialog;
    }

}
