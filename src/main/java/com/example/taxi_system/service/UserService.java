package com.example.taxi_system.service;

import com.example.taxi_system.entity.User;
import com.example.taxi_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }
    public User save(User user) {
        return repo.save(user);
    }
    public List<User> findAll() {
        return repo.findAll();
    }
    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }
    public User findById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public List<User> findAllDriversOnly() {
        return repo.findByRole("DRIVER");
    }
    
    public List<User> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return findAll();
        }
        String searchTerm = query.trim();
        List<User> byUsername = repo.findByUsernameContainingIgnoreCase(searchTerm);
        List<User> byRole = repo.findByRoleContainingIgnoreCase(searchTerm);
        
        return java.util.stream.Stream.concat(byUsername.stream(), byRole.stream())
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
    }
    
    public List<User> searchAndSort(String query, String sortBy, String sortDir) {
        List<User> results = search(query);
        return sort(results, sortBy, sortDir);
    }
    
    public List<User> sort(List<User> users, String sortBy, String sortDir) {
        if (sortBy == null || sortBy.isEmpty()) {
            return users;
        }
        
        Comparator<User> comparator = null;
        
        switch (sortBy) {
            case "username":
                comparator = Comparator.comparing(u -> u.getUsername() != null ? u.getUsername().toLowerCase() : "");
                break;
            case "role":
                comparator = Comparator.comparing(u -> u.getRole() != null ? u.getRole().toLowerCase() : "");
                break;
            default:
                return users;
        }
        
        if ("desc".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }
        
        return users.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
