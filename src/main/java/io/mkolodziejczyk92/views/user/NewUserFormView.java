package io.mkolodziejczyk92.views.user;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import io.mkolodziejczyk92.data.controllers.UserFormController;
import io.mkolodziejczyk92.data.controllers.UsersViewController;
import io.mkolodziejczyk92.data.entity.User;
import io.mkolodziejczyk92.data.enums.ERole;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import static io.mkolodziejczyk92.data.enums.ERole.ADMIN;

@PageTitle("New User")
@Route(value = "new-user", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class NewUserFormView extends Div implements HasUrlParameter<String> {

    private final UserFormController userAddNewFormController;
    private final UsersViewController usersViewController;
    private final TextField userName = new TextField("User Name");
    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final TextField email = new TextField("Email");
    private final TextField password = new TextField("Password");
    private final CheckboxGroup<ERole> roles = new CheckboxGroup<>("Role");
    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");
    private final Button back = new Button("Back");
    private final Button update = new Button("Update");
    private final Binder<User> binder = new Binder<>(User.class);

    public NewUserFormView(UserFormController userAddNewFormController, UsersViewController usersViewController) {
        this.userAddNewFormController = userAddNewFormController;
        this.usersViewController = usersViewController;
        userAddNewFormController.initBinder(binder);

        add(createTopButtonLayout());
        createComboBox();
        add(createFormLayout());
        add(createBottomButtonLayout());
        binder.bindInstanceFields(this);
        binder.forField(roles).bind("ERoles");
        userAddNewFormController.clearForm();


    }

    private Component createFormLayout(){
        FormLayout formLayout = new FormLayout(userName, firstName, lastName, email, password);
        formLayout.getStyle().set("padding-left", "30px");
        formLayout.getStyle().set("padding-right", "30px");
        return formLayout;
    }

    private Component createBottomButtonLayout(){

        HorizontalLayout bottomButtonLayout = new HorizontalLayout();
        bottomButtonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        bottomButtonLayout.add(save);
        bottomButtonLayout.add(cancel);
        bottomButtonLayout.add(update);
        update.setVisible(false);
        bottomButtonLayout.getStyle().set("padding-left", "30px");
        bottomButtonLayout.getStyle().set("padding-top", "30px");

        cancel.addClickListener(e -> userAddNewFormController.clearForm());
        save.addClickListener(e -> {
            userAddNewFormController.saveNewUser(binder.getBean());
        });
        update.addClickListener(e -> {
            userAddNewFormController.update(binder.getBean());
        });
        save.addClickShortcut(Key.ENTER);
        return bottomButtonLayout;
    }

    private Component createTopButtonLayout(){
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        topButtonLayout.getStyle().set("padding-right", "15px");
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");

        back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        back.addClickListener(e -> UI.getCurrent().navigate(UsersView.class));
        back.getStyle().set("margin-left", "auto");
        back.addClickShortcut(Key.ESCAPE);
        topButtonLayout.add(back);
        return topButtonLayout;
    }

    private void createComboBox() {
        roles.setItemLabelGenerator(ERole::getType);
        roles.setItems(ADMIN);
        roles.getStyle().set("padding-left", "30px");
        add(roles);
    }
    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String userId) {
        if (!userId.isEmpty()) {
            binder.setBean(usersViewController.getUserById(Long.valueOf(userId)));
            // ZMIENIĆ METODĘ NA POBIERANIE ENCJI BEZ HASLA I SPRAWDZICZ CZY BINDER DODA JE DO FORMULARZA
            // ZROBIC DRUGI FORMULARZ DO ZMIANY HASLA
            // SPRAWDZIC

            password.setValue("");

            cancel.setVisible(false);
            save.setVisible(false);
            update.setVisible(true);
        }
    }
}
