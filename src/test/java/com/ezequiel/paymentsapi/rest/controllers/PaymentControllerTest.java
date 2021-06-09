package com.ezequiel.paymentsapi.rest.controllers;

import com.ezequiel.paymentsapi.entities.Customer;
import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;
import com.ezequiel.paymentsapi.rest.mappers.PaymentMapper;
import com.ezequiel.paymentsapi.rest.mappers.PaymentMapperImpl;
import com.ezequiel.paymentsapi.rest.responses.*;
import com.ezequiel.paymentsapi.services.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.ezequiel.paymentsapi.rest.controllers.PaymentControllerTestFixture.aMockedPaymentByDateAndCustomerName;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class PaymentControllerTest {

    private PaymentController subject;
    private SortedPaymentsService sortedPaymentsService;
    private TotalAmountPaymentsService totalAmountPaymentsService;
    private TopSellingProductsService topSellingProductsService;
    private TopBillingProductsService topBillingProductsService;
    private CustomerProductsService customerProductsService;
    private CustomerBillingService customerBillingService;
    private MonthlyBillingService monthlyBillingService;
    private final PaymentMapper paymentMapper = new PaymentMapperImpl();

    @BeforeEach
    public void setUp() {
        sortedPaymentsService = Mockito.mock(SortedPaymentsService.class);
        totalAmountPaymentsService = Mockito.mock(TotalAmountPaymentsService.class);
        topSellingProductsService = Mockito.mock(TopSellingProductsService.class);
        topBillingProductsService = Mockito.mock(TopBillingProductsService.class);
        customerProductsService = Mockito.mock(CustomerProductsService.class);
        customerBillingService = Mockito.mock(CustomerBillingService.class);
        monthlyBillingService = Mockito.mock(MonthlyBillingService.class);
        subject = new PaymentController(paymentMapper,
                sortedPaymentsService,
                totalAmountPaymentsService,
                topSellingProductsService,
                topBillingProductsService,
                customerProductsService,
                customerBillingService,
                monthlyBillingService
        );
    }

    @Test
    public void shouldReturnSortedPaymentsByDate() {
        List<Payment> payments = new ArrayList<>();
        payments.add(aMockedPaymentByDateAndCustomerName("2021-06-05 11:30", "Customer 1"));
        payments.add(aMockedPaymentByDateAndCustomerName("2021-06-06 08:30", "Customer 2"));
        payments.add(aMockedPaymentByDateAndCustomerName("2021-06-07 19:30", "Customer 3"));

        given(sortedPaymentsService.getSortedPaymentsByDate()).willReturn(payments);

        ResponseEntity<List<SortedPaymentResponse>> sortedPaymentsByDate = subject.getSortedPaymentsByDate();
        Assertions.assertNotNull(sortedPaymentsByDate);
        Assertions.assertNotNull(sortedPaymentsByDate.getBody());
        Assertions.assertEquals(3, sortedPaymentsByDate.getBody().size());
        Assertions.assertEquals("2021-06-05 11:30", sortedPaymentsByDate.getBody().get(0).getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        Assertions.assertEquals("2021-06-06 08:30", sortedPaymentsByDate.getBody().get(1).getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        Assertions.assertEquals("2021-06-07 19:30", sortedPaymentsByDate.getBody().get(2).getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        verify(sortedPaymentsService).getSortedPaymentsByDate();
        verifyNoMoreInteractions(sortedPaymentsService);
    }

    @Test
    public void shouldReturnTotalPaymentsAmount() {
        given(totalAmountPaymentsService.getTotalPaymentsAmount()).willReturn(BigDecimal.valueOf(500.0));

        ResponseEntity<TotalAmountPaymentResponse> totalPaymentsAmount = subject.getTotalPaymentsAmount();
        Assertions.assertNotNull(totalPaymentsAmount);
        Assertions.assertNotNull(totalPaymentsAmount.getBody());
        Assertions.assertEquals(BigDecimal.valueOf(500.0), totalPaymentsAmount.getBody().getTotalAmount());
        verify(totalAmountPaymentsService).getTotalPaymentsAmount();
        verifyNoMoreInteractions(totalAmountPaymentsService);
    }

    @Test
    public void shouldReturnTopSellingProductsNamesAndQuantities() {
        Map<String, Long> productsNamesAndQuantities = new HashMap<>();
        productsNamesAndQuantities.put("Product 1", 5L);
        productsNamesAndQuantities.put("Product 2", 3L);

        given(topSellingProductsService.getTopSellingProductsNamesAndQuantities()).willReturn(productsNamesAndQuantities);

        ResponseEntity<List<TopSellingProductResponse>> topSellingProductsNamesAndQuantities = subject.getTopSellingProductsNamesAndQuantities();
        Assertions.assertNotNull(topSellingProductsNamesAndQuantities);
        Assertions.assertNotNull(topSellingProductsNamesAndQuantities.getBody());
        Assertions.assertEquals(2, topSellingProductsNamesAndQuantities.getBody().size());
        Assertions.assertEquals("Product 1", topSellingProductsNamesAndQuantities.getBody().get(0).getProduct());
        Assertions.assertEquals(5L, topSellingProductsNamesAndQuantities.getBody().get(0).getQuantity());
        Assertions.assertEquals("Product 2", topSellingProductsNamesAndQuantities.getBody().get(1).getProduct());
        Assertions.assertEquals(3L, topSellingProductsNamesAndQuantities.getBody().get(1).getQuantity());
        verify(topSellingProductsService).getTopSellingProductsNamesAndQuantities();
        verifyNoMoreInteractions(topSellingProductsService);
    }

    @Test
    public void shouldReturnTopBillingProductsNamesAndAmount() {
        Map<String, BigDecimal> productsNamesAndAmount = new HashMap<>();
        productsNamesAndAmount.put("Product 1", BigDecimal.valueOf(100.0));
        productsNamesAndAmount.put("Product 2", BigDecimal.valueOf(150.0));

        given(topBillingProductsService.getTopBillingProductsNamesAndAmount()).willReturn(productsNamesAndAmount);

        ResponseEntity<List<TopBillingProductResponse>> topBillingProductsNamesAndQuantities = subject.getTopBillingProductsNamesAndAmount();
        Assertions.assertNotNull(topBillingProductsNamesAndQuantities);
        Assertions.assertNotNull(topBillingProductsNamesAndQuantities.getBody());
        Assertions.assertEquals(2, topBillingProductsNamesAndQuantities.getBody().size());
        Assertions.assertEquals("Product 2", topBillingProductsNamesAndQuantities.getBody().get(0).getProduct());
        Assertions.assertEquals(BigDecimal.valueOf(150.0), topBillingProductsNamesAndQuantities.getBody().get(0).getAmount());
        Assertions.assertEquals("Product 1", topBillingProductsNamesAndQuantities.getBody().get(1).getProduct());
        Assertions.assertEquals(BigDecimal.valueOf(100.0), topBillingProductsNamesAndQuantities.getBody().get(1).getAmount());
        verify(topBillingProductsService).getTopBillingProductsNamesAndAmount();
        verifyNoMoreInteractions(topBillingProductsService);
    }

    @Test
    public void shouldReturnProductsByCustomer() {
        Map<Customer, List<Product>> productsByCustomerMocked = new HashMap<>();
        productsByCustomerMocked.put(new Customer("Customer 1"), Arrays.asList(
                new Product("Product 1", "Path 1", BigDecimal.valueOf(100.0)),
                new Product("Product 2", "Path 2", BigDecimal.valueOf(200.0))
        ));
        productsByCustomerMocked.put(new Customer("Customer 2"), Arrays.asList(
                new Product("Product 1", "Path 1", BigDecimal.valueOf(100.0)),
                new Product("Product 2", "Path 2", BigDecimal.valueOf(200.0))
        ));

        given(customerProductsService.getProductsByCustomer()).willReturn(productsByCustomerMocked);

        ResponseEntity<List<CustomerProductsResponse>> productsByCustomer = subject.getProductsByCustomer();
        Assertions.assertNotNull(productsByCustomer);
        Assertions.assertNotNull(productsByCustomer.getBody());
        Assertions.assertEquals(2, productsByCustomer.getBody().size());

        Assertions.assertEquals(2, productsByCustomer.getBody().get(0).getProducts().size());
        Assertions.assertEquals("Product 1", productsByCustomer.getBody().get(0).getProducts().get(0).getName());
        Assertions.assertEquals("Product 2", productsByCustomer.getBody().get(0).getProducts().get(1).getName());

        Assertions.assertEquals(2, productsByCustomer.getBody().get(1).getProducts().size());
        Assertions.assertEquals("Product 1", productsByCustomer.getBody().get(1).getProducts().get(0).getName());
        Assertions.assertEquals("Product 2", productsByCustomer.getBody().get(1).getProducts().get(1).getName());
        verify(customerProductsService).getProductsByCustomer();
        verifyNoMoreInteractions(customerProductsService);
    }

    @Test
    public void shouldReturnCustomersBilling() {
        Map<Customer, BigDecimal> customersBillingMocked = new HashMap<>();
        customersBillingMocked.put(new Customer("Customer 1"), BigDecimal.valueOf(200.0));
        customersBillingMocked.put(new Customer("Customer 2"), BigDecimal.valueOf(400.0));

        given(customerBillingService.getCustomersBilling()).willReturn(customersBillingMocked);

        ResponseEntity<List<CustomerBillingResponse>> customersBilling = subject.getCustomersBilling();
        Assertions.assertNotNull(customersBilling);
        Assertions.assertNotNull(customersBilling.getBody());
        Assertions.assertEquals(2, customersBilling.getBody().size());

        Assertions.assertEquals("Customer 2", customersBilling.getBody().get(0).getCustomer());
        Assertions.assertEquals(BigDecimal.valueOf(400.0), customersBilling.getBody().get(0).getAmount());
        Assertions.assertEquals("Customer 1", customersBilling.getBody().get(1).getCustomer());
        Assertions.assertEquals(BigDecimal.valueOf(200.0), customersBilling.getBody().get(1).getAmount());
        verify(customerBillingService).getCustomersBilling();
        verifyNoMoreInteractions(customerBillingService);
    }

    @Test
    public void shouldReturnMonthlyBilling() {
        Map<YearMonth, BigDecimal> monthlyBillingMocked = new HashMap<>();
        monthlyBillingMocked.put(YearMonth.of(2021, Month.MAY), BigDecimal.valueOf(100.0));
        monthlyBillingMocked.put(YearMonth.of(2021, Month.JUNE), BigDecimal.valueOf(300.0));

        given(monthlyBillingService.getMonthlyBilling()).willReturn(monthlyBillingMocked);

        ResponseEntity<List<MonthlyBillingResponse>> monthlyBilling = subject.getMonthlyBilling();
        Assertions.assertNotNull(monthlyBilling);
        Assertions.assertNotNull(monthlyBilling.getBody());
        Assertions.assertEquals(2, monthlyBilling.getBody().size());

        Assertions.assertEquals(6, monthlyBilling.getBody().get(0).getMonth());
        Assertions.assertEquals(2021, monthlyBilling.getBody().get(0).getYear());
        Assertions.assertEquals(BigDecimal.valueOf(300.0), monthlyBilling.getBody().get(0).getAmount());
        Assertions.assertEquals(5, monthlyBilling.getBody().get(1).getMonth());
        Assertions.assertEquals(2021, monthlyBilling.getBody().get(1).getYear());
        Assertions.assertEquals(BigDecimal.valueOf(100.0), monthlyBilling.getBody().get(1).getAmount());
        verify(monthlyBillingService).getMonthlyBilling();
        verifyNoMoreInteractions(monthlyBillingService);
    }
}
