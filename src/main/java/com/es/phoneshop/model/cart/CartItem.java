package com.es.phoneshop.model.cart;

public class CartItem {
    private Long productId;
    private int quantity;

    public CartItem(Long productId, int quantity) {
        if(productId == null) {
            throw new IllegalArgumentException("product is required");
        }
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product " + productId + ", " + quantity;
    }
}