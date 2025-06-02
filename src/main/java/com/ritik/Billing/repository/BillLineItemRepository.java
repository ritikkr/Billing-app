package com.ritik.Billing.repository;

import com.ritik.Billing.model.BillLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillLineItemRepository extends JpaRepository<BillLineItem, Long> {
    // No specific methods needed initially beyond CRUD from JpaRepository
}
