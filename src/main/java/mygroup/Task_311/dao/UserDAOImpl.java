package mygroup.Task_311.dao;


import mygroup.Task_311.model.User;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Component
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> allUsers() {
        return entityManager
                .createQuery("from User")
                .getResultList();
    }

    public void add(User user) {
        entityManager.persist(user);
    }

    public void delete(Long id) {
        entityManager.remove(getById(id));
    }

    public void edit(User user) {

        entityManager.merge(user);

    }

    public User getById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByUsername(String username) {
        return (User) entityManager.createQuery("from User where username = :username")
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void editUser(User user) {
        User newUser = entityManager.merge(user);
        entityManager.persist(newUser);
    }

    @Override
    public Optional<User> findUserByUsernameOptional(String username) {
        User user = new User();
        try {
            user = entityManager.createQuery("FROM User u WHERE u.username=:username", User.class).setParameter("username", username).getSingleResult();
        } catch (Exception E) {
        }
        return Optional.ofNullable(user);
    }
}