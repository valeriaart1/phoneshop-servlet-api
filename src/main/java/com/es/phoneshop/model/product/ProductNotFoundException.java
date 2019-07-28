package com.es.phoneshop.model.product;

public class ProductNotFoundException extends RuntimeException {
    ProductNotFoundException(Long productId) {
        super("Product with id = " + productId + " nor found!");
    }
}