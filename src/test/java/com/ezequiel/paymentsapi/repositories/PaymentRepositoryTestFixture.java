package com.ezequiel.paymentsapi.repositories;

import com.ezequiel.paymentsapi.entities.Customer;
import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class PaymentRepositoryTestFixture {

    public static Payment aMockedPaymentByCustomerName(String customerName) {
        Payment payment = new Payment();
        payment.setDate(LocalDateTime.now());
        payment.setCustomer(new Customer(customerName));
        payment.getProducts().addAll(Arrays.asList(
                new Product("Mocked Product 1", "Mocked Path 1", BigDecimal.valueOf(100.0)),
                new Product("Mocked Product 2", "Mocked Path 2", BigDecimal.valueOf(50.0))
        ));
        return payment;
    }

}
