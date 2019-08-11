package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.orderException.Validator;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
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

        order.setDeliveryCost(new BigDecimal(5));
        order.setDeliveryDate(new Date(new Date().getTime()+24*3600000));
        order.setOrderCost(order.getDeliveryCost().add(cart.getTotalCost()));
        return order;
    }

    @Override
    public Order placeOrder(Order order) {
        order.setSecureId(UUID.randomUUID().toString());
        OrderDaoImpl.getInstance().save(order);
        return order;
    }

    @Override
    public Map<String, String> isOrderValid(Map<String, String> errors, Order order, HttpServletRequest request) {
        errors.put("firstNameError", Validator.validateFirstName(order, request.getParameter("firstName")));
        errors.put("lastNameError", Validator.validateLastName(order, request.getParameter("lastName")));
        errors.put("phoneError", Validator.validatePhone(order, request.getParameter("phone")));
        DeliveryMode.setDeliveryMode(request.getParameter("deliveryMode"), order);
        errors.put("deliveryAddressError", Validator.validateDeliveryAddress(order, request.getParameter("deliveryAddress")));
        PaymentMethod.setPaymentMethod(request.getParameter("paymentMethod"), order);
        return errors;
    }
}
