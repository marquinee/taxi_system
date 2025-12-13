package com.example.taxi_system.service;

import com.example.taxi_system.entity.Order;
import com.example.taxi_system.entity.OrderStatus;
import com.example.taxi_system.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository repo;

    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }

    public Order save(Order order) {
        return repo.save(order);
    }

    public List<Order> findAll() {
        return repo.findAll();
    }

    public List<Order> findByStatus(OrderStatus status) {
        return repo.findByStatus(status);
    }

    public Order findById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
    public void updateStatus(Order order, OrderStatus status) {
        order.setStatus(status);

        if (status == OrderStatus.COMPLETED) {
            order.setCompletedAt(LocalDateTime.now());
        }

        repo.save(order);
    }

    public Object findByDriverId(Long driverId) {
        return repo.findByDriver_DriverId(driverId);
    }
    
    public List<Order> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return findAll();
        }
        String searchTerm = query.trim();
        List<Order> byPickup = repo.findByPickupAddressContainingIgnoreCase(searchTerm);
        List<Order> byDestination = repo.findByDestinationAddressContainingIgnoreCase(searchTerm);
        
        return java.util.stream.Stream.concat(byPickup.stream(), byDestination.stream())
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
    }
    
    public List<Order> searchAndSort(String query, String sortBy, String sortDir) {
        List<Order> results = search(query);
        return sort(results, sortBy, sortDir);
    }
    
    public List<Order> sort(List<Order> orders, String sortBy, String sortDir) {
        if (sortBy == null || sortBy.isEmpty()) {
            return orders;
        }
        
        Comparator<Order> comparator = null;
        
        switch (sortBy) {
            case "createdAt":
                comparator = Comparator.comparing(o -> o.getCreatedAt() != null ? o.getCreatedAt() : LocalDateTime.MIN);
                break;
            case "price":
                comparator = Comparator.comparing(o -> o.getPrice() != null ? o.getPrice() : BigDecimal.ZERO);
                break;
            case "status":
                comparator = Comparator.comparing(o -> o.getStatus() != null ? o.getStatus().name() : "");
                break;
            default:
                return orders;
        }
        
        if ("desc".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }
        
        return orders.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}

