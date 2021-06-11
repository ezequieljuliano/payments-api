package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Customer;
import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.repositories.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.ezequiel.paymentsapi.services.CustomerBillingServiceTestFixture.*;
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
        payments.add(newMockedPaymentByCustomerNameAndValues(CUSTOMER_NAME_1, PAYMENT_1_VALUES));
        payments.add(newMockedPaymentByCustomerNameAndValues(CUSTOMER_NAME_2, PAYMENT_2_VALUES));

        given(paymentRepository.findAll()).willReturn(payments);

        Map<Customer, BigDecimal> customersBilling = subject.getCustomersBilling();
        Assertions.assertNotNull(customersBilling);
        Assertions.assertEquals(CUSTOMERS_BILLING_AMOUNT, customersBilling.size());

        Optional<Map.Entry<Customer, BigDecimal>> customer1Billing = customersBilling.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getName().equals(CUSTOMER_NAME_1))
                .findAny();
        Assertions.assertTrue(customer1Billing.isPresent());
        Assertions.assertEquals(TOTAL_CUSTOMER_1, customer1Billing.get().getValue());

        Optional<Map.Entry<Customer, BigDecimal>> customer2Billing = customersBilling.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getName().equals(CUSTOMER_NAME_2))
                .findAny();
        Assertions.assertTrue(customer2Billing.isPresent());
        Assertions.assertEquals(TOTAL_CUSTOMER_2, customer2Billing.get().getValue());

        verify(paymentRepository).findAll();
        verifyNoMoreInteractions(paymentRepository);
    }

}
