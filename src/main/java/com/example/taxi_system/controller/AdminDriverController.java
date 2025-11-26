package com.example.taxi_system.controller;

import com.example.taxi_system.entity.Driver;
import com.example.taxi_system.service.DriverService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/drivers")
public class AdminDriverController {

    private final DriverService driverService;

    public AdminDriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("drivers", driverService.findActive());
        return "admin/drivers";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("driver", new Driver());
        model.addAttribute("actionUrl", "/admin/drivers/add");
        return "admin/driver-form";
    }


    @PostMapping("/add")
    public String add(@ModelAttribute("driver") Driver driver) {
        driverService.save(driver);
        return "redirect:/admin/drivers";
    }


    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("driver", driverService.findById(id));
        model.addAttribute("actionUrl", "/admin/drivers/edit/" + id);
        return "admin/driver-form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute("driver") Driver updated) {

        Driver d = driverService.findById(id);

        d.setFullName(updated.getFullName());
        d.setLicenseNumber(updated.getLicenseNumber());
        d.setPhoneNumber(updated.getPhoneNumber());
        d.setExperienceYears(updated.getExperienceYears());

        driverService.save(d);

        return "redirect:/admin/drivers";
    }

    @GetMapping("/deactivate/{id}")
    public String deactivate(@PathVariable Long id) {
        Driver driver = driverService.findById(id);
        driver.setActive(false);
        driverService.save(driver);
        return "redirect:/admin/drivers";
    }
}
