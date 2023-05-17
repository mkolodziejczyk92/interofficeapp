package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import io.mkolodziejczyk92.data.entity.User;
import io.mkolodziejczyk92.data.service.ContractRepository;
import io.mkolodziejczyk92.data.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class UserFormController {

    private final UserService userService;

    private Binder<User> binder;
    private final ContractRepository contractRepository;

    public UserFormController(UserService userService,
                              ContractRepository contractRepository) {
        this.userService = userService;
        this.contractRepository = contractRepository;
    }

    public void initBinder(Binder<User> binder) {
        this.binder = binder;
    }

    public void clearForm() {
        this.binder.setBean(new User());
    }

    public void saveNewUser(User user) {
        if (!checkIfExist(user.getUserName())) {
            userService.save(user);
            Notification.show("User " + user.getUserName() + " stored.");
            UI.getCurrent().navigate("users");
        }
    }

    public void update(User user) {
        userService.update(user);
        Notification.show("User " + user.getUserName() + " updated.");
        UI.getCurrent().navigate("users");
    }

    public boolean checkIfExist(String userName) {
        return userService.isExist(userName);
    }

    public void changePassword(User user, PasswordField newPassword, PasswordField confirmPassword) {
        if (userService.checkIfPasswordMatch(newPassword, confirmPassword)) {
            user.setPassword(userService.createHashedPassword(newPassword.getValue()));
            userService.update(user);
            Notification.show("Password for " + user.getUserName() + " updated.");
            UI.getCurrent().navigate("users");
        } else {
            Notification.show("Passwords do not match!");
            UI.getCurrent().navigate("new-user/" + user.getId());

        }

    }
}
