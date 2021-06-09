package com.ezequiel.paymentsapi.rest.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TotalAmountPaymentResponse implements Serializable {

    private BigDecimal totalAmount;

}
