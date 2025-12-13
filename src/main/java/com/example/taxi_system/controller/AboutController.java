package com.example.taxi_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping("/about")
    public String about(Model model) {
        // Поля для заполнения автором
        model.addAttribute("fullName", "ФИО автора");
        model.addAttribute("group", "Группа/Учебное заведение");
        model.addAttribute("contactInfo", "Контактные данные (email, телефон и т.д.)");
        model.addAttribute("experience", "Краткое описание опыта работы с технологиями, использованными в проекте");
        model.addAttribute("startDate", "Дата начала работ");
        model.addAttribute("endDate", "Дата завершения работ");
        
        return "about";
    }
}

