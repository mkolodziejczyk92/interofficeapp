package io.mkolodziejczyk92.views.user;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
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

    private final UserFormController userFormController;
    private final UsersViewController usersViewController;

    private static final String PATTERN = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
    private final TextField userName = new TextField("User Name");
    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final TextField email = new TextField("Email");

    private final PasswordField newPassword = new PasswordField("Password");
    private final PasswordField confirmPassword = new PasswordField("Confirm password");

    private final CheckboxGroup<ERole> roles = new CheckboxGroup<>("Role");

    private final Button cancel = ComponentFactory.createCancelButton();

    private final Button save = ComponentFactory.createSaveButton();

    private final Button back = ComponentFactory.createBackButton();

    private final Button update = ComponentFactory.createUpdateButton();
    private final Button changePassword = new Button("Change password");

    private final Binder<User> binder = new Binder<>(User.class);

    public NewUserFormView(UserFormController userFormController, UsersViewController usersViewController) {
        this.userFormController = userFormController;
        this.usersViewController = usersViewController;
        userFormController.initBinder(binder);
        binder.bindInstanceFields(this);
        add(createTopButtonLayout());
        createComboBox();
        add(createFormLayout());
        add(createBottomButtonLayout());

        binder.forField(roles).bind("ERoles");

        binder.forField(email).
                withValidator(new EmailValidator("Incorrect email address"))
                .bind(User::getEmail, User::setEmail);

        userName.addValidationStatusChangeListener(e -> validateUsername());

        userFormController.clearForm();
    }

    private void validateUsername() {
        if (userFormController.checkIfExist(userName.getValue())) {
            userName.setErrorMessage("This username exist in the database.");
            userName.setInvalid(true);
            save.setEnabled(false);
        } else {
            userName.setErrorMessage(null);
            userName.setInvalid(false);
            save.setEnabled(true);
        }
    }

    private Component createFormLayout() {
        return ComponentFactory.createFormLayout(userName, firstName, lastName, email);
    }

    private Component createBottomButtonLayout() {
        HorizontalLayout bottomButtonLayout = ComponentFactory.createBottomButtonLayout();
        bottomButtonLayout.add(cancel, save, update, changePassword);
        update.setVisible(false);
        changePassword.setVisible((false));
        changePassword.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addClickListener(e -> userFormController.clearForm());
        save.addClickShortcut(Key.ENTER);

        save.addClickListener(e -> {
            userFormController.saveNewUser(binder.getBean());
        });
        update.addClickListener(e -> {
            userFormController.update(binder.getBean());
        });
        changePassword.addClickListener(e -> {
            Dialog changingPasswordDialog = createDialogForChangeUserPassword(binder.getBean());
            add(changingPasswordDialog);
            changingPasswordDialog.open();
        });

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

    private Dialog createDialogForChangeUserPassword(User user) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(String.format("Add new or change password for n%s?", user.getUserName()));
        Button saveButton = ComponentFactory.createStandardButton("Save");
        saveButton.getStyle().set("padding-right", "15px");
        dialog.add(newPassword, confirmPassword);
        newPassword.getStyle().set("padding-right", "10px");
        confirmPassword.getStyle().set("padding-left", "10px");
        newPassword.setPattern(PATTERN);
        newPassword.setErrorMessage("Incorrect password.The password must have a minimum of 8 characters, " +
                "lowercase and uppercase and a special character");
        confirmPassword.setPattern(newPassword.getPattern());
        confirmPassword.setErrorMessage(newPassword.getErrorMessage());
        confirmPassword.addValidationStatusChangeListener(e -> {
            String password1 = newPassword.getValue();
            String password2 = confirmPassword.getValue();
            boolean passwordsMatch = password1.equals(password2);
            if (passwordsMatch) {
                confirmPassword.setInvalid(false);
                confirmPassword.setErrorMessage("");
                saveButton.setEnabled(true);
            } else {
                confirmPassword.setInvalid(true);
                confirmPassword.setErrorMessage("Passwords do not match!");
                saveButton.setEnabled(false);
            }
        });
        saveButton.addClickListener(e -> {
            userFormController.changePassword(user, newPassword, confirmPassword);
            dialog.close();
        });
        Button cancelButton = new Button("Cancel", event -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        dialog.getFooter().add(saveButton, cancelButton);
        return dialog;
    }

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String userId) {
        if (!userId.isEmpty()) {
            binder.setBean(usersViewController.getUserByIdForEditForm(Long.valueOf(userId)));
            cancel.setVisible(false);
            save.setVisible(false);
            update.setVisible(true);
            changePassword.setVisible(true);
        }
    }
}
