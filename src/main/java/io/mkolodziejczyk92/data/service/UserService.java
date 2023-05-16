package io.mkolodziejczyk92.data.service;

import com.vaadin.flow.component.textfield.PasswordField;
import io.mkolodziejczyk92.data.entity.User;
import io.mkolodziejczyk92.data.enums.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User save(User user) {
        if (user.getERoles().isEmpty()) {
            user.setERoles(Set.of(ERole.USER));
        } else {
            user.setERoles(Set.of(ERole.USER, ERole.ADMIN));
        }
        return repository.save(user);
    }

    public Optional<User> get(Long id) {
        return repository.findById(id);
    }

    public User update(User user) {
        return repository.save(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<User> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<User> list(Pageable pageable, Specification<User> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public String createHashedPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public boolean isExist(Long userId) {
        return repository.existsById(userId);
    }

    public boolean isExist(String userName) {
        return repository.existsUserByUserName(userName);
    }

    public boolean checkIfPasswordMatch(PasswordField newPassword, PasswordField confirmPassword) {
        return newPassword.getValue().equals(confirmPassword.getValue());
    }
}
