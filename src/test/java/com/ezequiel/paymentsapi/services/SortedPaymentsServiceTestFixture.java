package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Customer;
import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SortedPaymentsServiceTestFixture {

    public static Payment createMockedPaymentByDateAndCustomerName(String date, String customerName) {
        Payment payment = new Payment();
        payment.setDate(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        payment.setCustomer(new Customer(customerName));
        payment.getProducts().add(new Product("Mocked Product", "Mocked Path", BigDecimal.valueOf(100.0)));
        return payment;
    }

}
