package mygroup.Task_311.controller;

import mygroup.Task_311.model.Role;
import mygroup.Task_311.model.User;
import mygroup.Task_311.service.RoleService;
import mygroup.Task_311.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class UsersController {

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

    @GetMapping("admin/users")
    public String allUsers(Model model, @AuthenticationPrincipal UserDetails currentUser){
        model.addAttribute("roles", roleService.listRoles());
        model.addAttribute("users", userService.allUsers());
        User user = userService.findUserByUsername(currentUser.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("userRoles", roleService.getById(user.getId()));
        return "admin/allUsers";
    }

    @GetMapping("admin/users/{id}")
    public String show(@PathVariable("id") long id, Model model){
        model.addAttribute("user", userService.getById(id));
        return "admin/show";
    }

    @GetMapping("/admin/users/new")
    public String newUser(Model model, @AuthenticationPrincipal UserDetails currentUser){
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.listRoles());
        User user = userService.findUserByUsername(currentUser.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("userRoles", roleService.getById(user.getId()));
        return "admin/new";
    }

    @PostMapping("admin/users/createNew")
    public String create(@ModelAttribute("user") User user, @RequestParam(value = "allRoles") String [] roles){
        List<Role> roleList = new ArrayList<>();
        for (String role : roles){
            roleList.add(roleService.findRoleByName(role));
        }
        user.setRoles(roleList);
        userService.add(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String editPage(Model model, @PathVariable("id") long id){
        model.addAttribute("allRoles", roleService.listRoles());
        model.addAttribute("user", userService.getById(id));
        return "admin/edit";
    }

    @RequestMapping(value = "/admin/users/{id}", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("user") User user, @RequestParam(value = "allRoles") String[] roles){
        List<Role> roleSet = new ArrayList<>();
        for(String role : roles){
            roleSet.add(roleService.findRoleByName(role));
        }
        user.setRoles(roleSet);
        userService.edit(user);
        return "redirect:/admin/users";
    }

    @RequestMapping(value="/admin/users/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

//
//    @RequestMapping("/user")
//    public String dashboardPageList(Model model, @AuthenticationPrincipal UserDetails currentUser ) {
//        User user = userService.findUserByUsername(currentUser.getUsername());
//        model.addAttribute("user", user);
//        model.addAttribute("userRoles", roleService.getById(user.getId()));
//        return "user";
//    }
}