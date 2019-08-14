package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private CartService cartService;
    @Mock
    private Cart cart;
    @InjectMocks
    private CartPageServlet cartPageServlet;

    private String[] productIds;
    private String[] quantities;
    private static final String MESSAGE_SUCCESS_UPDATING = "?message=Updated successfully";

    @Before
    public void setUp() {
    }

    @Test
    public void testDoPostSuccessfully() throws ServletException, IOException {
        productIds = new String[]{"1"};
        quantities = new String[]{"2"};
        when(request.getParameterValues("productId")).thenReturn(productIds);
        when(request.getParameterValues("quantity")).thenReturn(quantities);
        when(request.getSession()).thenReturn(session);
        when(cartService.getCart(session)).thenReturn(cart);
        when(request.getRequestURI()).thenReturn("/phoneshop-servlet-api/cart");

        cartPageServlet.doPost(request, response);

        verify(response).sendRedirect("/phoneshop-servlet-api/cart" + MESSAGE_SUCCESS_UPDATING);
    }

}
