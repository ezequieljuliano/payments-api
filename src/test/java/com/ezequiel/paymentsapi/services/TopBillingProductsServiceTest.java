package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;
import com.ezequiel.paymentsapi.repositories.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.*;

import static com.ezequiel.paymentsapi.services.TopBillingProductsServiceTestFixture.aMockedPaymentByCustomerNameAndProducts;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class TopBillingProductsServiceTest {

    private TopBillingProductsService subject;
    private PaymentRepository paymentRepository;

    @BeforeEach
    public void setUp() {
        paymentRepository = Mockito.mock(PaymentRepository.class);
        subject = new TopBillingProductsService(paymentRepository);
    }

    @Test
    public void shouldReturnTopBillingProductsNamesAndAmount() {
        List<Payment> payments = new ArrayList<>();
        payments.add(aMockedPaymentByCustomerNameAndProducts("Customer 1", Arrays.asList(
                new Product("Mocked Product 1", "Mocked Path 1", BigDecimal.valueOf(300.0)),
                new Product("Mocked Product 2", "Mocked Path 2", BigDecimal.valueOf(200.0))
        )));
        payments.add(aMockedPaymentByCustomerNameAndProducts("Customer 2", Arrays.asList(
                new Product("Mocked Product 1", "Mocked Path 1", BigDecimal.valueOf(300.0)),
                new Product("Mocked Product 3", "Mocked Path 3", BigDecimal.valueOf(200.0))
        )));

        given(paymentRepository.findAll()).willReturn(payments);

        Map<String, BigDecimal> topBillingProductsNamesAndAmount = subject.getTopBillingProductsNamesAndAmount();
        Assertions.assertNotNull(topBillingProductsNamesAndAmount);
        Assertions.assertEquals(3, topBillingProductsNamesAndAmount.size());

        Optional<Map.Entry<String, BigDecimal>> product1Billing = topBillingProductsNamesAndAmount.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals("Mocked Product 1"))
                .findAny();
        Assertions.assertTrue(product1Billing.isPresent());
        Assertions.assertEquals(BigDecimal.valueOf(600.0), product1Billing.get().getValue());

        Optional<Map.Entry<String, BigDecimal>> product2Billing = topBillingProductsNamesAndAmount.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals("Mocked Product 2"))
                .findAny();
        Assertions.assertTrue(product2Billing.isPresent());
        Assertions.assertEquals(BigDecimal.valueOf(200.0), product2Billing.get().getValue());

        Optional<Map.Entry<String, BigDecimal>> product3Billing = topBillingProductsNamesAndAmount.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals("Mocked Product 3"))
                .findAny();
        Assertions.assertTrue(product3Billing.isPresent());
        Assertions.assertEquals(BigDecimal.valueOf(200.0), product2Billing.get().getValue());

        verify(paymentRepository).findAll();
        verifyNoMoreInteractions(paymentRepository);
    }

}
