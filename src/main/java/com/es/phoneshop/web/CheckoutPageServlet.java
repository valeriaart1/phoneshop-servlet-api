package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.order.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CheckoutPageServlet extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = CartServiceImpl.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request.getSession());
        Order order = orderService.createOrder(cart);
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        String dayDelivery = newDateFormat.format(order.getDeliveryDate());
        request.setAttribute("dayDelivery", dayDelivery);
        request.getSession(true).setAttribute("order", order);
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Cart cart = cartService.getCart(request.getSession());
        Order order = orderService.createOrder(cart);
        Map<String, String> errors = new HashMap<>();

        errors = orderService.isOrderValid(errors, order, request);

        if (errors.get("") != null) {
            Set keys = errors.keySet();
            for (Object key : keys) {
                request.setAttribute((String) key, errors.get(key));
            }
            request.setAttribute("order", order);
            request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp")
                    .forward(request, response);
        } else {
            orderService.placeOrder(order);
            response.sendRedirect(request.getContextPath() + "/orderOverview/" + order.getSecureId());
        }
        cartService.cleanCart(cart);
    }
}