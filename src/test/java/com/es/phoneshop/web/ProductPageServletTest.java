package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;
import com.es.phoneshop.model.viewed.ViewedProductsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductDao productDao;
    @Mock
    private Product product;
    @Mock
    private ViewedProductsServiceImpl viewedProducts;
    @Mock
    private Deque<Product> dequeViewedProducts;
    @Mock
    private CartServiceImpl cartService;
    @Mock
    private Cart cart;
    @InjectMocks
    private ProductPageServlet servlet;

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getPathInfo()).thenReturn("/1");
        when(cartService.getCart(request.getSession())).thenReturn(cart);
        when(viewedProducts.getViewedProducts(request.getSession())).thenReturn(dequeViewedProducts);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(productDao.getProduct(1L)).thenReturn(product);
        cart = cartService.getCart(request.getSession());
        servlet.doGet(request, response);
        verify(request).setAttribute("cart", cart);
        verify(request).setAttribute("product", product);
        verify(request).setAttribute("viewedProducts", dequeViewedProducts);
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/pages/product.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.doPost(request, response);
    }
}
