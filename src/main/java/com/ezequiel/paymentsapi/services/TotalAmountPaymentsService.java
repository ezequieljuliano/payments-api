package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Product;
import com.ezequiel.paymentsapi.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TotalAmountPaymentsService {

    private final PaymentRepository paymentRepository;

    public BigDecimal getTotalPaymentsAmount() {
        return paymentRepository
                .findAll()
                .stream()
                .flatMap(payment -> payment.getProducts().stream().map(Product::getPrice))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
