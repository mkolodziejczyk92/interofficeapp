package io.mkolodziejczyk92.views.address;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.*;
import io.mkolodziejczyk92.data.controllers.AddressesViewController;
import io.mkolodziejczyk92.data.entity.Address;
import io.mkolodziejczyk92.views.MainLayout;
import io.mkolodziejczyk92.views.client.ClientsView;
import jakarta.annotation.security.PermitAll;

@PageTitle("Client Addresses")
@Route(value = "clientAddresses", layout = MainLayout.class)
@PermitAll
public class ClientAddressesView extends Div implements HasUrlParameter<String> {

    private final AddressesViewController addressesViewController;
    private Long clientId;

    private Button newAddressButton = new Button("Add new address");
    private Button back = new Button("Back");
    private Grid<Address> grid = new Grid<>();

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String clientId) {
        this.clientId = Long.valueOf(clientId);
        grid.setItems(addressesViewController.clientAddresses(Long.valueOf(clientId)));
    }

    public ClientAddressesView(AddressesViewController addressesViewController) {
        this.addressesViewController = addressesViewController;


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
        });
        menu.addItem("Delete", event -> {
        });

        add(createTopButtonLayout());
        add(grid);
    }


    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        newAddressButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newAddressButton.getStyle().set("margin-left", "auto");
        newAddressButton
                .addClickListener(event -> addressesViewController.createNewAddressFormForClient(clientId));
        back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        back.addClickListener(event -> UI.getCurrent().navigate(ClientsView.class));
        back.getStyle().set("margin-left", "auto");
        topButtonLayout.getStyle().set("padding-right", "15px");
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");
        topButtonLayout.add(newAddressButton, back);
        return topButtonLayout;
    }
}
