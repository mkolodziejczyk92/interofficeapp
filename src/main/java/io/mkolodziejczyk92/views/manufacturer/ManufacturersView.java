package io.mkolodziejczyk92.views.manufacturer;

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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.mkolodziejczyk92.data.controllers.ManufacturersViewController;
import io.mkolodziejczyk92.data.entity.Manufacturer;
import io.mkolodziejczyk92.data.entity.Supplier;
import io.mkolodziejczyk92.utils.ComponentFactory;
import io.mkolodziejczyk92.views.MainLayout;
import io.mkolodziejczyk92.views.supplier.NewSupplierFormView;
import jakarta.annotation.security.PermitAll;

import static io.mkolodziejczyk92.utils.ComponentFactory.createStandardButton;
import static io.mkolodziejczyk92.utils.ComponentFactory.createTextFieldForSearchLayout;

@PageTitle("Manufacturers")
@Route(value = "manufacturers", layout = MainLayout.class)
@PermitAll
public class ManufacturersView extends Div {

    private final ManufacturersViewController manufacturersViewController;
    private final ManufacturerFilter manufacturerFilter = new ManufacturerFilter();
    private final ManufacturerDataProvider manufacturerDataProvider = new ManufacturerDataProvider();
    private final ConfigurableFilterDataProvider<Manufacturer, Void, ManufacturerFilter> filterDataProvider
            = manufacturerDataProvider.withConfigurableFilter();
    private final Button newManufacturerButton = createStandardButton("New Manufacturer");
    private Grid<Manufacturer> grid = new Grid<>(Manufacturer.class, false);


    public ManufacturersView(ManufacturersViewController manufacturersViewController) {
        this.manufacturersViewController = manufacturersViewController;


        grid.addColumn(Manufacturer::getNameOfCompany).setHeader("Name of company");
        grid.addColumn(Manufacturer::getNip).setHeader("NIP");
        grid.getColumns().forEach(supplierColumn -> supplierColumn.setAutoWidth(true));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setItems(filterDataProvider);


        GridContextMenu<Manufacturer> menu = grid.addContextMenu();
        menu.addItem("All purchases", event -> {
            if (event.getItem().isPresent()) {
                manufacturersViewController.showAllPurchasesForManufacturer(event.getItem().get());
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
            manufacturerFilter.setSearchTerm(e.getValue());
            filterDataProvider.setFilter(manufacturerFilter);
        });
        searchLayout.add(searchField);
        return searchLayout;
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = ComponentFactory.createTopButtonLayout();
        topButtonLayout.add(newManufacturerButton);
        newManufacturerButton.addClickListener(e -> UI.getCurrent().navigate(NewManufacturerFormView.class));
        return topButtonLayout;
    }

    private Dialog createDialogConfirmForDeleteSupplier(Manufacturer manufacturer) {
        Dialog dialog = new Dialog();
        dialog.add(String.format("Are you sure you want to delete this supplier: %s?", manufacturer.getNameOfCompany()));
        Button deleteButton = new Button("Delete", event ->
        {
            manufacturersViewController.deleteManufacturer(manufacturer);
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
