package mygroup.Task_311.service;

import mygroup.Task_311.model.Role;
import mygroup.Task_311.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> allUsers();

    void add(User user);

    void delete(Long id);

    void editUser(User user);

    User getById(long id);

    User getUserByUsername(String username);
}