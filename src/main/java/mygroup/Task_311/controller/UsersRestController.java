package mygroup.Task_311.controller;

import mygroup.Task_311.model.Role;
import mygroup.Task_311.model.User;
import mygroup.Task_311.service.RoleService;
import mygroup.Task_311.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/users")
public class UsersRestController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService){
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<User>> allUsers(){
        return ResponseEntity.ok(userService.allUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping
    public ResponseEntity<List<User>> create(@RequestBody User user){
        userService.add(user);
        return ResponseEntity.ok(userService.allUsers());
    }


    @PutMapping("/{id}")
    public ResponseEntity<List<User>> editUser(@PathVariable Long id, @RequestBody User userUpdated){
        User user = userService.getById(id);
        user.setUsername(userUpdated.getUsername());
        user.setLastname(userUpdated.getLastname());
        user.setAge(userUpdated.getAge());
        user.setEmail(userUpdated.getEmail());
        user.setPassword(userUpdated.getPassword());

        user.setRoles(userUpdated.getRoles());
        userService.updateRoleList(user);
        userService.edit(user);

        return ResponseEntity.ok(userService.allUsers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<User>> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(userService.allUsers());
    }
}