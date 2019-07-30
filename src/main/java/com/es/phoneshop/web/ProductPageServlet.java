package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDaoImpl;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;
import com.es.phoneshop.model.viewed.ViewedProductsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

public class ProductPageServlet extends HttpServlet {
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
        try {
            List<Product> listViewedProducts = viewedProducts.getViewedProducts(request.getSession());
            Product product = loadProduct(request);
            Cart cart = cartService.getCart(request);
            request.setAttribute("cart", cart);
            request.setAttribute("viewedProducts", listViewedProducts);
            request.setAttribute("product", product);
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp")
                    .forward(request, response);
            viewedProducts.addViewedProducts(viewedProducts.getViewedProducts(request.getSession()), product);
        }
        catch(NumberFormatException | ProductNotFoundException e) {
            request.getRequestDispatcher("/WEB-INF/pages/404.jsp")
                    .forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Cart cart = cartService.getCart(request);

        try {
            Product product = loadProduct(request);
            Locale locale = request.getLocale();
            Long quantity = Long.valueOf(String.valueOf(NumberFormat.getInstance(locale).parse(request.getParameter("quantity"))));

            cartService.add(cart, product, quantity);

            response.sendRedirect(request.getContextPath() + request.getServletPath() +
                    request.getPathInfo() + "?message=Added to cart successfully");
            return;
        }
        catch(ParseException exception) {
            request.setAttribute("error", "Not a number");
        }
        catch(OutOfStockException exception) {
                request.setAttribute("error", "Out of stock. Max stock is " + exception.getMaxStock());
        }

        doGet(request, response);
    }
    private Product loadProduct(HttpServletRequest request) throws NoSuchElementException {
        String idString = request.getRequestURI().substring((request.getContextPath() + request.getServletPath()).length() + 1);
        Long id = Long.parseLong(idString);
        return productDao.getProduct(id);
    }
}