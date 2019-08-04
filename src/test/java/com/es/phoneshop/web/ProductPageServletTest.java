package com.es.phoneshop.web;

<<<<<<< HEAD
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
=======
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
>>>>>>> Valeria3
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
<<<<<<< HEAD

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
=======
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

>>>>>>> Valeria3
@RunWith(MockitoJUnitRunner.class)
public class ProductPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
<<<<<<< HEAD
    Product product;
    @Mock
    private ProductDao testProduct;
    private ProductPageServlet servlet = new ProductPageServlet();

    @Before
    public void setup() {
        when(product.getId()).thenReturn(1L);
    }

    @Test(expected = NumberFormatException.class)
    public void testDoGetBadPath() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("null");
        servlet.doGet(request, response);

        verify(request).getRequestDispatcher("/WEB-INF/pages/product.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test(expected = NullPointerException.class)
    public void testDoGet() throws ServletException, IOException {
        testProduct = ArrayListProductDao.getInstance();

        testProduct.save(product);

        when(request.getPathInfo()).thenReturn("/3");
        servlet.doGet(request, response);

        verify(request).getRequestDispatcher("/WEB-INF/pages/product.jsp");
        verify(requestDispatcher).forward(request, response);
    }

}
=======
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
    @Mock
    private ProductNotFoundException exception;
    @InjectMocks
    private ProductPageServlet servlet;

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getPathInfo()).thenReturn("/1");
        when(cartService.getCart(request)).thenReturn(cart);
        when(viewedProducts.getViewedProducts(request.getSession())).thenReturn(dequeViewedProducts);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(productDao.getProduct(1L)).thenReturn(Optional.of(product));
        cart = cartService.getCart(request);
        servlet.doGet(request, response);
        verify(request).setAttribute("cart", cart);
        verify(request).setAttribute("product", product);
        verify(request).setAttribute("viewedProducts", dequeViewedProducts);
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/pages/product.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test(expected = NoSuchElementException.class)
    public void testDoGetProductNotFound() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).setAttribute("idProductNotFound", exception.getProductIdNotFound(product.getId()));
        verify(request).getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.doPost(request, response);
    }

    @Test(expected = NoSuchElementException.class)
    public void loadProduct() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).getPathInfo().substring(1);
    }
}
>>>>>>> Valeria3
