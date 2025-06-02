package com.ritik.Billing.enums;

public enum BillStatus {
    DRAFT,      // Bill is being created
    ISSUED,     // Bill has been sent to customer
    PAID,       // Payment has been received
    VOID,       // Bill was created in error and cancelled before payment
    CANCELLED   // Bill was cancelled after being issued (e.g., by customer request)
}
