package com.ezequiel.paymentsapi.services;

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

import static com.ezequiel.paymentsapi.services.TopBillingProductsServiceTestFixture.*;
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
        payments.add(newMockedPayment1());
        payments.add(newMockedPayment2());

        given(paymentRepository.findAll()).willReturn(payments);

        Map<String, BigDecimal> topBillingProductsNamesAndAmount = subject.getTopBillingProductsNamesAndAmount();
        Assertions.assertNotNull(topBillingProductsNamesAndAmount);
        Assertions.assertEquals(TOP_PRODUCTS_BILLING_AMOUNT, topBillingProductsNamesAndAmount.size());

        Optional<Map.Entry<String, BigDecimal>> product1Billing = topBillingProductsNamesAndAmount.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(PRODUCT_NAME_1))
                .findAny();
        Assertions.assertTrue(product1Billing.isPresent());
        Assertions.assertEquals(PRODUCT_1_TOTAL_BILLING, product1Billing.get().getValue());

        Optional<Map.Entry<String, BigDecimal>> product2Billing = topBillingProductsNamesAndAmount.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(PRODUCT_NAME_2))
                .findAny();
        Assertions.assertTrue(product2Billing.isPresent());
        Assertions.assertEquals(PRODUCT_2_TOTAL_BILLING, product2Billing.get().getValue());

        Optional<Map.Entry<String, BigDecimal>> product3Billing = topBillingProductsNamesAndAmount.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(PRODUCT_NAME_3))
                .findAny();
        Assertions.assertTrue(product3Billing.isPresent());
        Assertions.assertEquals(PRODUCT_3_TOTAL_BILLING, product3Billing.get().getValue());

        verify(paymentRepository).findAll();
        verifyNoMoreInteractions(paymentRepository);
    }

}
