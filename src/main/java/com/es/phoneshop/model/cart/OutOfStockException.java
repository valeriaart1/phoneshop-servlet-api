package com.es.phoneshop.model.cart;

public class OutOfStockException extends Exception {
    public int getMaxStock(int productStock) {
        return productStock;
    }
}