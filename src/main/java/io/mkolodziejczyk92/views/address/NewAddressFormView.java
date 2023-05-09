package io.mkolodziejczyk92.views.address;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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

import static io.mkolodziejczyk92.utils.ComponentFactory.PARAMETER_FOR_CLIENT_ID_FROM_GRID;

@PageTitle("New Address")
@Route(value = "new-address", layout = MainLayout.class)
@PermitAll
public class NewAddressFormView extends Div implements HasUrlParameter<String> {

    private final AddressFormViewController addressFormViewController;
    private final ClientsViewController clientsViewController;
    private final TextField street = new TextField("Street");
    private final TextField houseNumber = new TextField("House number");
    private final TextField apartmentNumber = new TextField("Apartment number");
    private final TextField zipCode = new TextField("Zip code");
    private final TextField city = new TextField("City");
    private final TextField plotNumber = new TextField("Plot number");
    private final TextField municipality = new TextField("Municipality");
    private final ComboBox<EVoivodeship> voivodeship = new ComboBox<>("Voivodeship");
    private final ComboBox<ECountry> country = new ComboBox<>("Country");
    private final ComboBox<EAddressType> addressType = new ComboBox<>("Address type");
    private final ComboBox<Client> client = new ComboBox<>("Client");
    private final Button cancel = ComponentFactory.createCancelButton();
    private final Button save = ComponentFactory.createSaveButton();
    private final Button back = ComponentFactory.createBackButton();

    private final Button update = ComponentFactory.createUpdateButton();
    private final Binder<Address> binder = new Binder<>(Address.class);

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

        bottomButtonLayout.add(cancel, save , update);

        cancel.addClickListener(event -> addressFormViewController.clearForm());
        save.addClickListener(event -> addressFormViewController.saveNewAddress(binder.getBean()));
        update.addClickListener(event -> addressFormViewController.updateAddress(binder.getBean()));
        update.setVisible(false);
        return bottomButtonLayout;
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = ComponentFactory.createTopButtonLayout();
        back.addClickListener(e -> UI.getCurrent().navigate(AddressesView.class));
        topButtonLayout.add(back);
        return topButtonLayout;
    }

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String urlWithClientId) {
        if (!urlWithClientId.isBlank() && !urlWithClientId.startsWith(PARAMETER_FOR_CLIENT_ID_FROM_GRID) ) {
            binder.setBean(addressFormViewController.findAddressById(Long.valueOf(urlWithClientId)));

            cancel.setVisible(false);
            save.setVisible(false);
            update.setVisible(true);

        } else if (!urlWithClientId.isBlank()) {
            String clientId = urlWithClientId.substring(1);
            client.setValue(clientsViewController.findClientById(Long.valueOf(clientId)));
        }

    }

}
