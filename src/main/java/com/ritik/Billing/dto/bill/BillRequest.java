package com.ritik.Billing.dto.bill;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BillRequest {
    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Issue Date is required")
    private LocalDate issueDate;

    @NotNull(message = "Due Date is required")
    @FutureOrPresent(message = "Due date cannot be in the past")
    private LocalDate dueDate;

    @Size(max = 1000)
    private String notes;

    @NotNull(message = "Bill must contain at least one line item")
    @Size(min = 1, message = "Bill must contain at least one line item")
    @Valid // Crucial for nested validation
    private List<BillLineItemDto> lineItems;

    // Optional: for manually setting currency if not using default
    private String currency;
}
