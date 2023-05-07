package io.mkolodziejczyk92.views.user;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.mkolodziejczyk92.data.controllers.UserFormController;
import io.mkolodziejczyk92.data.entity.User;
import io.mkolodziejczyk92.data.enums.ERole;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("New User")
@Route(value = "new-user", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class NewUserFormView extends Div {

    private final UserFormController userAddNewFormController;

    private final TextField userName = new TextField("User Name");

    private final TextField firstName = new TextField("First Name");

    private final TextField lastName = new TextField("Last Name");

    private final TextField email = new TextField("Email");

    private final TextField hashedPassword = new TextField("Password");

    private final CheckboxGroup<ERole> ERoles = new CheckboxGroup<>("Role");

    private final Button cancel = new Button("Cancel");

    private final Button save = new Button("Save");

    private final Button back = new Button("Back");

    private final Binder<User> binder = new Binder<>(User.class);

    public NewUserFormView(UserFormController userAddNewFormController) {
        this.userAddNewFormController = userAddNewFormController;
        userAddNewFormController.initBinder(binder);

        add(createTopButtonLayout());
        createComboBox();
        add(createFormLayout());
        add(createBottomButtonLayout());
        binder.bindInstanceFields(this);
        binder.forField(ERoles).bind("ERoles");
        userAddNewFormController.clearForm();


    }

    private Component createFormLayout(){
        FormLayout formLayout = new FormLayout(userName, firstName, lastName, email, hashedPassword);
        formLayout.getStyle().set("padding-left", "30px");
        formLayout.getStyle().set("padding-right", "30px");
        return formLayout;
    }

    private Component createBottomButtonLayout(){

        HorizontalLayout bottomButtonLayout = new HorizontalLayout();
        bottomButtonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        bottomButtonLayout.add(save);
        bottomButtonLayout.add(cancel);
        bottomButtonLayout.getStyle().set("padding-left", "30px");
        bottomButtonLayout.getStyle().set("padding-top", "30px");

        cancel.addClickListener(e -> userAddNewFormController.clearForm());

        save.addClickListener(e -> {
            userAddNewFormController.saveNewUser(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " stored.");
            userAddNewFormController.clearForm();
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
        ERoles.setItemLabelGenerator(ERole::getType);
        ERoles.setItems(ERole.ADMIN);
        ERoles.getStyle().set("padding-left", "30px");

        add(ERoles);
    }
}
