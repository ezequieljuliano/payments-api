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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class CustomerBillingServiceTest {

    private CustomerBillingService subject;
    private PaymentRepository paymentRepository;

    @BeforeEach
    public void setUp() {
        paymentRepository = Mockito.mock(PaymentRepository.class);
        subject = new CustomerBillingService(paymentRepository);
    }

    @Test
    public void shouldReturnCustomersBilling() {
        List<Payment> payments = new ArrayList<>();
        payments.add(createMockedPayment("Customer 1", Arrays.asList(100.0, 200.0)));
        payments.add(createMockedPayment("Customer 2", Arrays.asList(300.0, 400.0)));

        given(paymentRepository.findAll()).willReturn(payments);

        Map<Customer, BigDecimal> customersBilling = subject.getCustomersBilling();
        Assertions.assertNotNull(customersBilling);
        Assertions.assertEquals(2, customersBilling.size());

        Optional<Map.Entry<Customer, BigDecimal>> customer1Billing = customersBilling.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getName().equals("Customer 1"))
                .findAny();
        Assertions.assertTrue(customer1Billing.isPresent());
        Assertions.assertEquals(BigDecimal.valueOf(300.0), customer1Billing.get().getValue());

        Optional<Map.Entry<Customer, BigDecimal>> customer2Billing = customersBilling.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getName().equals("Customer 2"))
                .findAny();
        Assertions.assertTrue(customer2Billing.isPresent());
        Assertions.assertEquals(BigDecimal.valueOf(700.0), customer2Billing.get().getValue());
        verify(paymentRepository).findAll();
        verifyNoMoreInteractions(paymentRepository);
    }

    private Payment createMockedPayment(String customerName, List<Double> values) {
        Payment payment = new Payment();
        payment.setDate(LocalDateTime.now());
        payment.setCustomer(new Customer(customerName));
        values.forEach(value -> payment.getProducts().add(new Product("Mocked Product", "Mocked Path", BigDecimal.valueOf(value))));
        return payment;
    }

}
