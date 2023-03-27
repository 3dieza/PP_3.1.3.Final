package com.tim.spring_security.controller;

import com.tim.spring_security.model.User;
import com.tim.spring_security.service.RoleService;
import com.tim.spring_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String userList(Model model) {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        model.addAttribute("allUsers", userService.listUsers());
        model.addAttribute("userMain", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("user") User user, @RequestParam("listRoles") ArrayList<Long> roles) {
        userService.change(user, roleService.findRoles(roles));
        return "redirect:/admin";
    }

    @PostMapping("/new")
    public String addUser(User user, @RequestParam("listRoles") ArrayList<Long> roles) {
        userService.add(user, roleService.findRoles(roles));
        return "redirect:/admin";
    }
}