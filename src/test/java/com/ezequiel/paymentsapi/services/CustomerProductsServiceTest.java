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
import java.util.*;

import static com.ezequiel.paymentsapi.services.CustomerProductsServiceTestFixture.aMockedPaymentByCustomerNameAndProducts;
import static org.mockito.BDDMockito.given;

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
        payments.add(aMockedPaymentByCustomerNameAndProducts("Customer 1", Arrays.asList(
                new Product("Mocked Product 1", "Mocked Path 1", BigDecimal.valueOf(100.0)),
                new Product("Mocked Product 2", "Mocked Path 2", BigDecimal.valueOf(200.0))
        )));
        payments.add(aMockedPaymentByCustomerNameAndProducts("Customer 2", Arrays.asList(
                new Product("Mocked Product 3", "Mocked Path 3", BigDecimal.valueOf(100.0)),
                new Product("Mocked Product 4", "Mocked Path 4", BigDecimal.valueOf(200.0))
        )));

        given(paymentRepository.findAll()).willReturn(payments);

        Map<Customer, List<Product>> productsByCustomer = subject.getProductsByCustomer();
        Assertions.assertNotNull(productsByCustomer);
        Assertions.assertEquals(2, productsByCustomer.size());

        Optional<Map.Entry<Customer, List<Product>>> customer1Billing = productsByCustomer.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getName().equals("Customer 1"))
                .findAny();
        Assertions.assertTrue(customer1Billing.isPresent());
        Assertions.assertEquals(2, customer1Billing.get().getValue().size());
        Assertions.assertEquals("Mocked Product 1", customer1Billing.get().getValue().get(0).getName());
        Assertions.assertEquals("Mocked Path 1", customer1Billing.get().getValue().get(0).getFile());
        Assertions.assertEquals(BigDecimal.valueOf(100.0), customer1Billing.get().getValue().get(0).getPrice());
        Assertions.assertEquals("Mocked Product 2", customer1Billing.get().getValue().get(1).getName());
        Assertions.assertEquals("Mocked Path 2", customer1Billing.get().getValue().get(1).getFile());
        Assertions.assertEquals(BigDecimal.valueOf(200.0), customer1Billing.get().getValue().get(1).getPrice());

        Optional<Map.Entry<Customer, List<Product>>> customer2Billing = productsByCustomer.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getName().equals("Customer 2"))
                .findAny();
        Assertions.assertTrue(customer2Billing.isPresent());
        Assertions.assertEquals(2, customer2Billing.get().getValue().size());
        Assertions.assertEquals("Mocked Product 3", customer2Billing.get().getValue().get(0).getName());
        Assertions.assertEquals("Mocked Path 3", customer2Billing.get().getValue().get(0).getFile());
        Assertions.assertEquals(BigDecimal.valueOf(100.0), customer2Billing.get().getValue().get(0).getPrice());
        Assertions.assertEquals("Mocked Product 4", customer2Billing.get().getValue().get(1).getName());
        Assertions.assertEquals("Mocked Path 4", customer2Billing.get().getValue().get(1).getFile());
        Assertions.assertEquals(BigDecimal.valueOf(200.0), customer2Billing.get().getValue().get(1).getPrice());
    }
}
