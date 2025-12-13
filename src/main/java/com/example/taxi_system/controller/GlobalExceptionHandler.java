package com.example.taxi_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementException(NoSuchElementException e, Model model) {
        model.addAttribute("errorCode", "404");
        model.addAttribute("errorTitle", "Запись не найдена");
        model.addAttribute("errorMessage", "Запрашиваемая запись не существует в системе.");
        model.addAttribute("errorSuggestion", "Проверьте правильность введенных данных или вернитесь на предыдущую страницу.");
        return "error/404";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
        model.addAttribute("errorCode", "400");
        model.addAttribute("errorTitle", "Некорректный запрос");
        model.addAttribute("errorMessage", e.getMessage() != null ? e.getMessage() : "Введены некорректные данные.");
        model.addAttribute("errorSuggestion", "Проверьте правильность введенных данных и попробуйте снова.");
        return "error/error";
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRuntimeException(RuntimeException e, Model model) {
        model.addAttribute("errorCode", "500");
        model.addAttribute("errorTitle", "Внутренняя ошибка");
        model.addAttribute("errorMessage", "Произошла непредвиденная ошибка: " + (e.getMessage() != null ? e.getMessage() : "Неизвестная ошибка"));
        model.addAttribute("errorSuggestion", "Попробуйте обновить страницу или вернитесь на главную.");
        return "error/500";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e, Model model) {
        model.addAttribute("errorCode", "500");
        model.addAttribute("errorTitle", "Произошла ошибка");
        model.addAttribute("errorMessage", "Произошла непредвиденная ошибка. Мы уже работаем над её устранением.");
        model.addAttribute("errorSuggestion", "Попробуйте обновить страницу или вернитесь на главную.");
        return "error/500";
    }
}

