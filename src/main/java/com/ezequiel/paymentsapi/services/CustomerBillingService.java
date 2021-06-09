package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Customer;
import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;
import com.ezequiel.paymentsapi.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerBillingService {

    private final PaymentRepository paymentRepository;

    public Map<Customer, BigDecimal> getCustomersBilling() {
        return paymentRepository
                .findAll()
                .stream()
                .collect(Collectors.groupingBy(Payment::getCustomer,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                p -> p.getProducts()
                                        .stream()
                                        .map(Product::getPrice)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add), BigDecimal::add)
                        )
                );
    }

}
