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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        payments.add(createMockedPayment(Arrays.asList(100.0, 200.0)));
        payments.add(createMockedPayment(Arrays.asList(300.0, 400.0)));

        given(paymentRepository.findAll()).willReturn(payments);

        BigDecimal totalPaymentsAmount = subject.getTotalPaymentsAmount();
        Assertions.assertNotNull(totalPaymentsAmount);
        Assertions.assertEquals(BigDecimal.valueOf(1000.0), totalPaymentsAmount);
        verify(paymentRepository).findAll();
        verifyNoMoreInteractions(paymentRepository);
    }

    private Payment createMockedPayment(List<Double> values) {
        Payment payment = new Payment();
        payment.setDate(LocalDateTime.now());
        payment.setCustomer(new Customer("Mocked Customer"));
        values.forEach(value -> payment.getProducts().add(new Product("Mocked Product", "Mocked Path", BigDecimal.valueOf(value))));
        return payment;
    }

}
