package io.mkolodziejczyk92.views.clients;

import com.vaadin.flow.component.Component;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.mkolodziejczyk92.data.controllers.ClientFormViewController;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.enums.EClientType;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("New Client")
@Route(value = "new-client", layout = MainLayout.class)
@PermitAll
public class NewClientFormView  extends Div {

    private final ClientFormViewController clientFormViewController;

    private TextField firstName = new TextField("First Name");

    private TextField lastName = new TextField("Last Name");
    private TextField phoneNumber = new TextField("Phone Number");
    private TextField email = new TextField("Email");
    private TextField nip = new TextField("NIP");
    private ComboBox<EClientType> clientType = new ComboBox<>("Client Type");
    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    private Button back = new Button("Back");
    private Binder<Client> binder = new Binder<>(Client.class);



    public NewClientFormView(ClientFormViewController clientFormViewController) {
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
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        bottomButtonLayout.add(save);
        bottomButtonLayout.add(cancel);
        bottomButtonLayout.getStyle().set("padding-left", "30px");
        bottomButtonLayout.getStyle().set("padding-top", "30px");

        cancel.addClickListener(e -> clientFormViewController.clearForm());
        save.addClickListener(e -> {
            clientFormViewController.saveNewClient(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " stored.");
            clientFormViewController.clearForm();
        });


        return bottomButtonLayout;

    }

    private Component createTopButtonLayout(){
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        topButtonLayout.getStyle().set("padding-right", "15px");
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");

        back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        back.addClickListener(e -> UI.getCurrent().navigate(ClientsView.class));
        back.getStyle().set("margin-left", "auto");

        topButtonLayout.add(back);
        return topButtonLayout;
    }

    private void createComboBox(){
        clientType.setItemLabelGenerator(EClientType::getType);
        clientType.setItems(EClientType.values());
        clientType.getStyle().set("padding-left", "30px");
        add(clientType);
    }
}
