package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);

    String add(HttpServletRequest request, Cart cart, Long productId, String quantity);

    String update(HttpServletRequest request, Cart cart, Long productId, String quantity);

    void delete(Cart cart, Product product);
}