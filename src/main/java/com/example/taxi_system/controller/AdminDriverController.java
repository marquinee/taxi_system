package com.example.taxi_system.controller;

import com.example.taxi_system.entity.Driver;
import com.example.taxi_system.entity.User;
import com.example.taxi_system.service.DriverService;
import com.example.taxi_system.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/drivers")
public class AdminDriverController {

    private final DriverService driverService;
    private final UserService userService;

    public AdminDriverController(DriverService driverService, UserService userService) {
        this.driverService = driverService;
        this.userService = userService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String search,
                       @RequestParam(required = false) String sortBy,
                       @RequestParam(required = false, defaultValue = "asc") String sortDir,
                       Model model) {
        List<com.example.taxi_system.entity.Driver> drivers;
        if (search != null && !search.trim().isEmpty()) {
            drivers = driverService.searchAndSort(search.trim(), sortBy, sortDir);
        } else {
            drivers = driverService.sort(driverService.findActive(), sortBy, sortDir);
        }
        model.addAttribute("drivers", drivers);
        model.addAttribute("search", search);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        return "admin/drivers";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("driver", new Driver());
        // список пользователей с ролью DRIVER
        model.addAttribute("users", userService.findAllDriversOnly());
        model.addAttribute("actionUrl", "/admin/drivers/add");
        return "admin/driver-form";
    }


    @PostMapping("/add")
    public String add(@ModelAttribute("driver") Driver driver,
                      @RequestParam Long userId) {

        User user = userService.findById(userId);
        driver.setUser(user);

        driverService.save(driver);
        return "redirect:/admin/drivers";
    }



    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("driver", driverService.findById(id));
        // список пользователей
        model.addAttribute("users", userService.findAllDriversOnly());
        model.addAttribute("actionUrl", "/admin/drivers/edit/" + id);
        return "admin/driver-form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute("driver") Driver updated, @RequestParam Long userId) {

        Driver driver = driverService.findById(id);

        driver.setFullName(updated.getFullName());
        driver.setLicenseNumber(updated.getLicenseNumber());
        driver.setPhoneNumber(updated.getPhoneNumber());
        driver.setExperienceYears(updated.getExperienceYears());

        User user = userService.findById(userId);
        driver.setUser(user);

        driverService.save(driver);

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
