package com.example.taxi_system.controller;

import com.example.taxi_system.entity.Tariff;
import com.example.taxi_system.service.TariffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/tariffs")
public class AdminTariffController {

    private final TariffService tariffService;

    public AdminTariffController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tariffs", tariffService.findAll());
        return "admin/tariffs";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("tariff", new Tariff());
        model.addAttribute("actionUrl", "/admin/tariffs/add");
        return "admin/tariff-form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Tariff t) {
        tariffService.save(t);
        return "redirect:/admin/tariffs";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("tariff", tariffService.findById(id));
        model.addAttribute("actionUrl", "/admin/tariffs/edit/" + id);
        return "admin/tariff-form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute Tariff updated) {
        Tariff t = tariffService.findById(id);

        t.setName(updated.getName());
        t.setBaseRate(updated.getBaseRate());
        t.setPerKmRate(updated.getPerKmRate());
        t.setActive(updated.isActive());

        tariffService.save(t);

        return "redirect:/admin/tariffs";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        tariffService.deleteById(id);
        return "redirect:/admin/tariffs";
    }
}
