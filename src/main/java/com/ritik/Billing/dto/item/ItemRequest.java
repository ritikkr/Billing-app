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

    @Size(max = 500)
    private String description;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.00", message = "Unit price must be positive")
    private BigDecimal unitPrice;

    @Size(max = 50)
    private String unitOfMeasure;

    @Size(max = 100)
    private String sku;

    private boolean taxable = true;
}
