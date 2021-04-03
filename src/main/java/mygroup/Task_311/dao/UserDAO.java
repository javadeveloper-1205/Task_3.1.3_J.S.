package mygroup.Task_311.dao;


import mygroup.Task_311.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    List<User> allUsers();

    void add(User user);

    void delete(Long id);

    void edit(User user);

    void editUser(User user);

    User getById(long id);

    User findByUsername(String username);

    Optional<User> findUserByUsernameOptional(String username);

}