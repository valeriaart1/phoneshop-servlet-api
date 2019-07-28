package com.es.phoneshop.model.product;

public class ProductNotFoundException extends RuntimeException{
    private long productId;

    public ProductNotFoundException(Long productId) {
        this.productId = productId;
    }
}