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

public class CustomerBillingServiceTestFixture {

    private static final Faker faker = new Faker(new Locale("pt-BR"));

    public static final String CUSTOMER_NAME_1 = faker.name().fullName();
    public static final String CUSTOMER_NAME_2 = faker.name().fullName();
    public static final List<BigDecimal> PAYMENT_1_VALUES = Arrays.asList(BigDecimal.valueOf(100.0), BigDecimal.valueOf(200.0));
    public static final List<BigDecimal> PAYMENT_2_VALUES = Arrays.asList(BigDecimal.valueOf(300.0), BigDecimal.valueOf(400.0));
    public static final BigDecimal TOTAL_CUSTOMER_1 = BigDecimal.valueOf(300.0);
    public static final BigDecimal TOTAL_CUSTOMER_2 = BigDecimal.valueOf(700.0);
    public static final int CUSTOMERS_BILLING_AMOUNT = 2;

    public static Payment newMockedPaymentByCustomerNameAndValues(String customerName, List<BigDecimal> values) {
        Payment mockedPayment = new Payment();
        mockedPayment.setDate(LocalDateTime.now());
        mockedPayment.setCustomer(new Customer(customerName));
        values.forEach(value -> mockedPayment.getProducts().add(new Product(faker.commerce().productName(), faker.internet().url(), value)));
        return mockedPayment;
    }

}
