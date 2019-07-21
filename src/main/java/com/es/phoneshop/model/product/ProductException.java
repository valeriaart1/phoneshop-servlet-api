package com.es.phoneshop.model.product;

public class ProductNotFoundException {
    private long productId;

    public ProductNotFoundException(Long productId) {
        this.productId = productId;
    }
}
