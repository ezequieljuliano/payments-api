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

import static com.ezequiel.paymentsapi.services.TotalAmountPaymentsServiceTestFixture.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class TotalAmountPaymentsServiceTest {

    private TotalAmountPaymentsService subject;
    private PaymentRepository paymentRepository;

    @BeforeEach
    public void setUp() {
        paymentRepository = Mockito.mock(PaymentRepository.class);
        subject = new TotalAmountPaymentsService(paymentRepository);
    }

    @Test
    public void shouldReturnTotalPaymentsAmount() {
        List<Payment> payments = new ArrayList<>();
        payments.add(newMockedPaymentByValues(PAYMENT_1_VALUES));
        payments.add(newMockedPaymentByValues(PAYMENT_2_VALUES));

        given(paymentRepository.findAll()).willReturn(payments);

        BigDecimal totalPaymentsAmount = subject.getTotalPaymentsAmount();
        Assertions.assertNotNull(totalPaymentsAmount);
        Assertions.assertEquals(TOTAL_PAYMENTS_AMOUNT, totalPaymentsAmount);

        verify(paymentRepository).findAll();
        verifyNoMoreInteractions(paymentRepository);
    }

}
