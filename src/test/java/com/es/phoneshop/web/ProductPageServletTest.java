package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
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