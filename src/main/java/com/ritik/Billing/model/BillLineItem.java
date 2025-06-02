package com.ritik.Billing.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "bill_line_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BillLineItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false) // Link to the master Item
    private Item item;

    @Column(nullable = false)
    private String itemName; // Store snapshot of item name for historical integrity

    private String itemDescription; // Store snapshot of description

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal quantity;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal unitPriceAtTimeOfBilling; // Crucial: Price at time of bill creation

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal lineTotal; // quantity * unitPriceAtTimeOfBilling

    private boolean taxable; // Snapshot of item's taxable status
}
