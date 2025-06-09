package com.ritik.Billing.dto.item;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ItemResponse {
    private Long id;
    private String name;
    private String type; // e.g., "Product", "Service"
    private String hsnSacCode; // Harmonized System Code or Service Accounting Code
    private String description;
    private BigDecimal unitPrice;
    private String unitOfMeasure;
    private boolean taxable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
}
