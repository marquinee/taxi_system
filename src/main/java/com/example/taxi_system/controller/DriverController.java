package com.example.taxi_system.controller;

import com.example.taxi_system.entity.Driver;
import com.example.taxi_system.entity.User;
import com.example.taxi_system.service.DriverService;
import com.example.taxi_system.service.OrderService;
import com.example.taxi_system.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/driver")
public class DriverController {

    private final OrderService orderService;
    private final UserService userService;

    public DriverController(OrderService orderService,
                            UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/orders")
    public String myOrders(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = userService.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Driver d = u.getDriver();

        model.addAttribute("orders", orderService.findByDriverId(d.getDriverId()));
        return "driver/orders";
    }
}
