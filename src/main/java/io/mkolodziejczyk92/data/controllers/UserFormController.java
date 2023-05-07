package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.data.binder.Binder;
import io.mkolodziejczyk92.data.entity.User;
import io.mkolodziejczyk92.data.enums.ERole;
import io.mkolodziejczyk92.data.service.UserService;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class UserFormController {

    private final UserService userService;

    private Binder<User> binder;

    public UserFormController(UserService userService) {
        this.userService = userService;
    }

    public void initBinder(Binder<User> binder){
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
