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
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        payments.add(createMockedPayment("2021-06-07 11:30", "Customer 1", Arrays.asList(100.0, 200.0)));
        payments.add(createMockedPayment("2021-05-05 08:30", "Customer 2", Arrays.asList(300.0, 400.0)));

        given(paymentRepository.findAll()).willReturn(payments);

        Map<YearMonth, BigDecimal> monthlyBilling = subject.getMonthlyBilling();
        Assertions.assertNotNull(monthlyBilling);
        Assertions.assertEquals(2, monthlyBilling.size());

        Optional<Map.Entry<YearMonth, BigDecimal>> juneBilling = monthlyBilling.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getMonth() == Month.JUNE)
                .findAny();
        Assertions.assertTrue(juneBilling.isPresent());
        Assertions.assertEquals(BigDecimal.valueOf(300.0), juneBilling.get().getValue());

        Optional<Map.Entry<YearMonth, BigDecimal>> mayBilling = monthlyBilling.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getMonth() == Month.MAY)
                .findAny();
        Assertions.assertTrue(mayBilling.isPresent());
        Assertions.assertEquals(BigDecimal.valueOf(700.0), mayBilling.get().getValue());
        verify(paymentRepository).findAll();
        verifyNoMoreInteractions(paymentRepository);
    }

    private Payment createMockedPayment(String date, String customerName, List<Double> values) {
        Payment payment = new Payment();
        payment.setDate(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        payment.setCustomer(new Customer(customerName));
        values.forEach(value -> payment.getProducts().add(new Product("Mocked Product", "Mocked Path", BigDecimal.valueOf(value))));
        return payment;
    }

}
