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

public class TopSellingProductsServiceTestFixture {

    private static final Faker faker = new Faker(new Locale("pt-BR"));

    public static final String PRODUCT_NAME_1 = faker.commerce().productName();
    public static final String PRODUCT_NAME_2 = faker.commerce().productName();
    public static final String PRODUCT_NAME_3 = faker.commerce().productName();
    public static final Long PRODUCT_1_TOTAL = 2L;
    public static final Long PRODUCT_2_TOTAL = 1L;
    public static final Long PRODUCT_3_TOTAL = 1L;
    public static final int TOP_PRODUCTS_SELLING_AMOUNT = 3;

    public static Payment newMockedPayment1() {
        return newMockedPaymentByProducts(
                Arrays.asList(
                        new Product(PRODUCT_NAME_1, faker.internet().url(), BigDecimal.valueOf(300.0)),
                        new Product(PRODUCT_NAME_2, faker.internet().url(), BigDecimal.valueOf(200.0))
                )
        );
    }

    public static Payment newMockedPayment2() {
        return newMockedPaymentByProducts(
                Arrays.asList(
                        new Product(PRODUCT_NAME_1, faker.internet().url(), BigDecimal.valueOf(300.0)),
                        new Product(PRODUCT_NAME_3, faker.internet().url(), BigDecimal.valueOf(200.0))
                )
        );
    }

    private static Payment newMockedPaymentByProducts(List<Product> products) {
        Payment mockedPayment = new Payment();
        mockedPayment.setDate(LocalDateTime.now());
        mockedPayment.setCustomer(new Customer(faker.name().fullName()));
        mockedPayment.getProducts().addAll(products);
        return mockedPayment;
    }

}
