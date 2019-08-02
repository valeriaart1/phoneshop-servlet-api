package com.es.phoneshop.model.product;

public class ProductNotFoundException extends RuntimeException{
    public Long getProductIdNotFound(Long productId) {
        return productId;
    }
}