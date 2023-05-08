package io.mkolodziejczyk92.data.controllers;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import io.mkolodziejczyk92.data.entity.User;
import io.mkolodziejczyk92.data.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.stream.Stream;
@Slf4j
@Controller
public class UsersViewController {

    private final UsersService userService;

    public UsersViewController(UsersService userService) {
        this.userService = userService;
    }

    public User getUserById(Long userId) {
        return userService.get(userId).orElseThrow();
    }

    public Stream<User> allUsersPageableStream(Query<User, Void> query) {
        return userService.list(PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream();
    }

    public void deleteUser(User user) {
        userService.delete(user.getId());
        Notification.show("User " + user.getUserName() + " deleted.");
    }

    public void editUserInformation(User user) {
        if (userService.isExist(user.getId())) {
            UI.getCurrent().navigate("new-user/" + user.getId());
        } else {
            Notification.show("User "
                    + user.getUserName()
                    + " does not exist in the database.");
        }
    }
}
