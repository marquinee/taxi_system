package com.example.taxi_system.service;

import com.example.taxi_system.entity.Order;
import com.example.taxi_system.entity.OrderStatus;
import com.example.taxi_system.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repo;

    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }

    public Order save(Order order) {
        return repo.save(order);
    }

    public List<Order> findAll() {
        return repo.findAll();
    }

    public List<Order> findByStatus(OrderStatus status) {
        return repo.findByStatus(status);
    }

    public Order findById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
    public void updateStatus(Order order, OrderStatus status) {
        order.setStatus(status);

        if (status == OrderStatus.COMPLETED) {
            order.setCompletedAt(LocalDateTime.now());
        }

        repo.save(order);
    }

    public Object findByDriverId(Long driverId) {
        return repo.findByDriver_DriverId(driverId);
    }}

