package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Customer;
import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CustomerBillingServiceTestFixture {

    public static Payment aMockedPaymentByNameAndValues(String customerName, List<Double> values) {
        Payment payment = new Payment();
        payment.setDate(LocalDateTime.now());
        payment.setCustomer(new Customer(customerName));
        values.forEach(value -> payment.getProducts().add(new Product("Mocked Product", "Mocked Path", BigDecimal.valueOf(value))));
        return payment;
    }

}
