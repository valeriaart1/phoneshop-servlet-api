package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductDaoImpl;
import com.es.phoneshop.model.product.ProductNotFoundException;
import com.es.phoneshop.model.viewed.ViewedProductsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Deque;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ProductPageServlet extends HttpServlet {
    private static final String MESSAGE_SUCCESS_ADDING = "?message=Added to cart successfully";

    private ProductDao productDao;
    private CartService cartService;
    private ViewedProductsServiceImpl viewedProducts;

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ProductDaoImpl.getInstance();
        cartService = CartServiceImpl.getInstance();
        viewedProducts = ViewedProductsServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = Long.valueOf(loadProduct(request));
        try{
            Product product = (Product) productDao.getProduct(productId);
            Deque<Product> dequeViewedProducts = viewedProducts.getViewedProducts(request.getSession());
            Cart cart = cartService.getCart(request);

            request.setAttribute("cart", cart);
            request.setAttribute("viewedProducts", dequeViewedProducts);
            request.setAttribute("product", product);
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp")
                    .forward(request, response);
            viewedProducts.addViewedProducts(viewedProducts.getViewedProducts(request.getSession()), product);
        }
        catch(ProductNotFoundException exception) {
            response.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Cart cart = cartService.getCart(request);
        Long productId = Long.valueOf(loadProduct(request));
        String quantity = request.getParameter("quantity");
        Locale locale = request.getLocale();

        String QUANTITY_ERROR = cartService.add(request.getSession(), cart, productId, quantity, locale);
        if(QUANTITY_ERROR == null){
            response.sendRedirect(request.getContextPath() + request.getServletPath() +
                    request.getPathInfo() + MESSAGE_SUCCESS_ADDING);
            return;
        } else {
            request.setAttribute("error", QUANTITY_ERROR);
        }

        doGet(request, response);
    }

    private String loadProduct(HttpServletRequest request) throws NoSuchElementException{
        return  request.getPathInfo().substring(1);
    }
}