package com.ritik.Billing.dto.item;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemRequest {
    @NotBlank(message = "Item name is required")
    @Size(max = 255)
    private String name;

    @NotBlank(message = "Item type is required")
    @Size(max = 50, message = "Item type must be up to 50 characters")
    private String type; // e.g., "Product", "Service"

    @NotBlank(message = "HSN/SAC code is required")
    @Size(max = 20, message = "HSN/SAC code must be up to 20 characters")
    private String hsnSacCode; // Harmonized System Code or Service Accounting Code

    @Size(max = 500)
    private String description;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.00", message = "Unit price must be positive")
    private BigDecimal unitPrice;

    @Size(max = 50)
    private String unitOfMeasure;

    private boolean taxable = true;
}
