package com.example.taxi_system.controller;

import com.example.taxi_system.entity.Client;
import com.example.taxi_system.entity.Order;
import com.example.taxi_system.entity.OrderStatus;
import com.example.taxi_system.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Scanner;

@Controller
@RequestMapping("/operator")
public class OperatorController {
    private final ClientService clientService;
    private final DriverService driverService;
    private final OrderService orderService;
    private final CarService carService;
    private final TariffService tariffService;

    public OperatorController(ClientService clientService,
                              DriverService driverService,
                              OrderService orderService,
                              CarService carService,
                              TariffService tariffService) {
        this.clientService = clientService;
        this.driverService = driverService;
        this.orderService = orderService;
        this.carService = carService;
        this.tariffService = tariffService;
    }
    @GetMapping("/cars")
    public String cars(Model model) {
        model.addAttribute("cars", carService.findAll());
        return "operator/cars";
    }

    @GetMapping("/tariffs")
    public String tariffs(Model model) {
        model.addAttribute("tariffs", tariffService.findActive());
        return "operator/tariffs";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "operator/dashboard";
    }

}