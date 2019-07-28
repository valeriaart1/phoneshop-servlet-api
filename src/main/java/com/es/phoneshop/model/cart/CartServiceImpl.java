package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public class CartServiceImpl implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = CartServiceImpl.class + ".cart";

    private static CartServiceImpl instance;

    private Cart cart;

    private CartServiceImpl() {
        cart = new Cart();
    }

    public static CartServiceImpl getInstance() {
        if(instance == null) {
            instance = new CartServiceImpl();
        }
        return instance;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        Cart result = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
        if(result == null) {
            result = new Cart();
            request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, result);
        }
        return result;
    }

    @Override
    public void add(Cart cart, Product product, Long quantity) {
        if(quantity > product.getStock()) {
            throw new OutOfStockException(product.getStock());
        }
        cart.getCartItems().add(new CartItem(product.getId(), quantity));
        recalculateCart(cart);
    }

    private void recalculateCart(Cart cart) {

    }
}
