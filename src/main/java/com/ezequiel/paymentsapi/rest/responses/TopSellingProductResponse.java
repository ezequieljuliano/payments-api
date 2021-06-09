package com.ezequiel.paymentsapi.rest.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class TopSellingProductResponse implements Serializable {

    private String product;
    private Long quantity;

}
