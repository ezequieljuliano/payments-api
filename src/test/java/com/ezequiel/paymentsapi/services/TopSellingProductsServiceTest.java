package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.repositories.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.ezequiel.paymentsapi.services.TopSellingProductsServiceTestFixture.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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
        payments.add(newMockedPayment1());
        payments.add(newMockedPayment2());

        given(paymentRepository.findAll()).willReturn(payments);

        Map<String, Long> topSellingProductsNamesAndQuantities = subject.getTopSellingProductsNamesAndQuantities();
        Assertions.assertNotNull(topSellingProductsNamesAndQuantities);
        Assertions.assertEquals(TOP_PRODUCTS_SELLING_AMOUNT, topSellingProductsNamesAndQuantities.size());

        Optional<Map.Entry<String, Long>> product1Billing = topSellingProductsNamesAndQuantities.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(PRODUCT_NAME_1))
                .findAny();
        Assertions.assertTrue(product1Billing.isPresent());
        Assertions.assertEquals(PRODUCT_1_TOTAL, product1Billing.get().getValue());

        Optional<Map.Entry<String, Long>> product2Billing = topSellingProductsNamesAndQuantities.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(PRODUCT_NAME_2))
                .findAny();
        Assertions.assertTrue(product2Billing.isPresent());
        Assertions.assertEquals(PRODUCT_2_TOTAL, product2Billing.get().getValue());

        Optional<Map.Entry<String, Long>> product3Billing = topSellingProductsNamesAndQuantities.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(PRODUCT_NAME_3))
                .findAny();
        Assertions.assertTrue(product3Billing.isPresent());
        Assertions.assertEquals(PRODUCT_3_TOTAL, product2Billing.get().getValue());

        verify(paymentRepository).findAll();
        verifyNoMoreInteractions(paymentRepository);
    }

}
