package io.mkolodziejczyk92.views.address;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.router.*;
import io.mkolodziejczyk92.data.controllers.AddressesViewController;
import io.mkolodziejczyk92.data.entity.Address;
import io.mkolodziejczyk92.utils.ComponentFactory;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import static io.mkolodziejczyk92.utils.ComponentFactory.*;

@PageTitle("Addresses")
@Route(value = "addresses", layout = MainLayout.class)
@PermitAll
public class AddressesView extends Div implements HasUrlParameter<String> {

    private final AddressesViewController addressesViewController;
    private AddressFilter addressFilter = new AddressFilter();
    private AddressDataProvider addressDataProvider = new AddressDataProvider();
    private ConfigurableFilterDataProvider<Address, Void, AddressFilter> filterDataProvider
            = addressDataProvider.withConfigurableFilter();
    private Button newAddressButton = createStandardButton("New Address");

    private final Grid<Address> grid = new Grid<>();

    private String clientIdWithParameter;

    public AddressesView(AddressesViewController addressesViewController) {
        this.addressesViewController = addressesViewController;

        grid.addColumn(address -> address.getClient().getFullName()).setHeader("Client");
        grid.addColumn(Address::getStreet).setHeader("Street");
        grid.addColumn(Address::getHouseNumber).setHeader("House number");
        grid.addColumn(Address::getApartmentNumber).setHeader("apartmentNumber");
        grid.addColumn(Address::getZipCode).setHeader("Zip code");
        grid.addColumn(Address::getCity).setHeader("City");
        grid.addColumn(Address::getVoivodeship).setHeader("Voivodeship");
        grid.addColumn(Address::getPlotNumber).setHeader("Plot number");
        grid.getColumns().forEach(addressColumn -> addressColumn.setAutoWidth(true));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);


        GridContextMenu<Address> menu = grid.addContextMenu();
        menu.addItem("Edit", event -> {
            if(event.getItem().isPresent()){
                addressesViewController.editAddressInformation(event.getItem().get());
            }else menu.close();
        });
        menu.addItem("Delete", event -> {
        });

        add(createTopButtonLayout());
        add(createSearchLayout());
        add(grid);
    }

    private Component createSearchLayout() {
        HorizontalLayout searchLayout = new HorizontalLayout();
        TextField searchField = createTextFieldForSearchLayout("Search");
        searchField.addValueChangeListener(event -> {
            addressFilter.setSearchTerm(event.getValue());
            filterDataProvider.setFilter(addressFilter);
        });
        searchLayout.add(searchField);
        return searchLayout;
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = ComponentFactory.createTopButtonLayout();
        topButtonLayout.add(newAddressButton);
        newAddressButton.addClickListener(event -> addressesViewController.createNewAddressFormForClient(clientIdWithParameter));
        return topButtonLayout;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String urlParameter) {
        if(!urlParameter.isBlank()){
            String clientId = urlParameter.substring(1);
            clientIdWithParameter = urlParameter;
            grid.setItems(addressesViewController.clientAddresses(Long.valueOf(clientId)));
        } else {
            grid.setItems(filterDataProvider);
        }
    }
}

