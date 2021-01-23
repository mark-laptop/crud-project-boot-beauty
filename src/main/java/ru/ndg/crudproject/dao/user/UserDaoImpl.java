package ru.ndg.crudproject.dao.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.ndg.crudproject.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDaoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User saveUser(User user) {
        passwordEncoder.encode(user.getPassword());
        entityManager.persist(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        User userFromDB = entityManager.find(User.class, user.getId());
        BeanUtils.copyProperties(user, userFromDB, "password");
        if (!user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userFromDB.setPassword(user.getPassword());
        }
//        userFromDB.setRoles(user.getRoles());
        return entityManager.merge(userFromDB);
    }

    @Override
    public void deleteUser(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public User getUserByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("FROM User WHERE nickname = :username", User.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }
}
