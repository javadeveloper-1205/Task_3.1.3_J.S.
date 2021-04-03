package mygroup.Task_311.service.impl;

import mygroup.Task_311.dao.RoleDAOImpl;
import mygroup.Task_311.dao.UserDAO;
import mygroup.Task_311.model.User;
import mygroup.Task_311.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        userDAO.editUser(user);
    }

    public User getById(long id) {
        return userDAO.getById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> user = userDAO.findUserByUsernameOptional(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

//    @Override
//    @Transactional
//    public Set<Role> authorities(String[] roleNames) {
//        Set<Role> roleSet = roleDao.findRolesByRoleNames(roleNames);
//        System.out.println(roleSet);
//        return roleSet;
//    }
//
//    @Override
//    public Set<Role> getAllRoles() {
//        Set<Role> role = roleDao.findAllRoles();
//        return role;
//    }
}
