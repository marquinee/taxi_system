package com.example.taxi_system.service;

import com.example.taxi_system.entity.User;
import com.example.taxi_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
