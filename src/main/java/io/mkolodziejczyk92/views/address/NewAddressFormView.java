package io.mkolodziejczyk92.views.address;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.mkolodziejczyk92.data.entity.Address;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.enums.EAddressType;
import io.mkolodziejczyk92.data.enums.ECountry;
import io.mkolodziejczyk92.data.enums.EVoivodeship;
import io.mkolodziejczyk92.data.service.AddressService;
import io.mkolodziejczyk92.data.service.ClientService;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("New Address")
@Route(value = "new-address", layout = MainLayout.class)
@PermitAll
public class NewAddressFormView extends Div {

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

    private Binder<Address> binder = new Binder<>(Address.class);

    public NewAddressFormView(AddressService addressService) {
        addClassName("address-view");

        add(createFormLayout());
        add(createComboBox());
        add(createButtonLayout());
        binder.bindInstanceFields(this);

        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            addressService.update(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " stored.");
            clearForm();
        });
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        ComboBox.ItemFilter<Client> clientFilter = (clientDb, filterString) ->
                clientDb
                        .getFullName()
                        .toLowerCase()
                        .contains(filterString.toLowerCase());
        client.setItems(clientFilter, ClientService.allClients()); //SHOULD BE INJECTION BY CONSTRUCTOR
        client.setItemLabelGenerator(Client::getFullName);
        client.setMinWidth("350px");
        add(client);
        formLayout.add(street, houseNumber, apartmentNumber, zipCode, city, plotNumber, municipality);
//        houseNumber.setMaxWidth("150px");
//        houseNumber.getStyle().set("padding-right", "10px");
//        apartmentNumber.setMaxWidth("150px");
//        apartmentNumber.getStyle().set("padding-right", "300px");
//        zipCode.setMaxWidth("150px");
//        plotNumber.setMaxWidth("150px");
        return formLayout;
    }

    private Component createComboBox(){
        voivodeship.setItemLabelGenerator(EVoivodeship::getNameOfVoivodeship);
        voivodeship.setItems(EVoivodeship.values());
        add(voivodeship);
        voivodeship.getStyle().set("padding-right", "10px");
        country.setItemLabelGenerator(ECountry::getCountry);
        country.setItems(ECountry.values());
        add(country);
        country.getStyle().set("padding-right", "10px");
        addressType.setItemLabelGenerator(EAddressType::getType);
        addressType.setItems(EAddressType.values());
        add(addressType);
        return new FormLayout();
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

    private void clearForm() {
        this.binder.setBean(new Address());
    }

}
