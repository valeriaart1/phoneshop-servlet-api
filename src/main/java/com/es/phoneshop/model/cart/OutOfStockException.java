package com.es.phoneshop.model.cart;

public class OutOfStockException extends RuntimeException{
    private int maxStock;

    public OutOfStockException(int maxStock) {
        this.maxStock = maxStock;
    }

    public int getMaxStock() {
        return maxStock;
    }
}