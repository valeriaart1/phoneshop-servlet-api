package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface CartService {
    Cart getCart(HttpSession session);

    String add(HttpServletRequest request, Cart cart, Long productId, String quantity);

    String update(HttpServletRequest request, Cart cart, Long productId, String quantity);

    void delete(Cart cart, Product product);
}