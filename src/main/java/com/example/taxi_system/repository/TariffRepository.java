package com.example.taxi_system.repository;

import com.example.taxi_system.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
    List<Tariff> findByActiveTrue();
    List<Tariff> findByNameContainingIgnoreCase(String name);
}
