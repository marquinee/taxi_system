package com.example.taxi_system.service;

import com.example.taxi_system.entity.Car;
import com.example.taxi_system.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    
    public List<Car> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return findAll();
        }
        String searchTerm = query.trim();
        List<Car> byModel = repo.findByModelContainingIgnoreCase(searchTerm);
        List<Car> byLicensePlate = repo.findByLicensePlateContainingIgnoreCase(searchTerm);
        
        return java.util.stream.Stream.concat(byModel.stream(), byLicensePlate.stream())
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
    }
    
    public List<Car> searchAndSort(String query, String sortBy, String sortDir) {
        List<Car> results = search(query);
        return sort(results, sortBy, sortDir);
    }
    
    public List<Car> sort(List<Car> cars, String sortBy, String sortDir) {
        if (sortBy == null || sortBy.isEmpty()) {
            return cars;
        }
        
        Comparator<Car> comparator = null;
        
        switch (sortBy) {
            case "model":
                comparator = Comparator.comparing(c -> c.getModel() != null ? c.getModel().toLowerCase() : "");
                break;
            case "year":
                comparator = Comparator.comparing(c -> c.getYear() != null ? c.getYear() : 0);
                break;
            case "licensePlate":
                comparator = Comparator.comparing(c -> c.getLicensePlate() != null ? c.getLicensePlate().toLowerCase() : "");
                break;
            default:
                return cars;
        }
        
        if ("desc".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }
        
        return cars.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
