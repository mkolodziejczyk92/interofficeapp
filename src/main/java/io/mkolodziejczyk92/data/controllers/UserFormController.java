package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.data.binder.Binder;
import io.mkolodziejczyk92.data.entity.User;
import io.mkolodziejczyk92.data.enums.ERole;
import io.mkolodziejczyk92.data.service.UserService;
import io.mkolodziejczyk92.views.users.NewUserFormView;
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
        if(user.getERoles().isEmpty()){
            user.setERoles(Set.of(ERole.USER));
        } else{
            Set<ERole> roles = new HashSet<>(user.getERoles());
            roles.add(ERole.USER);
        }
        userService.save(user);
    }
}
