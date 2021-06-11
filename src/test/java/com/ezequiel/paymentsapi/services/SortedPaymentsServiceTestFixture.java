package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Customer;
import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SortedPaymentsServiceTestFixture {

    private static final Faker faker = new Faker(new Locale("pt-BR"));

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final String DATE_TIME_1 = "2021-06-05 19:30";
    public static final String DATE_TIME_2 = "2021-06-06 08:30";
    public static final String DATE_TIME_3 = "2021-06-07 11:30";
    public static final int AMOUNT_OF_PAYMENTS = 3;

    public static Payment newMockedPaymentByDate(String date) {
        Payment mockedPayment = new Payment();
        mockedPayment.setDate(LocalDateTime.parse(date, DATE_TIME_FORMATTER));
        mockedPayment.setCustomer(new Customer(faker.name().fullName()));
        mockedPayment.getProducts().add(new Product(faker.commerce().productName(), faker.internet().url(), BigDecimal.valueOf(100.0)));
        return mockedPayment;
    }

}
