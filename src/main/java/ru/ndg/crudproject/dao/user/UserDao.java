package ru.ndg.crudproject.dao.user;

import ru.ndg.crudproject.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
    User getUserById(Long id);
    User saveUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
    User getUserByUsername(String username);
}
