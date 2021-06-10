package com.ezequiel.paymentsapi.services;

import com.ezequiel.paymentsapi.entities.Customer;
import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;

import java.time.LocalDateTime;
import java.util.List;

public class TopBillingProductsServiceTestFixture {

    public static Payment aMockedPaymentByCustomerNameAndProducts(String customerName, List<Product> products) {
        Payment payment = new Payment();
        payment.setDate(LocalDateTime.now());
        payment.setCustomer(new Customer(customerName));
        payment.getProducts().addAll(products);
        return payment;
    }

}
