package io.mkolodziejczyk92.views.clients;

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
import com.vaadin.flow.router.RouteAlias;
import io.mkolodziejczyk92.data.controllers.ClientsViewController;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.views.MainLayout;
import io.mkolodziejczyk92.views.supplier.NewSupplierFormView;
import jakarta.annotation.security.PermitAll;

@PageTitle("Clients")
@Route(value = "clients", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class ClientsView extends Div {

    private final ClientsViewController clientsViewController;
    private ClientFilter clientFilter = new ClientFilter();
    private ClientDataProvider dataProvider = new ClientDataProvider();
    private ConfigurableFilterDataProvider<Client, Void, ClientFilter> filterDataProvider = dataProvider
            .withConfigurableFilter();


    private final Button newClientButton = new Button("Add new client");


    public ClientsView(ClientsViewController clientsViewController) {
        this.clientsViewController = clientsViewController;
        clientsViewController.initView(this);


        Grid<Client> grid = new Grid<>();
        grid.addColumn(Client::getFullName, "fullName").setHeader("Full Name");
        grid.addColumn(Client::getPhoneNumber, "phoneNumber").setHeader("Phone number");
        grid.addColumn(Client::getNip, "nip").setHeader("NIP");
        grid.addColumn(Client::getEmail, "email").setHeader("Email");
        grid.setItems(filterDataProvider);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);


        GridContextMenu<Client> menu = grid.addContextMenu();
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

    private Component createSearchLayout(){
        HorizontalLayout searchLayout = new HorizontalLayout();

        TextField searchField = new TextField();
        searchField.getStyle().set("padding-left", "15px");
        searchField.setWidth("30%");
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> {
            clientFilter.setSearchTerm(e.getValue());
            filterDataProvider.setFilter(clientFilter);
        });
        searchLayout.add(searchField);
        searchLayout.getStyle().set("padding-right", "15px");
        return searchLayout;
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        topButtonLayout.add(newClientButton);
        topButtonLayout.getStyle().set("padding-right", "15px");
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");
        newClientButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newClientButton.getStyle().set("margin-left", "auto");
        newClientButton.addClickListener(e -> UI.getCurrent().navigate(NewClientFormView.class));
        return topButtonLayout;
    }

}
