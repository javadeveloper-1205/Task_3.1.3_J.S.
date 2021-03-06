package mygroup.Task_311.controller;

import mygroup.Task_311.model.User;
import mygroup.Task_311.service.RoleService;
import mygroup.Task_311.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @PutMapping("/edit")
    public ResponseEntity<?> update(@RequestBody User user) {
        userService.editUser(user);
        return ResponseEntity.ok(userService.allUsers());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<User>> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(userService.allUsers());
    }
}