package com.ezequiel.paymentsapi.rest;

import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;
import com.ezequiel.paymentsapi.repositories.PaymentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ezequiel.paymentsapi.rest.PaymentRestTestFixture.aMockedPaymentByDateCustomerNameAndProducts;
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
        payments.add(aMockedPaymentByDateCustomerNameAndProducts("2021-06-07 11:30", "Customer 1",
                Arrays.asList(
                        new Product("Mocked Product 1", "Mocked Path 1", BigDecimal.valueOf(100.0)),
                        new Product("Mocked Product 2", "Mocked Path 2", BigDecimal.valueOf(50.0))
                )));
        payments.add(aMockedPaymentByDateCustomerNameAndProducts("2021-06-06 08:30", "Customer 1",
                Arrays.asList(
                        new Product("Mocked Product 1", "Mocked Path 1", BigDecimal.valueOf(100.0)),
                        new Product("Mocked Product 2", "Mocked Path 2", BigDecimal.valueOf(50.0))
                )));
        payments.add(aMockedPaymentByDateCustomerNameAndProducts("2021-06-05 09:30", "Customer 2",
                Arrays.asList(
                        new Product("Mocked Product 3", "Mocked Path 1", BigDecimal.valueOf(60.0)),
                        new Product("Mocked Product 4", "Mocked Path 2", BigDecimal.valueOf(30.0))
                )));
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
