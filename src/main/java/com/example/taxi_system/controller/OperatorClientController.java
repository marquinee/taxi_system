package com.example.taxi_system.controller;
import com.example.taxi_system.entity.Client;
import com.example.taxi_system.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/operator/clients")
public class OperatorClientController {

    private final ClientService clientService;

    public OperatorClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // Список клиентов
    @GetMapping
    public String list(Model model) {
        model.addAttribute("clients", clientService.findActive());
        return "operator/clients";
    }

    // Добавление — форма
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("actionUrl", "/operator/clients/add");
        return "operator/client-form";
    }

    // Добавление — отправка формы
    @PostMapping("/add")
    public String add(@ModelAttribute("client") Client client) {
        clientService.save(client);
        return "redirect:/operator/clients";
    }

    // Редактирование — форма
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("client", clientService.findById(id));
        model.addAttribute("actionUrl", "/operator/clients/edit/" + id);
        return "operator/client-form";
    }

    // Редактирование — отправка формы
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute("client") Client updated) {
        Client c = clientService.findById(id);

        c.setFullName(updated.getFullName());
        c.setPhoneNumber(updated.getPhoneNumber());
        c.setRegistrationDate(updated.getRegistrationDate());
        c.setNotes(updated.getNotes());

        clientService.save(c);

        return "redirect:/operator/clients";
    }

    // Вместо удаления - добавление в архив
    @GetMapping("/deactivate/{id}")
    public String deactivate(@PathVariable Long id) {
        Client client = clientService.findById(id);
        client.setActive(false);
        clientService.save(client);
        return "redirect:/operator/clients";
    }
}

