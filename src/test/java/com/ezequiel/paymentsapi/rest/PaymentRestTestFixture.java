package com.ezequiel.paymentsapi.rest;

import com.ezequiel.paymentsapi.entities.Customer;
import com.ezequiel.paymentsapi.entities.Payment;
import com.ezequiel.paymentsapi.entities.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PaymentRestTestFixture {

    public static Payment aMockedPaymentByDateCustomerNameAndProducts(String date,
                                                                      String customerName,
                                                                      List<Product> products) {
        Payment payment = new Payment();
        payment.setDate(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        payment.setCustomer(new Customer(customerName));
        payment.getProducts().addAll(products);
        return payment;
    }

}
