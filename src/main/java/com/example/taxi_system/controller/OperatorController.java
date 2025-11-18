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

    @GetMapping("/clients")
    public String clients(Model model) {
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("newClient", new Client());
        return "operator/clients";
    }

    @PostMapping("/clients")
    public String saveClient(@ModelAttribute("newClient") Client client) {
        clientService.save(client);
        return "redirect:/operator/clients";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("drivers", driverService.findAll());
        model.addAttribute("newOrder", new Order());
        return "operator/orders";
    }

    @PostMapping("/orders")
    public String saveOrder(@ModelAttribute("newOrder") Order order,
                            @RequestParam Long clientId,
                            @RequestParam(required = false) Long driverId) {

        order.setClient(clientService.findById(clientId));

        if (driverId != null)
            order.setDriver(driverService.findById(driverId));

        orderService.save(order);

        return "redirect:/operator/orders";
    }

    @PostMapping("/orders/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {

        Order order = orderService.findById(id);

        order.setStatus(status);

        if (status == OrderStatus.COMPLETED) {
            order.setCompletedAt(LocalDateTime.now());
        }

        orderService.save(order);

        return "redirect:/operator/orders";
    }
}