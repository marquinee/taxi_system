package com.example.taxi_system.controller;

import com.example.taxi_system.entity.Driver;
import com.example.taxi_system.service.DriverService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/driver")
public class DriverController {

    @GetMapping("/orders")
    public String orders() {
        return "driver/orders";
    }

    @GetMapping("/profile")
    public String profile() {
        return "driver/profile";
    }
}
