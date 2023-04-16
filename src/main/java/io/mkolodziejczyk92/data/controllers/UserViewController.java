package io.mkolodziejczyk92.data.controllers;


import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import io.mkolodziejczyk92.data.entity.User;
import io.mkolodziejczyk92.data.service.UsersService;
import io.mkolodziejczyk92.views.users.UserView;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.stream.Stream;

@Controller
public class UserViewController {

    private UserView userView;

    private final UsersService userService;

    public UserViewController(UsersService userService) {
        this.userService = userService;
    }

    public void initView(UserView userView) {
        this.userView = userView;
    }

    public Optional<User> getUserById(Long id){
       return userService.get(id);
    }

    public Stream<User> allUsersPageableStream(Query<User,Void> query){
        return userService.list(PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream();
    }

}
