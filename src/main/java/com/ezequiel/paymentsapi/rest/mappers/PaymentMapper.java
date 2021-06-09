package com.ezequiel.paymentsapi.rest.mappers;

import com.ezequiel.paymentsapi.rest.responses.*;
import com.ezequiel.paymentsapi.entities.Customer;
import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface PaymentMapper {

    @Mapping(source = "customer.name", target = "customer")
    SortedPaymentResponse createSortedPaymentResponse(Payment payment);

    SortedPaymentResponse.ProductResponse createSortedPaymentProductResponse(Product product);

    List<SortedPaymentResponse> createSortedPaymentResponseList(List<Payment> payments);

    default TopSellingProductResponse createTopSellingProductResponse(Map.Entry<String, Long> productQuantity) {
        return new TopSellingProductResponse(productQuantity.getKey(), productQuantity.getValue());
    }

    default List<TopSellingProductResponse> createTopSellingProductResponseList(Map<String, Long> topSellingProducts) {
        return topSellingProducts
                .entrySet()
                .stream()
                .map(this::createTopSellingProductResponse)
                .sorted(Comparator.comparing(TopSellingProductResponse::getQuantity).reversed())
                .collect(Collectors.toList());
    }

    default TopBillingProductResponse createTopBillingProductResponse(Map.Entry<String, BigDecimal> productAmount) {
        return new TopBillingProductResponse(productAmount.getKey(), productAmount.getValue());
    }

    default List<TopBillingProductResponse> createTopBillingProductResponseList(Map<String, BigDecimal> topBillingProducts) {
        return topBillingProducts
                .entrySet()
                .stream()
                .map(this::createTopBillingProductResponse)
                .sorted(Comparator.comparing(TopBillingProductResponse::getAmount).reversed())
                .collect(Collectors.toList());
    }

    CustomerProductsResponse.ProductResponse createCustomerProductResponse(Product product);

    List<CustomerProductsResponse.ProductResponse> createCustomerProductResponseList(List<Product> products);

    default CustomerProductsResponse createCustomerProductsResponse(Customer customer, List<Product> products) {
        return new CustomerProductsResponse(customer.getName(), createCustomerProductResponseList(products));
    }

    default List<CustomerProductsResponse> createCustomerProductsResponseList(Map<Customer, List<Product>> customersProducts) {
        return customersProducts
                .entrySet()
                .stream()
                .map(entry -> createCustomerProductsResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    default CustomerBillingResponse createCustomerBillingResponse(Map.Entry<Customer, BigDecimal> customerAmount) {
        return new CustomerBillingResponse(customerAmount.getKey().getName(), customerAmount.getValue());
    }

    default List<CustomerBillingResponse> createCustomerBillingResponseList(Map<Customer, BigDecimal> customersAmount) {
        return customersAmount
                .entrySet()
                .stream()
                .map(this::createCustomerBillingResponse)
                .sorted(Comparator.comparing(CustomerBillingResponse::getAmount).reversed())
                .collect(Collectors.toList());
    }

    default MonthlyBillingResponse createMonthlyBillingResponse(Map.Entry<YearMonth, BigDecimal> monthlyAmount) {
        return new MonthlyBillingResponse(monthlyAmount.getKey().getYear(), monthlyAmount.getKey().getMonthValue(), monthlyAmount.getValue());
    }

    default List<MonthlyBillingResponse> createMonthlyBillingResponseList(Map<YearMonth, BigDecimal> monthlyAmount) {
        return monthlyAmount
                .entrySet()
                .stream()
                .map(this::createMonthlyBillingResponse)
                .sorted(Comparator.comparing(MonthlyBillingResponse::getYear).thenComparing(MonthlyBillingResponse::getMonth).reversed())
                .collect(Collectors.toList());
    }

}
