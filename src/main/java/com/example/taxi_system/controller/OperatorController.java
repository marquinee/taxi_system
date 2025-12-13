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
    private final StatisticsService statisticsService;

    public OperatorController(ClientService clientService,
                              DriverService driverService,
                              OrderService orderService,
                              CarService carService,
                              TariffService tariffService,
                              StatisticsService statisticsService) {
        this.clientService = clientService;
        this.driverService = driverService;
        this.orderService = orderService;
        this.carService = carService;
        this.tariffService = tariffService;
        this.statisticsService = statisticsService;
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
    public String dashboard(Model model) {
        model.addAttribute("statistics", statisticsService.getOperatorStatistics());
        return "operator/dashboard";
    }

}