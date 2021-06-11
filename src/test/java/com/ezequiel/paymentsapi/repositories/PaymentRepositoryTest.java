package com.ezequiel.paymentsapi.repositories;

import com.ezequiel.paymentsapi.entities.Payment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static com.ezequiel.paymentsapi.repositories.PaymentRepositoryTestFixture.*;

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
        payments.add(newMockedPaymentByCustomerName(CUSTOMER_NAME_1));
        payments.add(newMockedPaymentByCustomerName(CUSTOMER_NAME_2));
        subject.saveAll(payments);

        List<Payment> findAllPayments = subject.findAll();
        Assertions.assertNotNull(findAllPayments);
        Assertions.assertEquals(AMOUNT_OF_PAYMENTS, findAllPayments.size());
        Assertions.assertTrue(findAllPayments.stream().anyMatch(payment -> payment.getCustomer().getName().equals(CUSTOMER_NAME_1)));
        Assertions.assertTrue(findAllPayments.stream().anyMatch(payment -> payment.getCustomer().getName().equals(CUSTOMER_NAME_2)));
    }

}
