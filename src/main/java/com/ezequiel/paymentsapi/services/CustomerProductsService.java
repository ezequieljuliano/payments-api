package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Customer;
import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;
import com.ezequiel.paymentsapi.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerProductsService {

    private final PaymentRepository paymentRepository;

    public Map<Customer, List<Product>> getProductsByCustomer() {
        return paymentRepository
                .findAll()
                .stream()
                .collect(Collectors.groupingBy(Payment::getCustomer,
                        Collectors.reducing(Collections.emptyList(),
                                Payment::getProducts, (l1, l2) -> {
                                    List<Product> l = new ArrayList<>();
                                    l.addAll(l1);
                                    l.addAll(l2);
                                    return l;
                                }))
                );
    }

}
