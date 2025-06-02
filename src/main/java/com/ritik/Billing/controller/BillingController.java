package com.ritik.Billing.controller;

import com.ritik.Billing.dto.bill.BillRequest;
import com.ritik.Billing.dto.bill.BillResponse;
import com.ritik.Billing.enums.BillStatus;
import com.ritik.Billing.service.BillingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping
    public ResponseEntity<BillResponse> createBill(@Valid @RequestBody BillRequest request) {
        BillResponse response = billingService.createBill(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillResponse> getBillById(@PathVariable Long id) {
        BillResponse response = billingService.getBillById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BillResponse>> getAllBills(
            @RequestParam(name = "includeInactive", defaultValue = "false") boolean includeInactive) {
        List<BillResponse> responses = billingService.getAllBills(includeInactive);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<BillResponse> updateBillStatus(@PathVariable Long id, @RequestParam BillStatus newStatus) {
        BillResponse response = billingService.updateBillStatus(id, newStatus);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteBill(@PathVariable Long id) {
        billingService.softDeleteBill(id);
        return ResponseEntity.noContent().build();
    }
}