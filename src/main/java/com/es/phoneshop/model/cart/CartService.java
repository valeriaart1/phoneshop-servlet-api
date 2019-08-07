package com.es.phoneshop.model.cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;

public interface CartService {
    Cart getCart(HttpServletRequest request);

    String add(HttpSession session, Cart cart, Long productId, String quantity, Locale locale);

    Map<Long, String> update(HttpSession session, Cart cart, String[] productIds, String[] quantities, Locale locale);

    void delete(Cart cart, Long productId);
}