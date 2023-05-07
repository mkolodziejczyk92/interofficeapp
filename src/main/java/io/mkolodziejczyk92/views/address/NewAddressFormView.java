package io.mkolodziejczyk92.views.address;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import io.mkolodziejczyk92.data.controllers.AddressFormViewController;
import io.mkolodziejczyk92.data.controllers.ClientsViewController;
import io.mkolodziejczyk92.data.entity.Address;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.enums.EAddressType;
import io.mkolodziejczyk92.data.enums.ECountry;
import io.mkolodziejczyk92.data.enums.EVoivodeship;
import io.mkolodziejczyk92.utils.ComponentFactory;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("New Address")
@Route(value = "newAddress", layout = MainLayout.class)
@PermitAll
public class NewAddressFormView extends Div implements HasUrlParameter<String> {

    private final AddressFormViewController addressFormViewController;
    private final ClientsViewController clientsViewController;
    private TextField street = new TextField("Street");
    private TextField houseNumber = new TextField("House number");
    private TextField apartmentNumber = new TextField("Apartment number");
    private TextField zipCode = new TextField("Zip code");
    private TextField city = new TextField("City");

    private TextField plotNumber = new TextField("Plot number");
    private TextField municipality = new TextField("Municipality");

    private ComboBox<EVoivodeship> voivodeship = new ComboBox<>("Voivodeship");

    private ComboBox<ECountry> country = new ComboBox<>("Country");
    private ComboBox<EAddressType> addressType = new ComboBox<>("Address type");

    private ComboBox<Client> client = new ComboBox<>("Client");

    private Button cancel = ComponentFactory.createCancelButton();
    private Button save = ComponentFactory.createSaveButton();
    private Button back = ComponentFactory.createBackButton();

    private Binder<Address> binder = new Binder<>(Address.class);

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String clientId) {
        if (!clientId.isEmpty()) {
            client.setValue(clientsViewController.findClientById(Long.valueOf(clientId)));
        }
    }

    public NewAddressFormView(AddressFormViewController addressFormViewController,
                              ClientsViewController clientsViewController) {
        this.addressFormViewController = addressFormViewController;
        this.clientsViewController = clientsViewController;
        addressFormViewController.initBinder(binder);

        add(createTopButtonLayout());
        createTopComboBox();
        add(createFormLayout());
        createBottomComboBox();
        add(createBottomButtonLayout());
        binder.bindInstanceFields(this);

        addressFormViewController.clearForm();
    }

    private Component createFormLayout() {
        return ComponentFactory.createFormLayout(street, houseNumber, apartmentNumber,
                zipCode,city,plotNumber,municipality);
    }

    private void createTopComboBox() {
        client.setItems(clientsViewController.allClients());
        client.setItemLabelGenerator(Client::getFullName);
        client.setMinWidth("350px");
        add(client);
        client.getStyle().set("padding-left", "30px");
    }

    private void createBottomComboBox() {
        voivodeship.setItemLabelGenerator(EVoivodeship::getNameOfVoivodeship);
        voivodeship.setItems(EVoivodeship.values());
        voivodeship.getStyle().set("padding-left", "30px");
        add(voivodeship);
        voivodeship.getStyle().set("padding-right", "10px");
        country.setItemLabelGenerator(ECountry::getCountry);
        country.setItems(ECountry.values());
        add(country);
        country.getStyle().set("padding-right", "10px");
        addressType.setItemLabelGenerator(EAddressType::getType);
        addressType.setItems(EAddressType.values());
        add(addressType);
    }


    private Component createBottomButtonLayout() {
        HorizontalLayout bottomButtonLayout = ComponentFactory.createBottomButtonLayout();

        bottomButtonLayout.add(cancel, save);

        cancel.addClickListener(event -> addressFormViewController.clearForm());
        save.addClickListener(event -> addressFormViewController.saveNewAddress(binder.getBean()));

        return bottomButtonLayout;
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = ComponentFactory.createTopButtonLayout();
        back.addClickListener(e -> UI.getCurrent().navigate(AddressesView.class));
        topButtonLayout.add(back);
        return topButtonLayout;
    }

}
