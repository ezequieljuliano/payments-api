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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        payments.add(createMockedPayment("2021-06-07 11:30", "Customer 1"));
        payments.add(createMockedPayment("2021-06-06 08:30", "Customer 2"));
        payments.add(createMockedPayment("2021-06-05 19:30", "Customer 3"));

        given(paymentRepository.findAll()).willReturn(payments);

        List<Payment> sortedPaymentsByDate = subject.getSortedPaymentsByDate();
        Assertions.assertNotNull(sortedPaymentsByDate);
        Assertions.assertEquals(3, sortedPaymentsByDate.size());
        Assertions.assertEquals("2021-06-05 19:30", sortedPaymentsByDate.get(0).getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        Assertions.assertEquals("2021-06-06 08:30", sortedPaymentsByDate.get(1).getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        Assertions.assertEquals("2021-06-07 11:30", sortedPaymentsByDate.get(2).getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        verify(paymentRepository).findAll();
        verifyNoMoreInteractions(paymentRepository);
    }

    private Payment createMockedPayment(String date, String customerName) {
        Payment payment = new Payment();
        payment.setDate(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        payment.setCustomer(new Customer(customerName));
        payment.getProducts().add(new Product("Mocked Product", "Mocked Path", BigDecimal.valueOf(100.0)));
        return payment;
    }

}
