package com.example.taxi_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping("/about")
    public String about(Model model) {
        // Поля для заполнения автором
        model.addAttribute("fullName", "Фомина Марина Александровна");
        model.addAttribute("group", "ПИ23-1");
        model.addAttribute("college", "Финансовый университет при Правительстве РФ");
        model.addAttribute("contactInfo", "marinall11xx@gmail.com");
        model.addAttribute("experience", "В ходе выполнения проекта я получила практический опыт разработки серверной части на Java с использованием Spring Boot, а также работы с PostgreSQL, ORM (JPA/Hibernate) и шаблонизатором Thymeleaf для создания клиентского веб-интерфейса. В рамках проекта были реализованы многослойная архитектура, ролевая модель доступа, CRUD-операции, поиск, сортировка и бизнес-логика предметной области службы такси.");
        model.addAttribute("startDate", "30.10.2025");
        model.addAttribute("endDate", "14.12.2025");
        
        return "about";
    }
}

