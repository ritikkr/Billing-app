package com.ritik.Billing.repository;

import com.ritik.Billing.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByInvoiceNumber(String invoiceNumber);
}
