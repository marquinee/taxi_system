package com.example.taxi_system.service;

import com.example.taxi_system.entity.Tariff;
import com.example.taxi_system.repository.TariffRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffService {

    private final TariffRepository repo;

    public TariffService(TariffRepository repo) {
        this.repo = repo;
    }

    public List<Tariff> findAll() { return repo.findAll(); }
    public List<Tariff> findActive() { return repo.findByActiveTrue(); }
    public Tariff findById(Long id) { return repo.findById(id).orElseThrow(); }
    public Tariff save(Tariff t) { return repo.save(t); }
    public void deleteById(Long id) { repo.deleteById(id); }
}

