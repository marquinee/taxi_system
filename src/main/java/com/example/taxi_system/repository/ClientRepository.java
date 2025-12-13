package com.example.taxi_system.repository;

import com.example.taxi_system.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByPhoneNumber(String phoneNumber);
    List<Client> findByActiveTrue();
    List<Client> findByFullNameContainingIgnoreCase(String fullName);
    List<Client> findByPhoneNumberContaining(String phoneNumber);
}

