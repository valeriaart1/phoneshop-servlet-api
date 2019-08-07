package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

public class CartItemDeleteServlet extends HttpServlet {
    private CartService cartService;
    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ProductDaoImpl.getInstance();
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long productId = Long.valueOf(loadProduct(request));
        Cart cart = cartService.getCart(request);
        cartService.delete(cart, productId);

        response.sendRedirect(request.getContextPath()
                + "/cart?message=Product deleted successfully");
    }

    private Long loadProduct(HttpServletRequest request) throws NoSuchElementException {
        String idString = request.getRequestURI().substring((request.getContextPath() + request.getServletPath()).length()+1);
        Long id = Long.parseLong(idString);
        return id;
    }
}
