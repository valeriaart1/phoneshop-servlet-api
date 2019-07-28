package com.es.phoneshop.model.product;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long productId) {
        super("Product with id = " + productId + " nor found!");
    }
}