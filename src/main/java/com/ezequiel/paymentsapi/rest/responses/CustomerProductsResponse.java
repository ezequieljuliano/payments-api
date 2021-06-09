package com.ezequiel.paymentsapi.rest.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class CustomerProductsResponse implements Serializable {

    @Data
    @NoArgsConstructor
    public static class ProductResponse implements Serializable {
        private String name;
    }

    private String customer;
    private List<ProductResponse> products;

}
