package ru.ndg.crudproject.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ndg.crudproject.dao.role.RoleDao;
import ru.ndg.crudproject.dao.user.UserDao;
import ru.ndg.crudproject.model.Role;
import ru.ndg.crudproject.model.User;
import ru.ndg.crudproject.util.SecurityUtil;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        return userDao.saveUser(user);
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        User updateUser = userDao.updateUser(user);
        SecurityUtil.refreshRolesForAuthenticatedUser(updateUser);
        return updateUser;
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = Optional.ofNullable(userDao.getUserByUsername(username));
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("Not found user by username: " + username));
        return new org.springframework.security.core.userdetails.User(user.getNickname(),
                user.getPassword(), getGrantedAuthorityByRoles(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorityByRoles(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }
}
