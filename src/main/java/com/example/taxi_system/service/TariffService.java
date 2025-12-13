package com.example.taxi_system.service;

import com.example.taxi_system.entity.Tariff;
import com.example.taxi_system.repository.TariffRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    
    public List<Tariff> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return findAll();
        }
        String searchTerm = query.trim();
        return repo.findByNameContainingIgnoreCase(searchTerm);
    }
    
    public List<Tariff> searchAndSort(String query, String sortBy, String sortDir) {
        List<Tariff> results = search(query);
        return sort(results, sortBy, sortDir);
    }
    
    public List<Tariff> sort(List<Tariff> tariffs, String sortBy, String sortDir) {
        if (sortBy == null || sortBy.isEmpty()) {
            return tariffs;
        }
        
        Comparator<Tariff> comparator = null;
        
        switch (sortBy) {
            case "name":
                comparator = Comparator.comparing(t -> t.getName() != null ? t.getName().toLowerCase() : "");
                break;
            case "baseRate":
                comparator = Comparator.comparing(t -> t.getBaseRate() != null ? t.getBaseRate() : BigDecimal.ZERO);
                break;
            case "perKmRate":
                comparator = Comparator.comparing(t -> t.getPerKmRate() != null ? t.getPerKmRate() : BigDecimal.ZERO);
                break;
            default:
                return tariffs;
        }
        
        if ("desc".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }
        
        return tariffs.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}

