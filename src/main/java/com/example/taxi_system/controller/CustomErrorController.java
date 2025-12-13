package com.example.taxi_system.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorCode", "404");
                model.addAttribute("errorTitle", "Страница не найдена");
                model.addAttribute("errorMessage", "Запрашиваемая страница не существует или была перемещена.");
                model.addAttribute("errorSuggestion", "Проверьте правильность введенного адреса или вернитесь на главную страницу.");
                return "error/404";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("errorCode", "403");
                model.addAttribute("errorTitle", "Доступ запрещен");
                model.addAttribute("errorMessage", "У вас нет прав для доступа к этой странице.");
                model.addAttribute("errorSuggestion", "Обратитесь к администратору для получения необходимых прав доступа.");
                return "error/403";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("errorCode", "500");
                model.addAttribute("errorTitle", "Внутренняя ошибка сервера");
                model.addAttribute("errorMessage", "Произошла внутренняя ошибка сервера. Мы уже работаем над её устранением.");
                model.addAttribute("errorSuggestion", "Попробуйте обновить страницу или вернитесь позже.");
                return "error/500";
            }
        }
        
        model.addAttribute("errorCode", "Ошибка");
        model.addAttribute("errorTitle", "Произошла ошибка");
        model.addAttribute("errorMessage", "Что-то пошло не так. Пожалуйста, попробуйте позже.");
        model.addAttribute("errorSuggestion", "Вернитесь на главную страницу или попробуйте обновить страницу.");
        return "error/error";
    }
}

