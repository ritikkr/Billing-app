package com.ritik.Billing.dto.bill;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillLineItemDto {
    @NotNull(message = "Item ID is required for a line item")
    private Long itemId;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.01", message = "Quantity must be greater than zero")
    private BigDecimal quantity;

    @NotNull
    @DecimalMin(value = "0.01", message = "Unit price must be greater than zero")
    private BigDecimal unitPriceAtTimeOfBilling; // Crucial: Price at time of bill creation

}
