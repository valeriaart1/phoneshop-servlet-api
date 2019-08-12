package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    private static OrderServiceImpl instance;

    private OrderServiceImpl() {
    }

    public static OrderServiceImpl getInstance() {
        if (instance == null) {
            synchronized (OrderServiceImpl.class) {
                if (instance == null) {
                    instance = new OrderServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        order.setCartItems(new ArrayList<>(cart.getCartItems()));
        order.setTotalCost(cart.getTotalCost());
        order.setTotalQuantity(cart.getTotalQuantity());

        System.out.println(LocalDate.now().plusDays(1));
        order.setDeliveryDate(LocalDate.now().plusDays(1));
        return order;
    }

    @Override
    public Order placeOrder(Order order) {
        order.setSecureId(UUID.randomUUID().toString());
        OrderDaoImpl.getInstance().save(order);
        return order;
    }
}
