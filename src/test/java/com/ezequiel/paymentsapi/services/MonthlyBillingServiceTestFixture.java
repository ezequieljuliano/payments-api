package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Customer;
import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MonthlyBillingServiceTestFixture {

    private static final Faker faker = new Faker(new Locale("pt-BR"));

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final String DATE_TIME_1 = "2021-05-05 08:30";
    public static final String DATE_TIME_2 = "2021-06-07 11:30";
    public static final List<BigDecimal> PAYMENT_1_VALUES = Arrays.asList(BigDecimal.valueOf(100.0), BigDecimal.valueOf(200.0));
    public static final List<BigDecimal> PAYMENT_2_VALUES = Arrays.asList(BigDecimal.valueOf(300.0), BigDecimal.valueOf(400.0));
    public static final BigDecimal TOTAL_JUNE = BigDecimal.valueOf(700.0);
    public static final BigDecimal TOTAL_MAY = BigDecimal.valueOf(300.0);
    public static final int MONTHLY_BILLING_AMOUNT = 2;

    public static Payment newMockedPaymentByDateAndValues(String date, List<BigDecimal> values) {
        Payment mockedPayment = new Payment();
        mockedPayment.setDate(LocalDateTime.parse(date, DATE_TIME_FORMATTER));
        mockedPayment.setCustomer(new Customer(faker.name().fullName()));
        values.forEach(value -> mockedPayment.getProducts().add(new Product(faker.commerce().productName(), faker.internet().url(), value)));
        return mockedPayment;
    }

}
