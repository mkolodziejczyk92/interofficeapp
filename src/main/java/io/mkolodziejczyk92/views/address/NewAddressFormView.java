package io.mkolodziejczyk92.views.address;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
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

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    private Button back = new Button("Back");

    private Binder<Address> binder = new Binder<>(Address.class);

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String clientId) {
        if (!clientId.isEmpty()){
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
        FormLayout formLayout = new FormLayout();
        formLayout.getStyle().set("padding-left", "30px");
        formLayout.getStyle().set("padding-right", "30px");
        formLayout.add(street, houseNumber, apartmentNumber, zipCode, city, plotNumber, municipality);
        return formLayout;
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
        HorizontalLayout bottomButtonLayout = new HorizontalLayout();
        bottomButtonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        bottomButtonLayout.getStyle().set("padding-left", "30px");
        bottomButtonLayout.getStyle().set("padding-top", "30px");
        bottomButtonLayout.add(save);
        bottomButtonLayout.add(cancel);

        cancel.addClickListener(event -> addressFormViewController.clearForm());
        save.addClickListener(event -> {
            addressFormViewController.saveNewAddress(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " stored.");
            addressFormViewController.clearForm();
        });
        save.addClickShortcut(Key.ENTER);
        return bottomButtonLayout;
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        topButtonLayout.getStyle().set("padding-right", "15px");
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");

        back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        back.addClickListener(e -> UI.getCurrent().navigate(AddressesView.class));
        back.getStyle().set("margin-left", "auto");
        back.addClickShortcut(Key.ESCAPE);
        topButtonLayout.add(back);
        return topButtonLayout;
    }

}
