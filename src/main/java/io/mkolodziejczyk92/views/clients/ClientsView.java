package io.mkolodziejczyk92.views.clients;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("Clients")
@Route(value = "clients", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class ClientsView extends Div {


    private PersonFilter personFilter = new PersonFilter();

    private PersonDataProvider dataProvider = new PersonDataProvider();

    private ConfigurableFilterDataProvider<Client, Void, PersonFilter> filterDataProvider = dataProvider
            .withConfigurableFilter();

    public ClientsView() {
        Grid<Client> grid = new Grid<>();
        grid.addColumn(Client::getFullName, "fullName").setHeader("Full Name");
        grid.addColumn(Client::getPhoneNumber, "phoneNumber").setHeader("Phone number");
        grid.addColumn(Client::getNip, "nip").setHeader("NIP");
        grid.addColumn(Client::getEmail, "email").setHeader("Email");
        grid.setItems(filterDataProvider);


        TextField searchField = new TextField();
        searchField.setWidth("50%");
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> {
            personFilter.setSearchTerm(e.getValue());
            filterDataProvider.setFilter(personFilter);
      
        });
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        VerticalLayout layout = new VerticalLayout(searchField, grid);
        layout.setPadding(false);
        add(layout);
    }

}
