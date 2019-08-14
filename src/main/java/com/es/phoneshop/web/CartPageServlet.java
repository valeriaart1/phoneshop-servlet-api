package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class CartPageServlet extends HttpServlet {
    private static final String MESSAGE_SUCCESS_UPDATING = "?message=Updated successfully";
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request.getSession()));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Cart cart = cartService.getCart(request.getSession());
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        Locale locale = request.getLocale();

        Map<Long, String> errors = cartService.update(request.getSession(), cart, productIds, quantities, locale);
        if (errors.isEmpty()) {
            response.sendRedirect(request.getRequestURI() + MESSAGE_SUCCESS_UPDATING);
        } else {
            request.setAttribute("errors", errors);
            doGet(request, response);
        }
    }
}
