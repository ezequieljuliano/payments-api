package com.ezequiel.paymentsapi.rest.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class SortedPaymentResponse implements Serializable {

    @Data
    @NoArgsConstructor
    public static class ProductResponse implements Serializable {
        private String name;
    }

    private LocalDateTime date;
    private String customer;
    private List<ProductResponse> products;

}
