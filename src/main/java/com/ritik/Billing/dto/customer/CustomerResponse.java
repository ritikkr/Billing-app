package com.ritik.Billing.dto.customer;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CustomerResponse {
    private Long id;
    private String companyName;
    private String email;
    private String contact;
    private String address;
    private String gstPreferences; // e.g., "Registered", "Unregistered"
    private String taxId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
}
