package com.ritik.Billing.repository;

import com.ritik.Billing.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    // Add custom queries here if needed, e.g., for pagination with active status
}
