package com.ritik.Billing.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "customers")
@Data // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true) // Include BaseEntity fields in equals/hashCode
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String companyName; // Optional, for B2B customers
    private String taxId;       // e.g., GSTIN, VAT ID

    // You might add one-to-many relationship to Bills here, but it's often better
    // to manage relationships from the "many" side (Bill manages Customer reference).
}
