package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Product;
import com.ezequiel.paymentsapi.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopBillingProductsService {

    private final PaymentRepository paymentRepository;

    public Map<String, BigDecimal> getTopBillingProductsNamesAndAmount() {
        return paymentRepository
                .findAll()
                .stream()
                .flatMap(payment -> payment.getProducts().stream())
                .collect(
                        Collectors.groupingBy(
                                Product::getName,
                                Collectors.reducing(BigDecimal.ZERO, Product::getPrice, BigDecimal::add)
                        )
                );
    }

}
