package com.example.ordermanagement.repository;


import com.example.ordermanagement.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
// This interface extends the JpaRepository interface provided by Spring Data JPA.
// It provides the basic CRUD operations and other database-related operations for the Order entity.

}

