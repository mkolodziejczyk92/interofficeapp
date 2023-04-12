package io.mkolodziejczyk92.views.address;

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
import io.mkolodziejczyk92.data.controllers.AddressesViewController;
import io.mkolodziejczyk92.data.entity.Address;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("Addresses")
@Route(value = "address", layout = MainLayout.class)
@PermitAll
public class AddressesView extends Div {

    private final AddressesViewController addressesViewController;
    private AddressFilter addressFilter = new AddressFilter();

    private AddressDataProvider addressDataProvider = new AddressDataProvider();

    private ConfigurableFilterDataProvider<Address, Void, AddressFilter> filterDataProvider = addressDataProvider
            .withConfigurableFilter();
    Button newAddressButton = new Button("Add new address");

    public AddressesView(AddressesViewController addressesViewController) {
        this.addressesViewController = addressesViewController;
        addressesViewController.initView(this);

        Grid<Address> grid = new Grid<>();
        grid.addColumn(address -> address.getClient().getFullName(), "clientFullName").setHeader("Client");
        grid.addColumn(Address::getHouseNumber, "houseNumber").setHeader("House number");
        grid.addColumn(Address::getApartmentNumber, "apartmentNumber").setHeader("apartmentNumber");
        grid.addColumn(Address::getZipCode, "zipCode").setHeader("Zip code");
        grid.addColumn(Address::getCity, "city").setHeader("City");
        grid.addColumn(Address::getVoivodeship, "voivodeship").setHeader("Voivodeship");
        grid.addColumn(Address::getPlotNumber, "plotNumber").setHeader("Plot number");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setItems(filterDataProvider);

        GridContextMenu<Address> menu = grid.addContextMenu();
        menu.addItem("View", event -> {
        });
        menu.addItem("Edit", event -> {
        });
        menu.addItem("Delete", event -> {
        });

        add(createTopButtonLayout());
        add(grid);
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        topButtonLayout.addClassName("button-layout");

        TextField searchField = new TextField();
        searchField.getStyle().set("padding-left", "15px");
        searchField.setWidth("30%");
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> {
            addressFilter.setSearchTerm(e.getValue());
            filterDataProvider.setFilter(addressFilter);

        });

        newAddressButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newAddressButton.getStyle().set("margin-left", "auto");
        newAddressButton
                .addClickListener(e -> UI.getCurrent().navigate(NewAddressFormView.class));
        topButtonLayout.add(searchField, newAddressButton);

        return topButtonLayout;
    }



}

