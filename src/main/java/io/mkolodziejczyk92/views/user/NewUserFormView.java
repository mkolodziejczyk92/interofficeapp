package io.mkolodziejczyk92.views.user;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import io.mkolodziejczyk92.data.controllers.UserFormController;
import io.mkolodziejczyk92.data.controllers.UsersViewController;
import io.mkolodziejczyk92.data.entity.User;
import io.mkolodziejczyk92.data.enums.ERole;
import io.mkolodziejczyk92.utils.ComponentFactory;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

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

    private final PasswordField password = new PasswordField("Password");
    private final PasswordField confirmPassword = new PasswordField("Confirm password");
    private final CheckboxGroup<ERole> roles = new CheckboxGroup<>("Role");

    private final Button cancel = ComponentFactory.createCancelButton();

    private final Button save = ComponentFactory.createSaveButton();

    private final Button back = ComponentFactory.createBackButton();
    private final Button update = ComponentFactory.createUpdateButton();
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

    private Component createFormLayout() {
        return ComponentFactory.createFormLayout(userName, firstName, lastName, email, password, confirmPassword);
    }

    private Component createBottomButtonLayout() {
        HorizontalLayout bottomButtonLayout = ComponentFactory.createBottomButtonLayout();
        bottomButtonLayout.add(cancel, save, update);
        update.setVisible(false);
        cancel.addClickListener(e -> userAddNewFormController.clearForm());
        save.addClickListener(e -> {
            userAddNewFormController.saveNewUser(binder.getBean(), confirmPassword);
        });
        update.addClickListener(e -> {
            userAddNewFormController.update(binder.getBean(), confirmPassword);
        });
        save.addClickShortcut(Key.ENTER);

        return bottomButtonLayout;
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = ComponentFactory.createTopButtonLayout();
        back.addClickListener(e -> UI.getCurrent().navigate(UsersView.class));
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
            binder.setBean(usersViewController.getUserByIdForEditForm(Long.valueOf(userId)));
            cancel.setVisible(false);
            save.setVisible(false);
            update.setVisible(true);

            password.setValue("");
            password.setPlaceholder("Add new or leave empty");
        }
    }
}
