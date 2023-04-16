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
    private ConfigurableFilterDataProvider<Address, Void, AddressFilter> filterDataProvider
            = addressDataProvider.withConfigurableFilter();
    private Button newAddressButton = new Button("Add new address");

    public AddressesView(AddressesViewController addressesViewController) {
        this.addressesViewController = addressesViewController;
        addressesViewController.initView(this);

        Grid<Address> grid = new Grid<>();
        grid.addColumn(address -> address.getClient().getFullName()).setHeader("Client");
        grid.addColumn(Address::getHouseNumber).setHeader("House number").setAutoWidth(true);
        grid.addColumn(Address::getApartmentNumber).setHeader("apartmentNumber").setAutoWidth(true);
        grid.addColumn(Address::getZipCode).setHeader("Zip code").setAutoWidth(true);
        grid.addColumn(Address::getCity).setHeader("City").setAutoWidth(true);
        grid.addColumn(Address::getVoivodeship).setHeader("Voivodeship").setAutoWidth(true);
        grid.addColumn(Address::getPlotNumber).setHeader("Plot number").setAutoWidth(true);
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
            addressFilter.setSearchTerm(e.getValue());
            filterDataProvider.setFilter(addressFilter);
        });
        searchLayout.add(searchField);
        return searchLayout;
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        topButtonLayout.add(newAddressButton);
        newAddressButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newAddressButton.getStyle().set("margin-left", "auto");
        newAddressButton
                .addClickListener(e -> UI.getCurrent().navigate(NewAddressFormView.class));
        topButtonLayout.getStyle().set("padding-right", "15px");
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");
        return topButtonLayout;
    }


}

