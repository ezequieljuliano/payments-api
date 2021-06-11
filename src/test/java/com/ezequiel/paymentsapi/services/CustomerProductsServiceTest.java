package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Customer;
import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;
import com.ezequiel.paymentsapi.repositories.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.ezequiel.paymentsapi.services.CustomerProductsServiceTestFixture.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class CustomerProductsServiceTest {

    private CustomerProductsService subject;
    private PaymentRepository paymentRepository;

    @BeforeEach
    public void setUp() {
        paymentRepository = Mockito.mock(PaymentRepository.class);
        subject = new CustomerProductsService(paymentRepository);
    }

    @Test
    public void shouldReturnProductsByCustomer() {
        List<Payment> payments = new ArrayList<>();
        payments.add(newMockedPayment1());
        payments.add(newMockedPayment2());

        given(paymentRepository.findAll()).willReturn(payments);

        Map<Customer, List<Product>> productsByCustomer = subject.getProductsByCustomer();
        Assertions.assertNotNull(productsByCustomer);
        Assertions.assertEquals(PRODUCTS_BY_CUSTOMERS_AMOUNT, productsByCustomer.size());

        Optional<Map.Entry<Customer, List<Product>>> customer1Billing = productsByCustomer.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getName().equals(CUSTOMER_NAME_1))
                .findAny();
        Assertions.assertTrue(customer1Billing.isPresent());
        Assertions.assertEquals(PRODUCTS_BY_CUSTOMER_1_AMOUNT, customer1Billing.get().getValue().size());

        Assertions.assertEquals(PRODUCT_NAME_1, customer1Billing.get().getValue().get(0).getName());
        Assertions.assertEquals(PRODUCT_FILE_1, customer1Billing.get().getValue().get(0).getFile());
        Assertions.assertEquals(PRODUCT_PRICE_1, customer1Billing.get().getValue().get(0).getPrice());

        Assertions.assertEquals(PRODUCT_NAME_2, customer1Billing.get().getValue().get(1).getName());
        Assertions.assertEquals(PRODUCT_FILE_2, customer1Billing.get().getValue().get(1).getFile());
        Assertions.assertEquals(PRODUCT_PRICE_2, customer1Billing.get().getValue().get(1).getPrice());

        Optional<Map.Entry<Customer, List<Product>>> customer2Billing = productsByCustomer.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getName().equals(CUSTOMER_NAME_2))
                .findAny();
        Assertions.assertTrue(customer2Billing.isPresent());
        Assertions.assertEquals(PRODUCTS_BY_CUSTOMER_2_AMOUNT, customer2Billing.get().getValue().size());

        Assertions.assertEquals(PRODUCT_NAME_3, customer2Billing.get().getValue().get(0).getName());
        Assertions.assertEquals(PRODUCT_FILE_3, customer2Billing.get().getValue().get(0).getFile());
        Assertions.assertEquals(PRODUCT_PRICE_3, customer2Billing.get().getValue().get(0).getPrice());

        Assertions.assertEquals(PRODUCT_NAME_4, customer2Billing.get().getValue().get(1).getName());
        Assertions.assertEquals(PRODUCT_FILE_4, customer2Billing.get().getValue().get(1).getFile());
        Assertions.assertEquals(PRODUCT_PRICE_4, customer2Billing.get().getValue().get(1).getPrice());

        verify(paymentRepository).findAll();
        verifyNoMoreInteractions(paymentRepository);
    }

}
