package io.mkolodziejczyk92.views.client;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.*;
import io.mkolodziejczyk92.data.controllers.ClientAddFormViewController;
import io.mkolodziejczyk92.data.controllers.UsersViewController;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.entity.User;
import io.mkolodziejczyk92.data.enums.EClientType;
import io.mkolodziejczyk92.security.AuthenticatedUser;
import io.mkolodziejczyk92.utils.ComponentFactory;
import io.mkolodziejczyk92.utils.validators.ClientNipValidator;
import io.mkolodziejczyk92.utils.validators.OnlyLettersOfAlphabetValidator;
import io.mkolodziejczyk92.utils.validators.PhoneNumberValidator;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.util.List;
import java.util.stream.Collectors;

import static io.mkolodziejczyk92.utils.ComponentFactory.*;

@PageTitle("New Client")
@Route(value = "new-client", layout = MainLayout.class)
@PermitAll
public class NewClientFormView extends Div implements HasUrlParameter<String> {

    private final ClientAddFormViewController clientFormViewController;
    private final UsersViewController usersViewController;
    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final TextField phoneNumber = new TextField("Phone Number");
    private final TextField email = new TextField("Email");
    private final TextField nip = new TextField("NIP");
    private final Checkbox officeClient = new Checkbox("Office Client");
    private final ComboBox<EClientType> clientType = new ComboBox<>("Client Type");
    private final ComboBox<String> addedBy = new ComboBox<>();
    private final Button cancel = createCancelButton();
    private final Button save = createSaveButton();
    private final Button back = createBackButton();
    private final Button update = createUpdateButton();
    private final Binder<Client> binder = new Binder<>(Client.class);

    public NewClientFormView(ClientAddFormViewController clientFormViewController, UsersViewController usersViewController, AuthenticatedUser loggedUser) {
        this.clientFormViewController = clientFormViewController;
        this.usersViewController = usersViewController;
        clientFormViewController.initBinder(binder);

        add(createTopButtonLayout());
        createComboBox();
        add(createFormLayout());
        add(createBottomButtonLayout());

        binder.bindInstanceFields(this);
        createFieldsValidation();

        clientFormViewController.clearForm();

        save.setEnabled(false);
        update.setEnabled(false);
        createSaveAndUpdateButtonStatus();

        if (loggedUser.get().isPresent() && addedBy.isEmpty()) {
            addedBy.setItems(getUsersFullNames());
            addedBy.setValue(loggedUser.get().get().getFullName());
        }
    }


    private void createFieldsValidation() {
        binder.forField(clientType)
                .asRequired("Choose client type")
                .bind(Client::getClientType, Client::setClientType);
        binder.forField(firstName)
                .withValidator
                        (new OnlyLettersOfAlphabetValidator())
                .bind(Client::getFirstName, Client::setFirstName);
        binder.forField(lastName)
                .withValidator
                        (new OnlyLettersOfAlphabetValidator())
                .bind(Client::getLastName, Client::setLastName);
        binder.forField(phoneNumber)
                .withValidator
                        (new PhoneNumberValidator())
                .bind(Client::getPhoneNumber, Client::setPhoneNumber);
        binder.forField(email)
                .withValidator(new EmailValidator("Incorrect email address"))
                .bind(Client::getEmail, Client::setEmail);
        binder.forField(nip)
                .withValidator(new ClientNipValidator())
                .bind(Client::getNip, Client::setNip);
    }

    private Component createFormLayout() {
        return ComponentFactory.createFormLayout(firstName, lastName, phoneNumber, email, nip);
    }

    private Component createBottomButtonLayout() {
        HorizontalLayout bottomButtonLayout = ComponentFactory.createBottomButtonLayout();

        bottomButtonLayout.add(cancel, save, update);
        save.setEnabled(false);
        cancel.addClickListener(e -> clientFormViewController.clearForm());
        save.addClickListener(e -> clientFormViewController.validateAndSave(binder.getBean()));
        update.addClickListener(e -> clientFormViewController.validateAndUpdate(binder.getBean()));
        update.setVisible(false);
        return bottomButtonLayout;
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = ComponentFactory.createTopButtonLayout();
        back.addClickListener(e -> clientFormViewController.returnToClients());
        topButtonLayout.add(back);
        return topButtonLayout;
    }

    private void createComboBox() {
        clientType.setItemLabelGenerator(EClientType::getType);
        clientType.setItems(EClientType.values());
        clientType.getStyle().set("padding-left", "30px");
        clientType.getStyle().set("padding-right", "30px");
        add(clientType, officeClient);
    }

    private void createSaveAndUpdateButtonStatus() {
        clientType.addValueChangeListener(e -> updateSaveAndUpdateButtonStatus(clientType.isInvalid()));
        email.addValidationStatusChangeListener(e -> updateSaveAndUpdateButtonStatus(email.isInvalid()));
        lastName.addValidationStatusChangeListener(e -> updateSaveAndUpdateButtonStatus(lastName.isInvalid()));
        firstName.addValidationStatusChangeListener(e -> updateSaveAndUpdateButtonStatus(firstName.isInvalid()));
        phoneNumber.addValidationStatusChangeListener(e -> updateSaveAndUpdateButtonStatus(phoneNumber.isInvalid()));
        nip.addValidationStatusChangeListener(e -> updateSaveAndUpdateButtonStatus(nip.isInvalid()));
    }

    private void updateSaveAndUpdateButtonStatus(boolean isInvalid) {
        save.setEnabled(!isInvalid && !clientType.isEmpty() && !email.isEmpty() && !firstName.isEmpty()
                && !lastName.isEmpty() && !phoneNumber.isEmpty());
        update.setEnabled(!isInvalid && !clientType.isEmpty() && !email.isEmpty() && !firstName.isEmpty()
                && !lastName.isEmpty() && !phoneNumber.isEmpty());
    }

    private List<String> getUsersFullNames(){
        List<User> users = usersViewController.allUsers();
        return users.stream()
                .map(User::getFullName)
                .collect(Collectors.toList());
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String clientId) {
        if (!clientId.isBlank()) {
            binder.setBean(clientFormViewController.findClientById(Long.valueOf(clientId)));

            cancel.setVisible(false);
            save.setVisible(false);
            update.setVisible(true);
        }
    }
}
