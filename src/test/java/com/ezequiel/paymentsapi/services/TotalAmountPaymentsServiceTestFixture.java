package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Customer;
import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TotalAmountPaymentsServiceTestFixture {

    private static final Faker faker = new Faker(new Locale("pt-BR"));

    public static final List<BigDecimal> PAYMENT_1_VALUES = Arrays.asList(BigDecimal.valueOf(100.0), BigDecimal.valueOf(200.0));
    public static final List<BigDecimal> PAYMENT_2_VALUES = Arrays.asList(BigDecimal.valueOf(300.0), BigDecimal.valueOf(400.0));
    public static final BigDecimal TOTAL_PAYMENTS_AMOUNT = BigDecimal.valueOf(1000.0);

    public static Payment newMockedPaymentByValues(List<BigDecimal> values) {
        Payment mockedPayment = new Payment();
        mockedPayment.setDate(LocalDateTime.now());
        mockedPayment.setCustomer(new Customer(faker.name().fullName()));
        values.forEach(value -> mockedPayment.getProducts().add(new Product(faker.commerce().productName(), faker.internet().url(), value)));
        return mockedPayment;
    }

}
