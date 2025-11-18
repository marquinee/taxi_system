package com.example.taxi_system.service;

import com.example.taxi_system.entity.Order;
import com.example.taxi_system.entity.OrderStatus;
import com.example.taxi_system.repository.OrderRepository;
import org.springframework.stereotype.Service;

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
}

