package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Customer;
import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;
import com.ezequiel.paymentsapi.repositories.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.BDDMockito.given;

public class TopSellingProductsServiceTest {

    private TopSellingProductsService subject;
    private PaymentRepository paymentRepository;

    @BeforeEach
    public void setUp() {
        paymentRepository = Mockito.mock(PaymentRepository.class);
        subject = new TopSellingProductsService(paymentRepository);
    }

    @Test
    public void shouldReturnTopSellingProductsNamesAndQuantities() {
        List<Payment> payments = new ArrayList<>();
        payments.add(createMockedPayment("Customer 1", Arrays.asList(
                new Product("Mocked Product 1", "Mocked Path 1", BigDecimal.valueOf(300.0)),
                new Product("Mocked Product 2", "Mocked Path 2", BigDecimal.valueOf(200.0))
        )));
        payments.add(createMockedPayment("Customer 2", Arrays.asList(
                new Product("Mocked Product 1", "Mocked Path 1", BigDecimal.valueOf(300.0)),
                new Product("Mocked Product 3", "Mocked Path 3", BigDecimal.valueOf(200.0))
        )));

        given(paymentRepository.findAll()).willReturn(payments);

        Map<String, Long> topSellingProductsNamesAndQuantities = subject.getTopSellingProductsNamesAndQuantities();
        Assertions.assertNotNull(topSellingProductsNamesAndQuantities);
        Assertions.assertEquals(3, topSellingProductsNamesAndQuantities.size());

        Optional<Map.Entry<String, Long>> product1Billing = topSellingProductsNamesAndQuantities.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals("Mocked Product 1"))
                .findAny();
        Assertions.assertTrue(product1Billing.isPresent());
        Assertions.assertEquals(2L, product1Billing.get().getValue());

        Optional<Map.Entry<String, Long>> product2Billing = topSellingProductsNamesAndQuantities.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals("Mocked Product 2"))
                .findAny();
        Assertions.assertTrue(product2Billing.isPresent());
        Assertions.assertEquals(1L, product2Billing.get().getValue());

        Optional<Map.Entry<String, Long>> product3Billing = topSellingProductsNamesAndQuantities.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals("Mocked Product 3"))
                .findAny();
        Assertions.assertTrue(product3Billing.isPresent());
        Assertions.assertEquals(1L, product2Billing.get().getValue());
    }

    private Payment createMockedPayment(String customerName, List<Product> products) {
        Payment payment = new Payment();
        payment.setDate(LocalDateTime.now());
        payment.setCustomer(new Customer(customerName));
        payment.getProducts().addAll(products);
        return payment;
    }

}
