package com.ritik.Billing.dto.bill;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BillLineItemDetailsDto {
    private Long id;
    private Long itemId;
    private String itemName;
    private String itemDescription;
    private BigDecimal quantity;
    private BigDecimal unitPriceAtTimeOfBilling;
    private BigDecimal lineTotal;
    private boolean taxable;
    private String hsnSacCode;
}
