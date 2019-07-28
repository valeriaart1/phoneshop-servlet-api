package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ProductDaoImpl;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import com.es.phoneshop.model.viewed.ViewedServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    Product product;
    @Mock
    ViewedServiceImpl viewedService;
    @Mock
    private ProductDao testProduct;
    private ProductPageServlet servlet = new ProductPageServlet();

    @Before
    public void setup() {
        when(product.getId()).thenReturn(1L);
    }

    @Test(expected = NullPointerException.class)
    public void testDoGetNullPointer() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("null");
        servlet.doGet(request, response);

        verify(request).getRequestDispatcher("/WEB-INF/pages/product.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test(expected = NullPointerException.class)
    public void testDoGet() throws ServletException, IOException {
        testProduct = ProductDaoImpl.getInstance();

        testProduct.save(product);

        when(request.getPathInfo()).thenReturn("/3");
        servlet.doGet(request, response);

        verify(request).getRequestDispatcher("/WEB-INF/pages/product.jsp");
        verify(requestDispatcher).forward(request, response);
    }

}