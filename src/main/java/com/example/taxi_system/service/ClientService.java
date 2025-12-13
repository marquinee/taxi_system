package com.example.taxi_system.service;

import com.example.taxi_system.entity.Client;
import com.example.taxi_system.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private final ClientRepository repo;
    public ClientService(ClientRepository repo) {
        this.repo = repo;
    }

    public List<Client> findAll() {
        return repo.findAll();
    }
    public Client save(Client client) {
        return repo.save(client);
    }
    public Optional<Client> findByPhoneNumber(String phoneNumber) {
        return repo.findByPhoneNumber(phoneNumber);
    }
    public List<Client> findActive() {
        return repo.findByActiveTrue();
    }

    public Client findById(Long clientId) {
        return repo.findById(clientId).orElseThrow();
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
    
    public List<Client> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return findActive();
        }
        String searchTerm = query.trim();
        List<Client> byName = repo.findByFullNameContainingIgnoreCase(searchTerm);
        List<Client> byPhone = repo.findByPhoneNumberContaining(searchTerm);
        
        return java.util.stream.Stream.concat(byName.stream(), byPhone.stream())
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
    }
    
    public List<Client> searchAndSort(String query, String sortBy, String sortDir) {
        List<Client> results = search(query);
        return sort(results, sortBy, sortDir);
    }
    
    public List<Client> sort(List<Client> clients, String sortBy, String sortDir) {
        if (sortBy == null || sortBy.isEmpty()) {
            return clients;
        }
        
        Comparator<Client> comparator = null;
        
        switch (sortBy) {
            case "fullName":
                comparator = Comparator.comparing(c -> c.getFullName() != null ? c.getFullName().toLowerCase() : "");
                break;
            case "registrationDate":
                comparator = Comparator.comparing(c -> c.getRegistrationDate() != null ? c.getRegistrationDate() : LocalDate.MIN);
                break;
            case "phoneNumber":
                comparator = Comparator.comparing(c -> c.getPhoneNumber() != null ? c.getPhoneNumber() : "");
                break;
            default:
                return clients;
        }
        
        if ("desc".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }
        
        return clients.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
