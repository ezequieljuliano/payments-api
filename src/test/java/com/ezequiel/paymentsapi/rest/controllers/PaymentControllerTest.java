package com.ezequiel.paymentsapi.rest.controllers;

import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.rest.mappers.PaymentMapper;
import com.ezequiel.paymentsapi.rest.mappers.PaymentMapperImpl;
import com.ezequiel.paymentsapi.rest.responses.*;
import com.ezequiel.paymentsapi.services.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static com.ezequiel.paymentsapi.rest.controllers.PaymentControllerTestFixture.*;
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
        subject = new PaymentController(
                paymentMapper,
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
        payments.add(newMockedPaymentByDate(DATE_TIME_1));
        payments.add(newMockedPaymentByDate(DATE_TIME_2));
        payments.add(newMockedPaymentByDate(DATE_TIME_3));

        given(sortedPaymentsService.getSortedPaymentsByDate()).willReturn(payments);

        ResponseEntity<List<SortedPaymentResponse>> sortedPaymentsByDate = subject.getSortedPaymentsByDate();

        Assertions.assertNotNull(sortedPaymentsByDate);
        Assertions.assertNotNull(sortedPaymentsByDate.getBody());

        Assertions.assertEquals(AMOUNT_OF_SORTED_PAYMENTS, sortedPaymentsByDate.getBody().size());
        Assertions.assertEquals(DATE_TIME_1, sortedPaymentsByDate.getBody().get(0).getDate().format(DATE_TIME_FORMATTER));
        Assertions.assertEquals(DATE_TIME_2, sortedPaymentsByDate.getBody().get(1).getDate().format(DATE_TIME_FORMATTER));
        Assertions.assertEquals(DATE_TIME_3, sortedPaymentsByDate.getBody().get(2).getDate().format(DATE_TIME_FORMATTER));

        verify(sortedPaymentsService).getSortedPaymentsByDate();
        verifyNoMoreInteractions(sortedPaymentsService);
    }

    @Test
    public void shouldReturnTotalPaymentsAmount() {
        given(totalAmountPaymentsService.getTotalPaymentsAmount()).willReturn(TOTAL_PAYMENTS_AMOUNT);

        ResponseEntity<TotalAmountPaymentResponse> totalPaymentsAmount = subject.getTotalPaymentsAmount();

        Assertions.assertNotNull(totalPaymentsAmount);
        Assertions.assertNotNull(totalPaymentsAmount.getBody());

        Assertions.assertEquals(TOTAL_PAYMENTS_AMOUNT, totalPaymentsAmount.getBody().getTotalAmount());

        verify(totalAmountPaymentsService).getTotalPaymentsAmount();
        verifyNoMoreInteractions(totalAmountPaymentsService);
    }

    @Test
    public void shouldReturnTopSellingProductsNamesAndQuantities() {
        given(topSellingProductsService.getTopSellingProductsNamesAndQuantities()).willReturn(newMockedProductsNamesAndQuantities());

        ResponseEntity<List<TopSellingProductResponse>> topSellingProductsNamesAndQuantities = subject.getTopSellingProductsNamesAndQuantities();

        Assertions.assertNotNull(topSellingProductsNamesAndQuantities);
        Assertions.assertNotNull(topSellingProductsNamesAndQuantities.getBody());

        Assertions.assertEquals(PRODUCTS_AMOUNT, topSellingProductsNamesAndQuantities.getBody().size());
        Assertions.assertEquals(PRODUCT_NAME_1, topSellingProductsNamesAndQuantities.getBody().get(0).getProduct());
        Assertions.assertEquals(PRODUCT_QUANTITY_1, topSellingProductsNamesAndQuantities.getBody().get(0).getQuantity());
        Assertions.assertEquals(PRODUCT_NAME_2, topSellingProductsNamesAndQuantities.getBody().get(1).getProduct());
        Assertions.assertEquals(PRODUCT_QUANTITY_2, topSellingProductsNamesAndQuantities.getBody().get(1).getQuantity());

        verify(topSellingProductsService).getTopSellingProductsNamesAndQuantities();
        verifyNoMoreInteractions(topSellingProductsService);
    }

    @Test
    public void shouldReturnTopBillingProductsNamesAndAmount() {
        given(topBillingProductsService.getTopBillingProductsNamesAndAmount()).willReturn(newMockedProductsNamesAndAmount());

        ResponseEntity<List<TopBillingProductResponse>> topBillingProductsNamesAndQuantities = subject.getTopBillingProductsNamesAndAmount();

        Assertions.assertNotNull(topBillingProductsNamesAndQuantities);
        Assertions.assertNotNull(topBillingProductsNamesAndQuantities.getBody());

        Assertions.assertEquals(PRODUCTS_AMOUNT, topBillingProductsNamesAndQuantities.getBody().size());
        Assertions.assertEquals(PRODUCT_NAME_2, topBillingProductsNamesAndQuantities.getBody().get(0).getProduct());
        Assertions.assertEquals(PRODUCT_AMOUNT_2, topBillingProductsNamesAndQuantities.getBody().get(0).getAmount());
        Assertions.assertEquals(PRODUCT_NAME_1, topBillingProductsNamesAndQuantities.getBody().get(1).getProduct());
        Assertions.assertEquals(PRODUCT_AMOUNT_1, topBillingProductsNamesAndQuantities.getBody().get(1).getAmount());

        verify(topBillingProductsService).getTopBillingProductsNamesAndAmount();
        verifyNoMoreInteractions(topBillingProductsService);
    }

    @Test
    public void shouldReturnProductsByCustomer() {
        given(customerProductsService.getProductsByCustomer()).willReturn(newMockedProductsByCustomer());

        ResponseEntity<List<CustomerProductsResponse>> productsByCustomer = subject.getProductsByCustomer();

        Assertions.assertNotNull(productsByCustomer);
        Assertions.assertNotNull(productsByCustomer.getBody());
        Assertions.assertEquals(PRODUCTS_AMOUNT, productsByCustomer.getBody().size());

        Assertions.assertEquals(PRODUCTS_AMOUNT, productsByCustomer.getBody().get(0).getProducts().size());
        Assertions.assertEquals(PRODUCT_NAME_1, productsByCustomer.getBody().get(0).getProducts().get(0).getName());
        Assertions.assertEquals(PRODUCT_NAME_2, productsByCustomer.getBody().get(0).getProducts().get(1).getName());

        Assertions.assertEquals(PRODUCTS_AMOUNT, productsByCustomer.getBody().get(1).getProducts().size());
        Assertions.assertEquals(PRODUCT_NAME_1, productsByCustomer.getBody().get(1).getProducts().get(0).getName());
        Assertions.assertEquals(PRODUCT_NAME_2, productsByCustomer.getBody().get(1).getProducts().get(1).getName());

        verify(customerProductsService).getProductsByCustomer();
        verifyNoMoreInteractions(customerProductsService);
    }

    @Test
    public void shouldReturnCustomersBilling() {
        given(customerBillingService.getCustomersBilling()).willReturn(newMockedCustomersBilling());

        ResponseEntity<List<CustomerBillingResponse>> customersBilling = subject.getCustomersBilling();
        Assertions.assertNotNull(customersBilling);
        Assertions.assertNotNull(customersBilling.getBody());
        Assertions.assertEquals(CUSTOMER_BILLING_AMOUNT, customersBilling.getBody().size());

        Assertions.assertEquals(CUSTOMER_NAME_2, customersBilling.getBody().get(0).getCustomer());
        Assertions.assertEquals(CUSTOMER_BILLING_2, customersBilling.getBody().get(0).getAmount());

        Assertions.assertEquals(CUSTOMER_NAME_1, customersBilling.getBody().get(1).getCustomer());
        Assertions.assertEquals(CUSTOMER_BILLING_1, customersBilling.getBody().get(1).getAmount());

        verify(customerBillingService).getCustomersBilling();
        verifyNoMoreInteractions(customerBillingService);
    }

    @Test
    public void shouldReturnMonthlyBilling() {
        given(monthlyBillingService.getMonthlyBilling()).willReturn(newMockedMonthlyBilling());

        ResponseEntity<List<MonthlyBillingResponse>> monthlyBilling = subject.getMonthlyBilling();

        Assertions.assertNotNull(monthlyBilling);
        Assertions.assertNotNull(monthlyBilling.getBody());
        Assertions.assertEquals(MONTHLY_BILLING_AMOUNT, monthlyBilling.getBody().size());

        Assertions.assertEquals(JUNE_BILLING, monthlyBilling.getBody().get(0).getMonth());
        Assertions.assertEquals(YEAR_BILLING, monthlyBilling.getBody().get(0).getYear());
        Assertions.assertEquals(JUNE_BILLING_TOTAL, monthlyBilling.getBody().get(0).getAmount());

        Assertions.assertEquals(MAY_BILLING, monthlyBilling.getBody().get(1).getMonth());
        Assertions.assertEquals(YEAR_BILLING, monthlyBilling.getBody().get(1).getYear());
        Assertions.assertEquals(MAY_BILLING_TOTAL, monthlyBilling.getBody().get(1).getAmount());

        verify(monthlyBillingService).getMonthlyBilling();
        verifyNoMoreInteractions(monthlyBillingService);
    }

}
