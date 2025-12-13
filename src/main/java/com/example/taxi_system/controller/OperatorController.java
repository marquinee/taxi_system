package com.example.taxi_system.controller;

import com.example.taxi_system.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String cars(@RequestParam(required = false) String search,
                       @RequestParam(required = false) String sortBy,
                       @RequestParam(required = false, defaultValue = "asc") String sortDir,
                       Model model) {
        List<com.example.taxi_system.entity.Car> cars;
        if (search != null && !search.trim().isEmpty()) {
            cars = carService.searchAndSort(search.trim(), sortBy, sortDir);
        } else {
            cars = carService.sort(carService.findAll(), sortBy, sortDir);
        }
        model.addAttribute("cars", cars);
        model.addAttribute("search", search);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        return "operator/cars";
    }

    @GetMapping("/tariffs")
    public String tariffs(@RequestParam(required = false) String search,
                          @RequestParam(required = false) String sortBy,
                          @RequestParam(required = false, defaultValue = "asc") String sortDir,
                          Model model) {
        List<com.example.taxi_system.entity.Tariff> tariffs;
        if (search != null && !search.trim().isEmpty()) {
            tariffs = tariffService.searchAndSort(search.trim(), sortBy, sortDir);
        } else {
            tariffs = tariffService.sort(tariffService.findActive(), sortBy, sortDir);
        }
        model.addAttribute("tariffs", tariffs);
        model.addAttribute("search", search);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        return "operator/tariffs";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("statistics", statisticsService.getOperatorStatistics());
        return "operator/dashboard";
    }

}