package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.repositories.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static com.ezequiel.paymentsapi.services.SortedPaymentsServiceTestFixture.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class SortedPaymentsServiceTest {

    private SortedPaymentsService subject;
    private PaymentRepository paymentRepository;

    @BeforeEach
    public void setUp() {
        paymentRepository = Mockito.mock(PaymentRepository.class);
        subject = new SortedPaymentsService(paymentRepository);
    }

    @Test
    public void shouldReturnSortedPaymentsByDate() {
        List<Payment> payments = new ArrayList<>();
        payments.add(newMockedPaymentByDate(DATE_TIME_3));
        payments.add(newMockedPaymentByDate(DATE_TIME_1));
        payments.add(newMockedPaymentByDate(DATE_TIME_2));

        given(paymentRepository.findAll()).willReturn(payments);

        List<Payment> sortedPaymentsByDate = subject.getSortedPaymentsByDate();
        Assertions.assertNotNull(sortedPaymentsByDate);
        Assertions.assertEquals(AMOUNT_OF_PAYMENTS, sortedPaymentsByDate.size());
        Assertions.assertEquals(DATE_TIME_1, sortedPaymentsByDate.get(0).getDate().format(DATE_TIME_FORMATTER));
        Assertions.assertEquals(DATE_TIME_2, sortedPaymentsByDate.get(1).getDate().format(DATE_TIME_FORMATTER));
        Assertions.assertEquals(DATE_TIME_3, sortedPaymentsByDate.get(2).getDate().format(DATE_TIME_FORMATTER));

        verify(paymentRepository).findAll();
        verifyNoMoreInteractions(paymentRepository);
    }

}
