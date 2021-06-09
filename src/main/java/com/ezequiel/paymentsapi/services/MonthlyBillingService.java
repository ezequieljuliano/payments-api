package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Product;
import com.ezequiel.paymentsapi.repositories.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MonthlyBillingService {

    private final PaymentRepository paymentRepository;

    public Map<YearMonth, BigDecimal> getMonthlyBilling() {
        return paymentRepository
                .findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        payment -> YearMonth.from(payment.getDate()),
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                payment -> payment.getProducts()
                                        .stream()
                                        .map(Product::getPrice)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add),
                                BigDecimal::add)
                        )
                );
    }

}
