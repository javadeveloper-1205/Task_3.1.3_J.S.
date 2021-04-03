package mygroup.Task_311.controller;

import mygroup.Task_311.model.User;
import mygroup.Task_311.service.RoleService;
import mygroup.Task_311.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping(value = "/user")
public class UserController {

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
    public String dashboardPageList(Model model, @AuthenticationPrincipal UserDetails currentUser ) {
        User user = userService.getUserByUsername(currentUser.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("userRoles", roleService.getById(user.getId()));
        return "user";
    }
}
