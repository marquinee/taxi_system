package com.example.taxi_system.service;

import com.example.taxi_system.entity.Driver;
import com.example.taxi_system.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverService {
    private final DriverRepository repo;
    public DriverService(DriverRepository repo) {
        this.repo = repo;
    }

    public List<Driver> findAll() {return repo.findAll();}
    public List<Driver> findActive() {
        return repo.findByActiveTrue();
    }
    public Driver save(Driver d) {return repo.save(d);}

    public Driver findById(Long driverId) {
        return repo.findById(driverId).orElseThrow();
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
    
    public List<Driver> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return findActive();
        }
        String searchTerm = query.trim();
        List<Driver> byName = repo.findByFullNameContainingIgnoreCase(searchTerm);
        List<Driver> byPhone = repo.findByPhoneNumberContaining(searchTerm);
        List<Driver> byLicense = repo.findByLicenseNumberContainingIgnoreCase(searchTerm);
        
        return java.util.stream.Stream.concat(
                java.util.stream.Stream.concat(byName.stream(), byPhone.stream()),
                byLicense.stream())
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
    }
    
    public List<Driver> searchAndSort(String query, String sortBy, String sortDir) {
        List<Driver> results = search(query);
        return sort(results, sortBy, sortDir);
    }
    
    public List<Driver> sort(List<Driver> drivers, String sortBy, String sortDir) {
        if (sortBy == null || sortBy.isEmpty()) {
            return drivers;
        }
        
        Comparator<Driver> comparator = null;
        
        switch (sortBy) {
            case "fullName":
                comparator = Comparator.comparing(d -> d.getFullName() != null ? d.getFullName().toLowerCase() : "");
                break;
            case "experienceYears":
                comparator = Comparator.comparingInt(Driver::getExperienceYears);
                break;
            case "phoneNumber":
                comparator = Comparator.comparing(d -> d.getPhoneNumber() != null ? d.getPhoneNumber() : "");
                break;
            default:
                return drivers;
        }
        
        if ("desc".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }
        
        return drivers.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
