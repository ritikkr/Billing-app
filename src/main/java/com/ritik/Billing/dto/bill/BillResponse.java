package com.ritik.Billing.dto.bill;

import com.ritik.Billing.enums.BillStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BillResponse {
    private Long id;
    private String invoiceNumber;
    private Long customerId; // Just the ID, not full customer object
    private String customerName; // For convenience
    private LocalDate issueDate;
    private LocalDate dueDate;
    private BillStatus status;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
    private String currency;
    private String notes;
    private List<BillLineItemDetailsDto> lineItems; // A new DTO for response line items
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
}
