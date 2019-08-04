package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

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
    public Cart getCart(HttpSession session) {
        Cart result = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);
        if(result == null) {
            result = new Cart();
            session.setAttribute(CART_SESSION_ATTRIBUTE, result);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartServiceImpl that = (CartServiceImpl) o;
        return Objects.equals(productDao, that.productDao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productDao);
    }

    @Override
    public String toString() {
        return "CartServiceImpl{" +
                "productDao=" + productDao +
                '}';
    }

    @Override
    public String add(HttpServletRequest request, Cart cart, Long productId, String quantity) {
        return checkInputQuantity("add", request, cart, productId, quantity);
    }

    @Override
    public String update(HttpServletRequest request, Cart cart, Long productId, String quantity) {
        return checkInputQuantity("update", request, cart, productId, quantity);
    }

    @Override
    public void delete(Cart cart, Product product) {
        Optional<CartItem> optionalCartItem  = optionalCartItem(cart, product);
        cart.setTotalQuantity(cart.getTotalQuantity() - optionalCartItem.get().getQuantity());
        BigDecimal oldProductPrice = product.getPrice().multiply(new BigDecimal(optionalCartItem.get().getQuantity()));
        cart.setTotalCost(cart.getTotalCost().subtract(oldProductPrice));
        cart.getCartItems().removeIf(cartItem -> product.equals(cartItem.getProduct()));
    }

    private String checkInputQuantity(String typeOfChanging, HttpServletRequest request, Cart cart, Long productId, String quantity){
        Product product = productDao.getProduct(productId).get();
        if(!quantity.matches("[\\-]*[1-9]+[\\.0*]*")){
            return "Not a number!";
        }
        try {
            Locale locale = request.getLocale();
            int quantityInt = Integer.parseInt(String.valueOf(NumberFormat.getInstance(locale).parse(quantity)));
            if(quantityInt < 0){
                return "Quantity must be positive!";
            }
            if(quantityInt > product.getStock()){
                throw new OutOfStockException();
            }
            changingCart(typeOfChanging, quantityInt, product, cart);
        } catch (ParseException e) {
            return "Not a number!";
        }
        catch (OutOfStockException exception) {
            return "Error of stock! Max stock = " + exception.getMaxStock(product.getStock());
        }
        return null;
    }

    private void changingCart(String typeOfChanging, int quantityInt, Product product,
                   Cart cart) {

        Optional<CartItem> optionalCartItem  = optionalCartItem(cart, product);

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            switch (typeOfChanging) {
                case "add": {
                    cartItem.setQuantity(cartItem.getQuantity() + quantityInt);
                    recalculateCart(cart, product, quantityInt);
                }
                break;
                case "update": {
                    cart.setTotalQuantity(cart.getTotalQuantity() - cartItem.getQuantity());
                    BigDecimal oldProductPrice = cartItem.getProduct().getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
                    cart.setTotalCost(cart.getTotalCost().subtract(oldProductPrice));
                    cartItem.setQuantity(quantityInt);
                    BigDecimal productPrice = product.getPrice().multiply(new BigDecimal(quantityInt));
                    cart.setTotalCost(cart.getTotalCost().add(productPrice));
                    cart.setTotalQuantity(cart.getTotalQuantity() + quantityInt);
                }
            }
        } else {
            cart.getCartItems().add(new CartItem(product, quantityInt));
            recalculateCart(cart, product, quantityInt);
        }
    }



    private Optional<CartItem> optionalCartItem(Cart cart, Product product) {
        Optional<CartItem> optionalCartItem = cart
                .getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId()))
                .findFirst();
        return optionalCartItem;
    }

    private void recalculateCart(Cart cart, Product product, int quantity) {
        BigDecimal productPrice = product.getPrice().multiply(new BigDecimal(quantity));
        cart.setTotalCost(cart.getTotalCost().add(productPrice));
        cart.setTotalQuantity(cart.getTotalQuantity() + quantity);
    }
}