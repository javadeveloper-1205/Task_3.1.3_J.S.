package mygroup.Task_311.service;

import mygroup.Task_311.dao.RoleDAOImpl;
import mygroup.Task_311.dao.UserDAO;
import mygroup.Task_311.model.Role;
import mygroup.Task_311.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    private RoleDAOImpl roleDAO;

    public List<User> allUsers() {
        return userDAO.allUsers();
    }

    @Transactional
    public void add(User user) {
        userDAO.add(user);
    }

    @Transactional
    public void delete(Long id) {
        userDAO.delete(id);
    }

    public void edit(User user) {
        userDAO.edit(user);
    }

    @Override
    @Transactional
    public void editUser(User user) {
        userDAO.saveUserDao(user);
    }

    public User getById(long id) {
        return userDAO.getById(id);
    }

    public User findUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public void updateRoleList(User user) {
        List<Role> roleList = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roleList.add(roleDAO.findRoleByName(role.getRole()));
        }
        user.setRoles(roleList);
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> user = userDAO.findUserByUsernameOptional(username);
        return user.get();
    }
}
