package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.data.binder.Binder;
import io.mkolodziejczyk92.data.entity.User;
import io.mkolodziejczyk92.data.enums.ERole;
import io.mkolodziejczyk92.data.service.UserService;
import io.mkolodziejczyk92.views.user.NewUserFormView;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.Set;

@Controller
public class UserFormController {

    private NewUserFormView newUserFormView;

    private final UserService userService;

    private Binder<User> binder;

    public UserFormController(UserService userService) {
        this.userService = userService;
    }

    public void initView(NewUserFormView newUserFormView, Binder<User> binder){
        this.newUserFormView = newUserFormView;
        this.binder=binder;
    }

    public void clearForm(){
        this.binder.setBean(new User());
    }

    public void saveNewUser(User user){
        user.setHashedPassword(userService.createHashedPassword(user.getHashedPassword()));
        if(user.getERoles().isEmpty()){
            user.setERoles(Set.of(ERole.USER));
        } else{
            user.setERoles(Set.of(ERole.USER,ERole.ADMIN));
        }
        userService.save(user);
    }
}
