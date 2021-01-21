package ru.ndg.crudproject.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.ndg.crudproject.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User saveUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
    User getUserByUsername(String username);
}
