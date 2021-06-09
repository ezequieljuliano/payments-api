package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SortedPaymentsService {

    private final PaymentRepository paymentRepository;

    public List<Payment> getSortedPaymentsByDate() {
        return paymentRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Payment::getDate))
                .collect(Collectors.toList());
    }

}
