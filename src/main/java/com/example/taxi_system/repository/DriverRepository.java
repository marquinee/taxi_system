package com.example.taxi_system.repository;

import com.example.taxi_system.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findByActiveTrue();
    List<Driver> findByFullNameContainingIgnoreCase(String fullName);
    List<Driver> findByPhoneNumberContaining(String phoneNumber);
    List<Driver> findByLicenseNumberContainingIgnoreCase(String licenseNumber);
}
