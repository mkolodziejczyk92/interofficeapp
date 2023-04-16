package io.mkolodziejczyk92.data.controllers;


import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import io.mkolodziejczyk92.data.entity.User;
import io.mkolodziejczyk92.data.service.UsersService;
import io.mkolodziejczyk92.views.user.UsersView;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.stream.Stream;

@Controller
public class UserViewController {

    private UsersView usersView;

    private final UsersService userService;

    public UserViewController(UsersService userService) {
        this.userService = userService;
    }

    public void initView(UsersView usersView) {
        this.usersView = usersView;
    }

    public Optional<User> getUserById(Long id){
       return userService.get(id);
    }

    public Stream<User> allUsersPageableStream(Query<User,Void> query){
        return userService.list(PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream();
    }

}
