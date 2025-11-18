package com.example.taxi_system.controller;

import com.example.taxi_system.entity.Driver;
import com.example.taxi_system.entity.User;
import com.example.taxi_system.service.DriverService;
import com.example.taxi_system.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final DriverService driverService;

    public AdminController(UserService userService, DriverService driverService) {
        this.userService = userService;
        this.driverService = driverService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("newUser", new User());
        return "admin/users";
    }
    @PostMapping("/users")
    public String addUser(@ModelAttribute("newUser") User user) {
        userService.save(user);
        return "redirect:/admin/users";
    }


    @GetMapping("/drivers")
    public String drivers(Model model) {
        model.addAttribute("drivers", driverService.findAll());
        model.addAttribute("newDriver", new Driver());
        return "admin/drivers";
    }
    @PostMapping("/drivers")
    public String addDriver(@ModelAttribute("newDriver") Driver driver) {
        driverService.save(driver);
        return "redirect:/admin/drivers";
    }


}
