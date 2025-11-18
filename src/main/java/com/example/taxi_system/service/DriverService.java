package com.example.taxi_system.service;

import com.example.taxi_system.entity.Driver;
import com.example.taxi_system.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {
    private final DriverRepository repo;
    public DriverService(DriverRepository repo) {
        this.repo = repo;
    }

    public List<Driver> findAll() {return repo.findAll();}
    public Driver save(Driver d) {return repo.save(d);}

    public Driver findById(Long driverId) {
        return repo.findById(driverId).orElseThrow();
    }
}
