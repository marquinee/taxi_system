package com.example.taxi_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Точка входа в приложение системы управления такси.
 * <p>
 * Запускает Spring Boot и инициализирует все компоненты приложения.
 */
@SpringBootApplication
public class TaxiSystemApplication {

    /**
     * Главный метод запуска приложения.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(TaxiSystemApplication.class, args);
    }

}

