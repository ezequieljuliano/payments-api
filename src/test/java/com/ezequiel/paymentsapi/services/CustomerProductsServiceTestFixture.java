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

public class CustomerProductsServiceTestFixture {

    private static final Faker faker = new Faker(new Locale("pt-BR"));

    public static final String CUSTOMER_NAME_1 = faker.name().fullName();
    public static final String CUSTOMER_NAME_2 = faker.name().fullName();

    public static final String PRODUCT_NAME_1 = faker.commerce().productName();
    public static final String PRODUCT_NAME_2 = faker.commerce().productName();
    public static final String PRODUCT_NAME_3 = faker.commerce().productName();
    public static final String PRODUCT_NAME_4 = faker.commerce().productName();

    public static final String PRODUCT_FILE_1 = faker.internet().url();
    public static final String PRODUCT_FILE_2 = faker.internet().url();
    public static final String PRODUCT_FILE_3 = faker.internet().url();
    public static final String PRODUCT_FILE_4 = faker.internet().url();

    public static final BigDecimal PRODUCT_PRICE_1 = BigDecimal.valueOf(100.0);
    public static final BigDecimal PRODUCT_PRICE_2 = BigDecimal.valueOf(200.0);
    public static final BigDecimal PRODUCT_PRICE_3 = BigDecimal.valueOf(100.0);
    public static final BigDecimal PRODUCT_PRICE_4 = BigDecimal.valueOf(200.0);

    public static final int PRODUCTS_BY_CUSTOMERS_AMOUNT = 2;
    public static final int PRODUCTS_BY_CUSTOMER_1_AMOUNT = 2;
    public static final int PRODUCTS_BY_CUSTOMER_2_AMOUNT = 2;

    public static Payment newMockedPayment1() {
        return newMockedPaymentByCustomerNameAndProducts(
                CUSTOMER_NAME_1,
                Arrays.asList(
                        new Product(PRODUCT_NAME_1, PRODUCT_FILE_1, PRODUCT_PRICE_1),
                        new Product(PRODUCT_NAME_2, PRODUCT_FILE_2, PRODUCT_PRICE_2)
                )
        );
    }

    public static Payment newMockedPayment2() {
        return newMockedPaymentByCustomerNameAndProducts(
                CUSTOMER_NAME_2,
                Arrays.asList(
                        new Product(PRODUCT_NAME_3, PRODUCT_FILE_3, PRODUCT_PRICE_3),
                        new Product(PRODUCT_NAME_4, PRODUCT_FILE_4, PRODUCT_PRICE_4)
                )
        );
    }

    private static Payment newMockedPaymentByCustomerNameAndProducts(String customerName, List<Product> products) {
        Payment mockedPayment = new Payment();
        mockedPayment.setDate(LocalDateTime.now());
        mockedPayment.setCustomer(new Customer(customerName));
        mockedPayment.getProducts().addAll(products);
        return mockedPayment;
    }

}
