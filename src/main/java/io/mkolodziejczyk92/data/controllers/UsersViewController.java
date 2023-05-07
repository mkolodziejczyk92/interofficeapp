package io.mkolodziejczyk92.data.controllers;


import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import io.mkolodziejczyk92.data.entity.User;
import io.mkolodziejczyk92.data.service.UsersService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.stream.Stream;

@Controller
public class UsersViewController {

    private final UsersService userService;

    public UsersViewController(UsersService userService) {
        this.userService = userService;
    }

    public Optional<User> getUserById(Long id){
       return userService.get(id);
    }

    public Stream<User> allUsersPageableStream(Query<User,Void> query){
        return userService.list(PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream();
    }

}
