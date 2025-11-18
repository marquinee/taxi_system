package com.example.taxi_system.service;

import com.example.taxi_system.entity.Client;
import com.example.taxi_system.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Client findById(Long clientId) {
        return repo.findById(clientId).orElseThrow();
    }

}
