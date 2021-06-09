package com.ezequiel.paymentsapi.rest.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MonthlyBillingResponse implements Serializable {

    private final int year;
    private final int month;
    private BigDecimal amount;

}
