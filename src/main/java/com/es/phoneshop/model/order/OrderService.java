package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface OrderService {
    Order createOrder(Cart cart);

    Order placeOrder(Order order);
}
