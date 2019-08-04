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
            Optional<Product> optionalProduct = productDao.getProduct(productId);
            Deque<Product> dequeViewedProducts = viewedProducts.getViewedProducts(request.getSession());
            Cart cart = cartService.getCart(request.getSession());
            request.setAttribute("cart", cart);
            request.setAttribute("viewedProducts", dequeViewedProducts);
            request.setAttribute("product", optionalProduct.get());
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp")
                    .forward(request, response);
            viewedProducts.addViewedProducts(viewedProducts.getViewedProducts(request.getSession()), optionalProduct.get());
        }
        catch(ProductNotFoundException exception) {
            response.setStatus(404);
            request.setAttribute("idProductNotFound", exception.getProductIdNotFound(productId));
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Cart cart = cartService.getCart(request.getSession());
        Long productId = Long.valueOf(loadProduct(request));
        String quantity = request.getParameter("quantity");

        String QUANTITY_ERROR = cartService.add(request, cart, productId, quantity);
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