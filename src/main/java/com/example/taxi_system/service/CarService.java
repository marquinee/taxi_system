package com.example.taxi_system.service;

import com.example.taxi_system.entity.Car;
import com.example.taxi_system.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarRepository repo;
    public CarService(CarRepository repo) {
        this.repo = repo;
    }
    public List<Car> findAll() { return repo.findAll(); }
    public Car findById(Long id) { return repo.findById(id).orElseThrow(); }
    public Car save(Car c) { return repo.save(c); }
    public void deleteById(Long id) { repo.deleteById(id); }
}
