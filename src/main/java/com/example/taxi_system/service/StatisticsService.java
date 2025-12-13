package com.example.taxi_system.service;

import com.example.taxi_system.entity.Driver;
import com.example.taxi_system.entity.Order;
import com.example.taxi_system.entity.OrderStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private final UserService userService;
    private final DriverService driverService;
    private final ClientService clientService;
    private final OrderService orderService;

    public StatisticsService(UserService userService,
                             DriverService driverService,
                             ClientService clientService,
                             OrderService orderService) {
        this.userService = userService;
        this.driverService = driverService;
        this.clientService = clientService;
        this.orderService = orderService;
    }

    // Статистика для админа
    public Map<String, Object> getAdminStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        // Общее количество пользователей
        long totalUsers = userService.findAll().size();
        stats.put("totalUsers", totalUsers);
        
        // Количество пользователей по ролям
        Map<String, Long> usersByRole = userService.findAll().stream()
                .collect(Collectors.groupingBy(
                    u -> u.getRole(),
                    Collectors.counting()
                ));
        stats.put("usersByRole", usersByRole);
        
        // Общее количество водителей
        long totalDrivers = driverService.findAll().size();
        stats.put("totalDrivers", totalDrivers);
        
        // Активные водители
        long activeDrivers = driverService.findActive().size();
        stats.put("activeDrivers", activeDrivers);
        
        // Средний стаж водителей
        List<Driver> drivers = driverService.findAll();
        double avgExperience = drivers.isEmpty() ? 0.0 :
            drivers.stream()
                .mapToInt(Driver::getExperienceYears)
                .average()
                .orElse(0.0);
        stats.put("avgExperience", Math.round(avgExperience * 10.0) / 10.0);
        
        // Статистика по заказам для расчета среднего времени ожидания
        List<Order> allOrders = orderService.findAll();
        List<Order> completedOrders = allOrders.stream()
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED 
                        && o.getCreatedAt() != null 
                        && o.getCompletedAt() != null)
                .collect(Collectors.toList());
        
        // Среднее время выполнения заказа (от создания до завершения)
        double avgCompletionTime = 0.0;
        if (!completedOrders.isEmpty()) {
            double totalMinutes = completedOrders.stream()
                    .mapToLong(o -> Duration.between(o.getCreatedAt(), o.getCompletedAt()).toMinutes())
                    .sum();
            avgCompletionTime = totalMinutes / completedOrders.size();
        }
        stats.put("avgCompletionTimeMinutes", Math.round(avgCompletionTime * 10.0) / 10.0);
        
        stats.put("totalOrders", allOrders.size());
        stats.put("completedOrders", completedOrders.size());
        
        return stats;
    }

    // Статистика для оператора
    public Map<String, Object> getOperatorStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        // Общее количество клиентов
        long totalClients = clientService.findAll().size();
        stats.put("totalClients", totalClients);
        
        // Активные клиенты
        long activeClients = clientService.findActive().size();
        stats.put("activeClients", activeClients);
        
        // Общее количество заказов
        List<Order> allOrders = orderService.findAll();
        stats.put("totalOrders", allOrders.size());
        
        // Заказы по статусам
        Map<String, Long> ordersByStatus = allOrders.stream()
                .collect(Collectors.groupingBy(
                    o -> o.getStatus().name(),
                    Collectors.counting()
                ));
        stats.put("ordersByStatus", ordersByStatus);
        
        // Среднее время выполнения заказа
        List<Order> completedOrders = allOrders.stream()
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED 
                        && o.getCreatedAt() != null 
                        && o.getCompletedAt() != null)
                .collect(Collectors.toList());
        
        double avgCompletionTime = 0.0;
        if (!completedOrders.isEmpty()) {
            double totalMinutes = completedOrders.stream()
                    .mapToLong(o -> Duration.between(o.getCreatedAt(), o.getCompletedAt()).toMinutes())
                    .sum();
            avgCompletionTime = totalMinutes / completedOrders.size();
        }
        stats.put("avgCompletionTimeMinutes", Math.round(avgCompletionTime * 10.0) / 10.0);
        
        // Средняя стоимость заказа
        double avgPrice = allOrders.stream()
                .filter(o -> o.getPrice() != null)
                .mapToDouble(o -> o.getPrice().doubleValue())
                .average()
                .orElse(0.0);
        stats.put("avgPrice", Math.round(avgPrice * 100.0) / 100.0);
        
        // Общая выручка
        double totalRevenue = allOrders.stream()
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED && o.getPrice() != null)
                .mapToDouble(o -> o.getPrice().doubleValue())
                .sum();
        stats.put("totalRevenue", Math.round(totalRevenue * 100.0) / 100.0);
        
        return stats;
    }

    // Статистика для водителя
    public Map<String, Object> getDriverStatistics(Long driverId) {
        Map<String, Object> stats = new HashMap<>();
        
        List<Order> driverOrders = (List<Order>) orderService.findByDriverId(driverId);
        stats.put("totalOrders", driverOrders.size());
        
        // Заказы по статусам
        Map<String, Long> ordersByStatus = driverOrders.stream()
                .collect(Collectors.groupingBy(
                    o -> o.getStatus().name(),
                    Collectors.counting()
                ));
        stats.put("ordersByStatus", ordersByStatus);
        
        // Завершенные заказы
        List<Order> completedOrders = driverOrders.stream()
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
                .collect(Collectors.toList());
        stats.put("completedOrders", completedOrders.size());
        
        // Среднее время выполнения заказа
        List<Order> completedWithTime = completedOrders.stream()
                .filter(o -> o.getCreatedAt() != null && o.getCompletedAt() != null)
                .collect(Collectors.toList());
        
        double avgCompletionTime = 0.0;
        if (!completedWithTime.isEmpty()) {
            double totalMinutes = completedWithTime.stream()
                    .mapToLong(o -> Duration.between(o.getCreatedAt(), o.getCompletedAt()).toMinutes())
                    .sum();
            avgCompletionTime = totalMinutes / completedWithTime.size();
        }
        stats.put("avgCompletionTimeMinutes", Math.round(avgCompletionTime * 10.0) / 10.0);
        
        // Общий заработок
        double totalEarnings = completedOrders.stream()
                .filter(o -> o.getPrice() != null)
                .mapToDouble(o -> o.getPrice().doubleValue())
                .sum();
        stats.put("totalEarnings", Math.round(totalEarnings * 100.0) / 100.0);
        
        // Средняя стоимость заказа
        double avgPrice = completedOrders.stream()
                .filter(o -> o.getPrice() != null)
                .mapToDouble(o -> o.getPrice().doubleValue())
                .average()
                .orElse(0.0);
        stats.put("avgPrice", Math.round(avgPrice * 100.0) / 100.0);
        
        return stats;
    }
}

