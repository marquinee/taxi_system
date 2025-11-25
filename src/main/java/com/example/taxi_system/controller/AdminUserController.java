package com.example.taxi_system.controller;

import com.example.taxi_system.entity.User;
import com.example.taxi_system.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;
    private final PasswordEncoder encoder;

    public AdminUserController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("actionUrl", "/admin/users/add");
        return "admin/user-form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("user") User user) {
        user.setPasswordHash(encoder.encode(user.getPasswordHash()));
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("actionUrl", "/admin/users/edit/" + id);
        return "admin/user-form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute("user") User updatedUser) {

        User existing = userService.findById(id);

        existing.setUsername(updatedUser.getUsername());
        existing.setRole(updatedUser.getRole());

        // пароль меняем только если пользователь ввёл новый
        if (updatedUser.getPasswordHash() != null && !updatedUser.getPasswordHash().isEmpty()) {
            existing.setPasswordHash(encoder.encode(updatedUser.getPasswordHash()));
        }

        userService.save(existing);
        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}
