package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.repositories.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.ezequiel.paymentsapi.services.MonthlyBillingServiceTestFixture.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class MonthlyBillingServiceTest {

    private MonthlyBillingService subject;
    private PaymentRepository paymentRepository;

    @BeforeEach
    public void setUp() {
        paymentRepository = Mockito.mock(PaymentRepository.class);
        subject = new MonthlyBillingService(paymentRepository);
    }

    @Test
    public void shouldReturnMonthlyBilling() {
        List<Payment> payments = new ArrayList<>();
        payments.add(newMockedPaymentByDateAndValues(DATE_TIME_2, PAYMENT_2_VALUES));
        payments.add(newMockedPaymentByDateAndValues(DATE_TIME_1, PAYMENT_1_VALUES));

        given(paymentRepository.findAll()).willReturn(payments);

        Map<YearMonth, BigDecimal> monthlyBilling = subject.getMonthlyBilling();
        Assertions.assertNotNull(monthlyBilling);
        Assertions.assertEquals(MONTHLY_BILLING_AMOUNT, monthlyBilling.size());

        Optional<Map.Entry<YearMonth, BigDecimal>> juneBilling = monthlyBilling.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getMonth() == Month.JUNE)
                .findAny();
        Assertions.assertTrue(juneBilling.isPresent());
        Assertions.assertEquals(TOTAL_JUNE, juneBilling.get().getValue());

        Optional<Map.Entry<YearMonth, BigDecimal>> mayBilling = monthlyBilling.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getMonth() == Month.MAY)
                .findAny();
        Assertions.assertTrue(mayBilling.isPresent());
        Assertions.assertEquals(TOTAL_MAY, mayBilling.get().getValue());

        verify(paymentRepository).findAll();
        verifyNoMoreInteractions(paymentRepository);
    }

}
