package com.ezequiel.paymentsapi.rest;

import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.repositories.PaymentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static com.ezequiel.paymentsapi.rest.PaymentRestTestFixture.*;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class PaymentRestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private PaymentRepository paymentRepository;

    private final List<Payment> payments = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        payments.add(newMockedPayment1());
        payments.add(newMockedPayment2());
        payments.add(newMockedPayment3());
        paymentRepository.saveAll(payments);
    }

    @AfterEach
    public void tearDown() {
        paymentRepository.deleteAll(payments);
    }

    @Test
    public void shouldReturnSortedPaymentsByDate() {
        when()
                .get(String.format("http://localhost:%s/payments-api/v1/payments/sorted-by-date", port))
                .then()
                .statusCode(is(200))
                .body(notNullValue());
    }

    @Test
    public void shouldReturnTotalPaymentsAmount() {
        when()
                .get(String.format("http://localhost:%s/payments-api/v1/payments/total-amount", port))
                .then()
                .statusCode(is(200))
                .body(notNullValue());
    }

    @Test
    public void shouldReturnTopSellingProductsNamesAndQuantities() {
        when()
                .get(String.format("http://localhost:%s/payments-api/v1/payments/top-selling-products", port))
                .then()
                .statusCode(is(200))
                .body(notNullValue());
    }

    @Test
    public void shouldReturnTopBillingProductsNamesAndAmount() {
        when()
                .get(String.format("http://localhost:%s/payments-api/v1/payments/top-billing-products", port))
                .then()
                .statusCode(is(200))
                .body(notNullValue());
    }

    @Test
    public void shouldReturnProductsByCustomer() {
        when()
                .get(String.format("http://localhost:%s/payments-api/v1/payments/products-by-customer", port))
                .then()
                .statusCode(is(200))
                .body(notNullValue());
    }

    @Test
    public void shouldReturnCustomersBilling() {
        when()
                .get(String.format("http://localhost:%s/payments-api/v1/payments/customers-billing", port))
                .then()
                .statusCode(is(200))
                .body(notNullValue());
    }

    @Test
    public void shouldReturnMonthlyBilling() {
        when()
                .get(String.format("http://localhost:%s/payments-api/v1/payments/monthly-billing", port))
                .then()
                .statusCode(is(200))
                .body(notNullValue());
    }

}
