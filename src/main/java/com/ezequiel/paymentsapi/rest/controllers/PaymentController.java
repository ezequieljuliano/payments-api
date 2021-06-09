package com.ezequiel.paymentsapi.rest.controllers;

import com.ezequiel.paymentsapi.rest.mappers.PaymentMapper;
import com.ezequiel.paymentsapi.rest.responses.*;
import com.ezequiel.paymentsapi.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentMapper paymentMapper;
    private final SortedPaymentsService sortedPaymentsService;
    private final TotalAmountPaymentsService totalAmountPaymentsService;
    private final TopSellingProductsService topSellingProductsService;
    private final TopBillingProductsService topBillingProductsService;
    private final CustomerProductsService customerProductsService;
    private final CustomerBillingService customerBillingService;
    private final MonthlyBillingService monthlyBillingService;

    @GetMapping("/sorted-by-date")
    public ResponseEntity<List<SortedPaymentResponse>> getSortedPaymentsByDate() {
        List<SortedPaymentResponse> response = paymentMapper
                .createSortedPaymentResponseList(sortedPaymentsService.getSortedPaymentsByDate());
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/total-amount")
    public ResponseEntity<TotalAmountPaymentResponse> getTotalPaymentsAmount() {
        return ResponseEntity.ok(
                new TotalAmountPaymentResponse(totalAmountPaymentsService.getTotalPaymentsAmount())
        );
    }

    @GetMapping("/top-selling-products")
    public ResponseEntity<List<TopSellingProductResponse>> getTopSellingProductsNamesAndQuantities() {
        List<TopSellingProductResponse> response = paymentMapper
                .createTopSellingProductResponseList(topSellingProductsService.getTopSellingProductsNamesAndQuantities());
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-billing-products")
    public ResponseEntity<List<TopBillingProductResponse>> getTopBillingProductsNamesAndAmount() {
        List<TopBillingProductResponse> response = paymentMapper
                .createTopBillingProductResponseList(topBillingProductsService.getTopBillingProductsNamesAndAmount());
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products-by-customer")
    public ResponseEntity<List<CustomerProductsResponse>> getProductsByCustomer() {
        List<CustomerProductsResponse> response = paymentMapper
                .createCustomerProductsResponseList(customerProductsService.getProductsByCustomer());
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customers-billing")
    public ResponseEntity<List<CustomerBillingResponse>> getCustomersBilling() {
        List<CustomerBillingResponse> response = paymentMapper
                .createCustomerBillingResponseList(customerBillingService.getCustomersBilling());
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/monthly-billing")
    public ResponseEntity<List<MonthlyBillingResponse>> getMonthlyBilling() {
        List<MonthlyBillingResponse> response = paymentMapper.createMonthlyBillingResponseList(
                monthlyBillingService.getMonthlyBilling());
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

}
