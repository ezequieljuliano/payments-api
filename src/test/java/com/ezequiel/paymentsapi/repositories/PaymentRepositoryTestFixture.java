package com.ezequiel.paymentsapi.repositories;

import com.ezequiel.paymentsapi.entities.Customer;
import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Locale;

public class PaymentRepositoryTestFixture {

    private static final Faker faker = new Faker(new Locale("pt-BR"));

    public static final String CUSTOMER_NAME_1 = faker.name().fullName();
    public static final String CUSTOMER_NAME_2 = faker.name().fullName();
    public static final int AMOUNT_OF_PAYMENTS = 2;

    public static Payment newMockedPaymentByCustomerName(String customerName) {
        Payment mockedPayment = new Payment();
        mockedPayment.setDate(LocalDateTime.now());
        mockedPayment.setCustomer(new Customer(customerName));
        mockedPayment.getProducts().addAll(Arrays.asList(
                new Product(faker.commerce().productName(), faker.internet().url(), BigDecimal.valueOf(faker.number().randomNumber())),
                new Product(faker.commerce().productName(), faker.internet().url(), BigDecimal.valueOf(faker.number().randomNumber()))
        ));
        return mockedPayment;
    }

}
