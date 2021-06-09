package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Product;
import com.ezequiel.paymentsapi.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopSellingProductsService {

    private final PaymentRepository paymentRepository;

    public Map<String, Long> getTopSellingProductsNamesAndQuantities() {
        return paymentRepository
                .findAll()
                .stream()
                .flatMap(payment -> payment.getProducts().stream())
                .collect(Collectors.groupingBy(Product::getName, Collectors.counting()));
    }

}
