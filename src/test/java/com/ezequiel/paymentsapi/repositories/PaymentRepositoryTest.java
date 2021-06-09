package com.ezequiel.paymentsapi.repositories;

import com.ezequiel.paymentsapi.entities.Customer;
import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository subject;

    @AfterEach
    public void tearDown() {
        subject.deleteAll();
    }

    @Test
    public void shouldSaveAndFindAllPayments() {
        List<Payment> payments = new ArrayList<>();
        payments.add(createPayment("Customer 1"));
        payments.add(createPayment("Customer 2"));
        subject.saveAll(payments);

        List<Payment> findAllPayments = subject.findAll();
        Assertions.assertNotNull(findAllPayments);
        Assertions.assertEquals(2, findAllPayments.size());
        Assertions.assertTrue(findAllPayments.stream().anyMatch(payment -> payment.getCustomer().getName().equals("Customer 1")));
        Assertions.assertTrue(findAllPayments.stream().anyMatch(payment -> payment.getCustomer().getName().equals("Customer 2")));
    }

    private Payment createPayment(String customerName) {
        Payment payment = new Payment();
        payment.setDate(LocalDateTime.now());
        payment.setCustomer(new Customer(customerName));
        payment.getProducts().addAll(Arrays.asList(
                new Product("Mocked Product 1", "Mocked Path 1", BigDecimal.valueOf(100.0)),
                new Product("Mocked Product 2", "Mocked Path 2", BigDecimal.valueOf(50.0))
        ));
        return payment;
    }

}
