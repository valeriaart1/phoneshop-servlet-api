package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

public class CartServiceImpl implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = "cart";
    private ProductDao productDao = ProductDaoImpl.getInstance();
    private static volatile CartServiceImpl instance;

    private CartServiceImpl() {
    }

    public static CartServiceImpl getInstance() {
        if(instance == null) {
            synchronized (CartServiceImpl.class) {
                if(instance == null) {
                    instance = new CartServiceImpl();
                }
            }
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
    public String add(HttpSession session, Cart cart, Long productId, String quantity, Locale locale) {
        Product product = (Product) productDao.getProduct(productId);
        String errorOfQuantity = quantityHasError(locale, quantity, product.getStock());
        if(errorOfQuantity == null) {
            int quantityInt = Integer.parseInt(quantity);
            Optional<CartItem> optionalCartItem = cart
                    .getCartItems()
                    .stream()
                    .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId()))
                    .findFirst();
            if (optionalCartItem.isPresent()) {
                CartItem cartItem = optionalCartItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantityInt);
            } else {
                cart.getCartItems().add(new CartItem(product, quantityInt));
            }
            recalculateCart(cart);
            return null;
        }
        else {
            return errorOfQuantity;
        }
    }

    @Override
    public Map<Long, String> update(HttpSession session, Cart cart, String[] productIds, String[] quantities, Locale locale) {
        Map<Long, String> errors = new HashMap();

        for(int i = 0; i < productIds.length; i++) {
            Product product = (Product) productDao.getProduct(Long.valueOf(productIds[i]));
            String errorOfQuantity = quantityHasError(locale, quantities[i], product.getStock());
            if(errorOfQuantity == null) {
                int quantityInt = Integer.parseInt(quantities[i]);
                cart.getCartItems()
                        .stream()
                        .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId()))
                        .findAny()
                        .ifPresent(cartItem -> cartItem.setQuantity(quantityInt));
                recalculateCart(cart);
            }
            else {
                errors.put(Long.valueOf(productIds[i]), errorOfQuantity);
            }
        }
        return errors;
    }

    private String quantityHasError(Locale locale, String quantity, int stock) {
        try {
            int quantityInt = Integer.parseInt(String.valueOf(NumberFormat.getInstance(locale).parse(quantity)));
            if (quantityInt < 0) {
                return "Quantity must be positive!";
            }
            if (quantityInt > stock) {
                return "Error of stock! Max stock = " + stock;
            }
        }
        catch(ParseException exception) {
            return "Not a number";
        }
        return null;
    }

    @Override
    public void delete(Cart cart, Long productId) {
        cart.getCartItems().removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));
        recalculateCart(cart);
    }

    private void recalculateCart(Cart cart) {
        Optional<BigDecimal> totalCost = cart.getCartItems()
                .stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(new BigDecimal(cart.getTotalQuantity())))
                .reduce(BigDecimal::add);
        Optional<Integer> totalQuantity = cart.getCartItems()
                .stream()
                .map(CartItem::getQuantity)
                .reduce(Integer::compareTo);
        cart.setTotalCost(totalCost.get());
        cart.setTotalQuantity(totalQuantity.get());
    }
}