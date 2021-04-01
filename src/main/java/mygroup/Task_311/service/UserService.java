package mygroup.Task_311.service;

import mygroup.Task_311.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> allUsers();
    void add(User user);
    void delete(Long id);
    void editUser(User user);
    User getById(long id);
    User findUserByUsername(String username);
    User getUserByUsername(String username);

    void updateRoleList(User user);
}