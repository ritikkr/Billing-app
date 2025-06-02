package com.ritik.Billing.service;


import com.ritik.Billing.dto.bill.BillLineItemDto;
import com.ritik.Billing.dto.bill.BillLineItemDetailsDto;
import com.ritik.Billing.dto.bill.BillRequest;
import com.ritik.Billing.dto.bill.BillResponse;
import com.ritik.Billing.enums.BillStatus;
import com.ritik.Billing.exception.ResourceNotFoundException;
import com.ritik.Billing.model.Bill;
import com.ritik.Billing.model.BillLineItem;
import com.ritik.Billing.model.Customer;
import com.ritik.Billing.model.Item;
import com.ritik.Billing.repository.BillRepository;
import com.ritik.Billing.repository.CustomerRepository;
import com.ritik.Billing.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillingService {

    private final BillRepository billRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;

    // Assuming a simple tax rate for now, complex tax logic would be a separate service
    private static final BigDecimal TAX_RATE = new BigDecimal("0.05"); // 5% tax

    public BillingService(BillRepository billRepository, CustomerRepository customerRepository, ItemRepository itemRepository) {
        this.billRepository = billRepository;
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public BillResponse createBill(BillRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + request.getCustomerId()));

        Bill bill = new Bill();
        bill.setCustomer(customer);
        bill.setIssueDate(request.getIssueDate());
        bill.setDueDate(request.getDueDate());
        bill.setStatus(BillStatus.DRAFT); // Start as DRAFT
        bill.setNotes(request.getNotes());
        bill.setCurrency(request.getCurrency() != null ? request.getCurrency() : "USD"); // Default to USD

        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal taxAmount = BigDecimal.ZERO;
        BigDecimal discountAmount = BigDecimal.ZERO; // Placeholder for now

        for (BillLineItemDto lineItemDto : request.getLineItems()) {
            Item item = itemRepository.findById(lineItemDto.getItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + lineItemDto.getItemId()));

            BillLineItem billLineItem = new BillLineItem();
            billLineItem.setItem(item);
            billLineItem.setItemName(item.getName());
            billLineItem.setItemDescription(item.getDescription());
            billLineItem.setQuantity(lineItemDto.getQuantity());
            billLineItem.setUnitPriceAtTimeOfBilling(item.getUnitPrice()); // Snapshot item price
            billLineItem.setTaxable(item.isTaxable());

            BigDecimal lineTotal = lineItemDto.getQuantity().multiply(item.getUnitPrice())
                    .setScale(2, RoundingMode.HALF_UP);
            billLineItem.setLineTotal(lineTotal);

            subtotal = subtotal.add(lineTotal);
            if (item.isTaxable()) {
                taxAmount = taxAmount.add(lineTotal.multiply(TAX_RATE));
            }
            bill.addLineItem(billLineItem); // Add to bill's line items and set bidirectional reference
        }

        bill.setSubtotal(subtotal.setScale(2, RoundingMode.HALF_UP));
        bill.setTaxAmount(taxAmount.setScale(2, RoundingMode.HALF_UP));
        bill.setDiscountAmount(discountAmount.setScale(2, RoundingMode.HALF_UP)); // No discounts for now
        bill.setTotalAmount(subtotal.add(taxAmount).subtract(discountAmount).setScale(2, RoundingMode.HALF_UP));

        // Generate invoice number (simple example: prefix + timestamp + random)
        bill.setInvoiceNumber("INV-" + System.currentTimeMillis()); // In real app, use a dedicated sequence/generator

        Bill savedBill = billRepository.save(bill);
        return mapToBillResponse(savedBill);
    }

    @Transactional(readOnly = true)
    public BillResponse getBillById(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with ID: " + id));
        return mapToBillResponse(bill);
    }

    @Transactional(readOnly = true)
    public List<BillResponse> getAllBills(boolean includeInactive) {
        List<Bill> bills = includeInactive ? billRepository.findAll() :
                billRepository.findAll().stream().filter(Bill::isActive).collect(Collectors.toList());
        return bills.stream()
                .map(this::mapToBillResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public BillResponse updateBillStatus(Long id, BillStatus newStatus) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with ID: " + id));

        // Add business rules for status transitions, e.g., cannot go from PAID to DRAFT
        if (bill.getStatus() == BillStatus.PAID && (newStatus == BillStatus.DRAFT || newStatus == BillStatus.ISSUED)) {
            throw new IllegalArgumentException("Cannot change status from PAID to DRAFT or ISSUED.");
        }
        // More complex logic for CANCELLED/VOID etc.
        bill.setStatus(newStatus);
        Bill updatedBill = billRepository.save(bill);
        return mapToBillResponse(updatedBill);
    }

    @Transactional
    public void softDeleteBill(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with ID: " + id));
        bill.setActive(false); // Soft delete
        billRepository.save(bill);
    }

    // Helper method to map Entity to Response DTO
    private BillResponse mapToBillResponse(Bill bill) {
        BillResponse response = new BillResponse();
        response.setId(bill.getId());
        response.setInvoiceNumber(bill.getInvoiceNumber());
        response.setCustomerId(bill.getCustomer().getId());
        response.setCustomerName(bill.getCustomer().getFirstName() + " " + bill.getCustomer().getLastName());
        response.setIssueDate(bill.getIssueDate());
        response.setDueDate(bill.getDueDate());
        response.setStatus(bill.getStatus());
        response.setSubtotal(bill.getSubtotal());
        response.setTaxAmount(bill.getTaxAmount());
        response.setDiscountAmount(bill.getDiscountAmount());
        response.setTotalAmount(bill.getTotalAmount());
        response.setCurrency(bill.getCurrency());
        response.setNotes(bill.getNotes());
        response.setCreatedAt(bill.getCreatedAt());
        response.setUpdatedAt(bill.getUpdatedAt());
        response.setActive(bill.isActive());

        response.setLineItems(bill.getLineItems().stream()
                .map(this::mapToBillLineItemDetailsDto)
                .collect(Collectors.toList()));
        return response;
    }

    private BillLineItemDetailsDto mapToBillLineItemDetailsDto(BillLineItem lineItem) {
        BillLineItemDetailsDto dto = new BillLineItemDetailsDto();
        dto.setId(lineItem.getId());
        dto.setItemId(lineItem.getItem().getId());
        dto.setItemName(lineItem.getItemName());
        dto.setItemDescription(lineItem.getItemDescription());
        dto.setQuantity(lineItem.getQuantity());
        dto.setUnitPriceAtTimeOfBilling(lineItem.getUnitPriceAtTimeOfBilling());
        dto.setLineTotal(lineItem.getLineTotal());
        dto.setTaxable(lineItem.isTaxable());
        return dto;
    }
}
