package com.example.taxi_system.repository;

import com.example.taxi_system.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByDriverIsNull();
}
