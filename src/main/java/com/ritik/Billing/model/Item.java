package com.ritik.Billing.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String type; // e.g., "product", "service"

    private String hsnSacCode; // Harmonized System Code or Service Accounting Code

    private String description;

    @Column(nullable = false, precision = 19, scale = 4) // Precision for currency
    private BigDecimal unitPrice;

    private String unitOfMeasure; // e.g., "piece", "hour", "kg", "service"

    private boolean taxable = true; // Is this item subject to tax?
}
