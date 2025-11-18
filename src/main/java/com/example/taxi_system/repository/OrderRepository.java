package com.example.taxi_system.repository;

import com.example.taxi_system.entity.Order;
import com.example.taxi_system.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByClient_ClientId(Long clientId);

    List<Order> findByDriver_DriverId(Long driverId);

    List<Order> findByStatus(OrderStatus status);
}

