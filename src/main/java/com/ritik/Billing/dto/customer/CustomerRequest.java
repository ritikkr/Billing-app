package com.ritik.Billing.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerRequest {
    @NotBlank(message = "Company name is required")
    @Size(max = 100)
    private String companyName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 255)
    private String email;

    @Size(max = 20)
    private String contact;

    @Size(max = 255)
    private String address;

    @Size(max = 50)
    private String taxId;

    @Size(max = 50)
    private String gstPreferences; // e.g., "Registered", "Unregistered"
}
