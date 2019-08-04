package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartPageServlet extends HttpServlet {
    private static final String MESSAGE_SUCCESS_UPDATING= "?message=Updated successfully";
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
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        Map<Long, String> errors = new HashMap();

        Cart cart = cartService.getCart(request.getSession());

        for(int i = 0; i < productIds.length; i++) {
            String QUANTITY_ERROR = cartService.update(request, cart, Long.valueOf(productIds[i]), quantities[i]);
            if(QUANTITY_ERROR != null) {
                errors.put(Long.valueOf(productIds[i]), QUANTITY_ERROR);
            }
        }
        request.setAttribute("errors", errors);
        if(errors.isEmpty()){
            response.sendRedirect(request.getRequestURI() + MESSAGE_SUCCESS_UPDATING);
        }
        else {
            doGet(request, response);
        }
    }
}
