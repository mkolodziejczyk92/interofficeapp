package io.mkolodziejczyk92.views.client;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import io.mkolodziejczyk92.data.controllers.ClientsViewController;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.utils.ComponentFactory;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import static io.mkolodziejczyk92.utils.ComponentFactory.*;

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
    private final Button newClientButton = createStandardButton("New Client");


    public ClientsView(ClientsViewController clientsViewController) {
        this.clientsViewController = clientsViewController;

        Grid<Client> grid = new Grid<>();
        grid.addColumn(Client::getFullName).setHeader("Full Name");
        grid.addColumn(Client::getPhoneNumber).setHeader("Phone number");
        grid.addColumn(Client::getNip).setHeader("NIP");
        grid.addColumn(Client::getEmail).setHeader("Email");
        grid.addComponentColumn(client -> {
            Icon icon;
            if (client.isOfficeClient()) {
                icon = VaadinIcon.CHECK_CIRCLE.create();
                icon.setColor("green");
            } else {
                icon = VaadinIcon.CLOSE_CIRCLE.create();
                icon.setColor("red");
            }
            return icon;
        }).setHeader("Office Client").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(client -> client.getAddedBy().getFullName()).setHeader("Added By");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.getColumns().forEach(clientColumn -> clientColumn.setAutoWidth(true));
        grid.setItems(filterDataProvider);

        GridContextMenu<Client> menu = grid.addContextMenu();
        menu.addItem("Addresses", event -> {
            if (event.getItem().isPresent()) {
                clientsViewController
                        .clientAddresses(event.getItem().get().getId());
            } else menu.close();
        });

        menu.addItem("Purchases", event -> {
            if (event.getItem().isPresent()) {
                clientsViewController
                        .clientPurchases(event.getItem().get().getId());
            } else menu.close();
        });
        menu.addItem("Invoices", event ->{
            if(event.getItem().isPresent()){
                clientsViewController
                        .clientInvoices(event.getItem().get().getId());
            }else menu.close();
        });
        menu.addItem("Contracts", event ->{
            if(event.getItem().isPresent()){
                clientsViewController
                        .clientContracts(event.getItem().get().getId());
            }else menu.close();
        });
        menu.add(new Hr());
        menu.addItem("Edit", event -> {
            if (event.getItem().isPresent()) {
                clientsViewController.editClientInformationForm(event.getItem().get());
            } else menu.close();
        });

        menu.addItem("Delete", event -> {
            if (event.getItem().isPresent()) {
                Dialog confirmDialog = createDialogConfirmForDeleteClient(event.getItem().get());
                add(confirmDialog);
                confirmDialog.open();
            } else menu.close();
        });

        add(createTopButtonLayout());
        add(createSearchLayout());
        add(grid);
    }

    private Component createSearchLayout() {
        HorizontalLayout searchLayout = new HorizontalLayout();
        TextField searchField = createTextFieldForSearchLayout("Search");
        searchField.addValueChangeListener(e -> {
            clientFilter.setSearchTerm(e.getValue());
            filterDataProvider.setFilter(clientFilter);
        });
        searchLayout.add(searchField);
        return searchLayout;
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = ComponentFactory.createTopButtonLayout();
        topButtonLayout.add(newClientButton);
        newClientButton.addClickListener(e -> UI.getCurrent().navigate(NewClientFormView.class));
        return topButtonLayout;
    }

    private Dialog createDialogConfirmForDeleteClient(Client client) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(
                String.format("Delete client %s", client.getFullName()));
        dialog.add("Are you sure you want to delete this client?");

        Button deleteButton = new Button("Delete", event ->
        {
            clientsViewController.deleteClient(client);
            dialog.close();
        });
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_PRIMARY);
        deleteButton.getStyle().set("margin-right", "auto");
        dialog.getFooter().add(deleteButton);

        Button cancelButton = new Button("Cancel", event -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.getFooter().add(cancelButton);
        return dialog;
    }

}

