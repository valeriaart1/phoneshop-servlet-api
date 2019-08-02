package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductDaoImpl;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public class CartServiceImpl implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = CartServiceImpl.class + ".cart";
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
    public String add(HttpServletRequest request, Cart cart, Long productId) {
        Product product = productDao.getProduct(productId).get();
        if(!request.getParameter("quantity").matches("[\\-]*[1-9]+[\\.0*]*")){
            return "Not a number!";
        }
        try {
            Locale locale = request.getLocale();
            String parameterQuantity = request.getParameter("quantity");
            int quantity = Integer.parseInt(String.valueOf(NumberFormat.getInstance(locale).parse(parameterQuantity)));
            if(quantity < 0){
                return "Quantity must be positive!";
            }
            if(quantity > product.getStock()){
                throw new OutOfStockException();
            }
            Optional<CartItem> optionalCartItem = cart
                    .getCartItems()
                    .stream()
                    .filter(cartItem -> cartItem.getProductId().equals(productId))
                    .findFirst();
            if (optionalCartItem.isPresent()) {
                CartItem cartItem = optionalCartItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            } else {
                cart.getCartItems().add(new CartItem(productId, quantity));
            }
            recalculateCart(cart, product, quantity);
        } catch (ParseException e) {
            return "Not a number!";
        }
        catch (OutOfStockException exception) {
            return "Error of stock! Max stock = " + exception.getMaxStock(product.getStock());
        }
        return null;
    }

    private void recalculateCart(Cart cart, Product product, int quantity) {
        BigDecimal productPrice = product.getPrice().multiply(new BigDecimal(quantity));
        cart.setTotalCost(cart.getTotalCost().add(productPrice));
        cart.setTotalQuantity(cart.getTotalQuantity() + quantity);
        product.setStock(product.getStock() - Integer.parseInt(String.valueOf(quantity)));
    }
}