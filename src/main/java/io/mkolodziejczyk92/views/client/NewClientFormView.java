package io.mkolodziejczyk92.views.client;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
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
import io.mkolodziejczyk92.data.controllers.ClientAddFormViewController;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.enums.EClientType;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("New Client")
@Route(value = "newClient", layout = MainLayout.class)
@PermitAll
public class NewClientFormView extends Div implements HasUrlParameter<String> {

    private final ClientAddFormViewController clientFormViewController;

    private final TextField firstName = new TextField("First Name");

    private final TextField lastName = new TextField("Last Name");
    private final TextField phoneNumber = new TextField("Phone Number");
    private final TextField email = new TextField("Email");
    private final TextField nip = new TextField("NIP");
    private final ComboBox<EClientType> clientType = new ComboBox<>("Client Type");
    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");
    private final Button back = new Button("Back");

    private final Button update = new Button("Update");
    private final Binder<Client> binder = new Binder<>(Client.class);


    public NewClientFormView(ClientAddFormViewController clientFormViewController) {
        this.clientFormViewController = clientFormViewController;
        clientFormViewController.initView(this, binder);

        add(createTopButtonLayout());
        createComboBox();
        add(createFormLayout());
        add(createBottomButtonLayout());
        binder.bindInstanceFields(this);
        clientFormViewController.clearForm();
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout(firstName, lastName, phoneNumber, email, nip);
        formLayout.getStyle().set("padding-left", "30px");
        formLayout.getStyle().set("padding-right", "30px");
        return formLayout;
    }

    private Component createBottomButtonLayout() {
        HorizontalLayout bottomButtonLayout = new HorizontalLayout();
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        bottomButtonLayout.add(save);
        bottomButtonLayout.add(cancel);
        bottomButtonLayout.add(update);
        bottomButtonLayout.getStyle().set("padding-left", "30px");
        bottomButtonLayout.getStyle().set("padding-top", "30px");


        cancel.addClickListener(e -> clientFormViewController.clearForm());
        save.addClickListener(e -> {
            clientFormViewController.saveNewClient(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " stored.");
            clientFormViewController.clearForm();
        });

        update.addClickListener(e -> {
            clientFormViewController.updateClient(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " updated.");
            clientFormViewController.clearForm();

        });
        update.setVisible(false);
        update.addClickShortcut(Key.ENTER);
        return bottomButtonLayout;

    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        topButtonLayout.getStyle().set("padding-right", "15px");
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");

        back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        back.addClickListener(e -> clientFormViewController.returnToClients());
        back.getStyle().set("margin-left", "auto");
        back.addClickShortcut(Key.ESCAPE);

        topButtonLayout.add(back);
        return topButtonLayout;
    }

    private void createComboBox() {
        clientType.setItemLabelGenerator(EClientType::getType);
        clientType.setItems(EClientType.values());
        clientType.getStyle().set("padding-left", "30px");
        add(clientType);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String clientId) {
        if (!clientId.isEmpty()) {
            Client client = clientFormViewController.findClientById(Long.valueOf(clientId));

            binder.setBean(client);
            firstName.setValue(client.getFirstName());
            lastName.setValue(client.getLastName());
            phoneNumber.setValue(client.getPhoneNumber());
            if (!nip.isEmpty()) {
                nip.setValue(client.getNip());
            }
            email.setValue(client.getEmail());
            clientType.setValue(client.getClientType());

            cancel.setVisible(false);
            save.setVisible(false);
            update.setVisible(true);
        }

    }


}
