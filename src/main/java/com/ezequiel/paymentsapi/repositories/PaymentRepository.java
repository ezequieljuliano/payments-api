package com.ezequiel.paymentsapi.repositories;

import com.ezequiel.paymentsapi.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
