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
import io.mkolodziejczyk92.utils.ComponentFactory;
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

    private final Button cancel = ComponentFactory.createCancelButton();

    private final Button save = ComponentFactory.createSaveButton();

    private final Button back = ComponentFactory.createBackButton();

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
        return  ComponentFactory.createFormLayout(userName, firstName, lastName, email, hashedPassword);
    }

    private Component createBottomButtonLayout(){

        HorizontalLayout bottomButtonLayout = ComponentFactory.createBottomButtonLayout();
        bottomButtonLayout.add(cancel, save);

        cancel.addClickListener(e -> userAddNewFormController.clearForm());

        save.addClickListener(e -> {
            userAddNewFormController.saveNewUser(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " stored.");
            userAddNewFormController.clearForm();
        });

        return bottomButtonLayout;
    }

    private Component createTopButtonLayout(){
        HorizontalLayout topButtonLayout = ComponentFactory.createTopButtonLayout();
        back.addClickListener(e -> UI.getCurrent().navigate(UsersView.class));
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
