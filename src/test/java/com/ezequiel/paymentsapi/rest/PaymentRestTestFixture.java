package com.ezequiel.paymentsapi.rest;

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

public class PaymentRestTestFixture {

    private static final Faker faker = new Faker(new Locale("pt-BR"));

    public static Payment newMockedPayment1() {
        return newMockedPaymentByDateCustomerNameAndProducts(
                "2021-06-05 09:30",
                faker.name().fullName(),
                Arrays.asList(
                        new Product(faker.commerce().productName(), faker.internet().url(), BigDecimal.valueOf(300.0)),
                        new Product(faker.commerce().productName(), faker.internet().url(), BigDecimal.valueOf(200.0))
                )
        );
    }

    public static Payment newMockedPayment2() {
        return newMockedPaymentByDateCustomerNameAndProducts(
                "2021-06-06 08:30",
                faker.name().fullName(),
                Arrays.asList(
                        new Product(faker.commerce().productName(), faker.internet().url(), BigDecimal.valueOf(300.0)),
                        new Product(faker.commerce().productName(), faker.internet().url(), BigDecimal.valueOf(200.0))
                )
        );
    }

    public static Payment newMockedPayment3() {
        return newMockedPaymentByDateCustomerNameAndProducts(
                "2021-06-07 11:30",
                faker.name().fullName(),
                Arrays.asList(
                        new Product(faker.commerce().productName(), faker.internet().url(), BigDecimal.valueOf(300.0)),
                        new Product(faker.commerce().productName(), faker.internet().url(), BigDecimal.valueOf(200.0))
                )
        );
    }

    private static Payment newMockedPaymentByDateCustomerNameAndProducts(String date,
                                                                         String customerName,
                                                                         List<Product> products) {
        Payment mockedPayment = new Payment();
        mockedPayment.setDate(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        mockedPayment.setCustomer(new Customer(customerName));
        mockedPayment.getProducts().addAll(products);
        return mockedPayment;
    }

}
