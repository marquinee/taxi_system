package com.example.taxi_system.controller;

import com.example.taxi_system.entity.Car;
import com.example.taxi_system.service.CarService;
import com.example.taxi_system.service.DriverService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/cars")
public class AdminCarController {

    private final CarService carService;
    private final DriverService driverService;

    public AdminCarController(CarService carService,
                              DriverService driverService) {
        this.carService = carService;
        this.driverService = driverService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("cars", carService.findAll());
        return "admin/cars";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("drivers", driverService.findAll());
        model.addAttribute("actionUrl", "/admin/cars/add");
        return "admin/car-form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Car car,
                      @RequestParam(required = false) Long driverId) {

        if (driverId != null)
            car.setDriver(driverService.findById(driverId));

        carService.save(car);
        return "redirect:/admin/cars";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Car car = carService.findById(id);

        model.addAttribute("car", car);
        model.addAttribute("drivers", driverService.findAll());
        model.addAttribute("actionUrl", "/admin/cars/edit/" + id);
        return "admin/car-form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @ModelAttribute Car updated,
                       @RequestParam(required = false) Long driverId) {

        Car car = carService.findById(id);

        car.setModel(updated.getModel());
        car.setYear(updated.getYear());
        car.setLicensePlate(updated.getLicensePlate());

        if (driverId != null) {
            car.setDriver(driverService.findById(driverId));
        } else {
            car.setDriver(null);
        }

        carService.save(car);
        return "redirect:/admin/cars";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        carService.deleteById(id);
        return "redirect:/admin/cars";
    }
}

