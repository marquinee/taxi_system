package com.example.taxi_system.controller;

import com.example.taxi_system.entity.Client;
import com.example.taxi_system.entity.Order;
import com.example.taxi_system.entity.OrderStatus;
import com.example.taxi_system.service.ClientService;
import com.example.taxi_system.service.DriverService;
import com.example.taxi_system.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/operator")
public class OperatorController {
    private final ClientService clientService;
    private final DriverService driverService;
    private final OrderService orderService;

    public OperatorController(ClientService clientService,
                              DriverService driverService,
                              OrderService orderService) {
        this.clientService = clientService;
        this.driverService = driverService;
        this.orderService = orderService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "operator/dashboard";
    }

}