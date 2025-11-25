package com.example.taxi_system.controller;
import com.example.taxi_system.entity.Order;
import com.example.taxi_system.entity.OrderStatus;
import com.example.taxi_system.service.ClientService;
import com.example.taxi_system.service.DriverService;
import com.example.taxi_system.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/operator/orders")
public class OperatorOrderController {

    private final OrderService orderService;
    private final ClientService clientService;
    private final DriverService driverService;

    public OperatorOrderController(OrderService orderService,
                                   ClientService clientService,
                                   DriverService driverService) {
        this.orderService = orderService;
        this.clientService = clientService;
        this.driverService = driverService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "operator/orders";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("drivers", driverService.findAll());
        model.addAttribute("actionUrl", "/operator/orders/add");
        return "operator/order-form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("order") Order order,
                      @RequestParam Long clientId,
                      @RequestParam(required = false) Long driverId) {

        order.setClient(clientService.findById(clientId));

        if (driverId != null)
            order.setDriver(driverService.findById(driverId));

        orderService.save(order);

        return "redirect:/operator/orders";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("drivers", driverService.findAll());
        model.addAttribute("actionUrl", "/operator/orders/edit/" + id);
        return "operator/order-form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @ModelAttribute("order") Order updated,
                       @RequestParam Long clientId,
                       @RequestParam(required = false) Long driverId) {

        Order o = orderService.findById(id);

        o.setPickupAddress(updated.getPickupAddress());
        o.setDestinationAddress(updated.getDestinationAddress());
        o.setPrice(updated.getPrice());

        o.setClient(clientService.findById(clientId));

        if (driverId != null) {
            o.setDriver(driverService.findById(driverId));
            o.setStatus(OrderStatus.ASSIGNED);
        } else {
            o.setDriver(null);
            o.setStatus(OrderStatus.NEW);
        }

        orderService.save(o);

        return "redirect:/operator/orders";
    }

    @PostMapping("/{id}/setStatus")
    public String setStatus(@PathVariable Long id, @RequestParam OrderStatus status) {

        Order order = orderService.findById(id);
        orderService.updateStatus(order, status);

        return "redirect:/operator/orders";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        orderService.deleteById(id);
        return "redirect:/operator/orders";
    }
}

