package com.ezequiel.paymentsapi.rest.controllers;

import com.ezequiel.paymentsapi.entities.Customer;
import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PaymentControllerTestFixture {

    private static final Faker faker = new Faker(new Locale("pt-BR"));

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final String DATE_TIME_1 = "2021-06-05 19:30";
    public static final String DATE_TIME_2 = "2021-06-06 08:30";
    public static final String DATE_TIME_3 = "2021-06-07 11:30";

    public static final int AMOUNT_OF_SORTED_PAYMENTS = 3;
    public static final BigDecimal TOTAL_PAYMENTS_AMOUNT = BigDecimal.valueOf(500.0);

    public static final String CUSTOMER_NAME_1 = faker.name().fullName();
    public static final String CUSTOMER_NAME_2 = faker.name().fullName();

    public static final BigDecimal CUSTOMER_BILLING_1 = BigDecimal.valueOf(200.0);
    public static final BigDecimal CUSTOMER_BILLING_2 = BigDecimal.valueOf(400.0);
    public static final int CUSTOMER_BILLING_AMOUNT = 2;

    public static final String PRODUCT_NAME_1 = faker.commerce().productName();
    public static final String PRODUCT_NAME_2 = faker.commerce().productName();

    public static final String PRODUCT_FILE_1 = faker.internet().url();
    public static final String PRODUCT_FILE_2 = faker.internet().url();

    public static final BigDecimal PRODUCT_PRICE_1 = BigDecimal.valueOf(100.0);
    public static final BigDecimal PRODUCT_PRICE_2 = BigDecimal.valueOf(200.0);

    public static final Long PRODUCT_QUANTITY_1 = 5L;
    public static final Long PRODUCT_QUANTITY_2 = 3L;

    public static final int PRODUCTS_AMOUNT = 2;

    public static final BigDecimal PRODUCT_AMOUNT_1 = BigDecimal.valueOf(100.0);
    public static final BigDecimal PRODUCT_AMOUNT_2 = BigDecimal.valueOf(150.0);

    public static final int YEAR_BILLING = 2021;
    public static final int MAY_BILLING = Month.MAY.getValue();
    public static final int JUNE_BILLING = Month.JUNE.getValue();
    public static final BigDecimal MAY_BILLING_TOTAL = BigDecimal.valueOf(100.0);
    public static final BigDecimal JUNE_BILLING_TOTAL = BigDecimal.valueOf(200.0);
    public static final int MONTHLY_BILLING_AMOUNT = 2;

    public static Payment newMockedPaymentByDate(String date) {
        Payment mockedPayment = new Payment();
        mockedPayment.setDate(LocalDateTime.parse(date, DATE_TIME_FORMATTER));
        mockedPayment.setCustomer(new Customer(faker.name().fullName()));
        mockedPayment.getProducts().add(new Product(faker.commerce().productName(), faker.internet().url(), BigDecimal.valueOf(100.0)));
        return mockedPayment;
    }

    public static Map<String, Long> newMockedProductsNamesAndQuantities() {
        Map<String, Long> mockedProductsNamesAndQuantities = new HashMap<>();
        mockedProductsNamesAndQuantities.put(PRODUCT_NAME_1, PRODUCT_QUANTITY_1);
        mockedProductsNamesAndQuantities.put(PRODUCT_NAME_2, PRODUCT_QUANTITY_2);
        return mockedProductsNamesAndQuantities;
    }

    public static Map<String, BigDecimal> newMockedProductsNamesAndAmount() {
        Map<String, BigDecimal> mockedProductsNamesAndAmount = new HashMap<>();
        mockedProductsNamesAndAmount.put(PRODUCT_NAME_1, PRODUCT_AMOUNT_1);
        mockedProductsNamesAndAmount.put(PRODUCT_NAME_2, PRODUCT_AMOUNT_2);
        return mockedProductsNamesAndAmount;
    }

    public static Map<Customer, List<Product>> newMockedProductsByCustomer() {
        Map<Customer, List<Product>> mockedProductsByCustomer = new HashMap<>();
        mockedProductsByCustomer.put(new Customer(CUSTOMER_NAME_1), Arrays.asList(
                new Product(PRODUCT_NAME_1, PRODUCT_FILE_1, PRODUCT_PRICE_1),
                new Product(PRODUCT_NAME_2, PRODUCT_FILE_2, PRODUCT_PRICE_2)
        ));
        mockedProductsByCustomer.put(new Customer(CUSTOMER_NAME_2), Arrays.asList(
                new Product(PRODUCT_NAME_1, PRODUCT_FILE_1, PRODUCT_PRICE_1),
                new Product(PRODUCT_NAME_2, PRODUCT_FILE_2, PRODUCT_PRICE_2)
        ));
        return mockedProductsByCustomer;
    }

    public static Map<Customer, BigDecimal> newMockedCustomersBilling() {
        Map<Customer, BigDecimal> mockedCustomersBilling = new HashMap<>();
        mockedCustomersBilling.put(new Customer(CUSTOMER_NAME_1), CUSTOMER_BILLING_1);
        mockedCustomersBilling.put(new Customer(CUSTOMER_NAME_2), CUSTOMER_BILLING_2);
        return mockedCustomersBilling;
    }

    public static Map<YearMonth, BigDecimal> newMockedMonthlyBilling() {
        Map<YearMonth, BigDecimal> mockedMonthlyBilling = new HashMap<>();
        mockedMonthlyBilling.put(YearMonth.of(YEAR_BILLING, Month.MAY), MAY_BILLING_TOTAL);
        mockedMonthlyBilling.put(YearMonth.of(YEAR_BILLING, Month.JUNE), JUNE_BILLING_TOTAL);
        return mockedMonthlyBilling;
    }

}
