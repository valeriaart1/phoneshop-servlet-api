package com.es.phoneshop.model.viewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.ArrayDeque;
import java.util.Deque;

public class ViewedProductsServiceImpl implements ViewedProductsService {
    private static final String VIEWED_SESSION_ATTRIBUTE = "viewedProducts";

    private static ViewedProductsServiceImpl instance;

    private ViewedProductsServiceImpl() {
    }

    public static ViewedProductsServiceImpl getInstance() {
        if (instance == null) {
            instance = new ViewedProductsServiceImpl();
        }
        return instance;
    }

    @Override
    public Deque<Product> getViewedProducts(HttpSession session) {
        Deque<Product> result = (Deque<Product>) session.getAttribute(VIEWED_SESSION_ATTRIBUTE);
        if (result == null) {
            result = new ArrayDeque<>();
            session.setAttribute(VIEWED_SESSION_ATTRIBUTE, result);
        }
        return result;
    }

    @Override
    public void addViewedProducts(Deque<Product> dequeProducts, Product product) {
        boolean dequeHasProductId = dequeProducts
                .stream()
                .anyMatch(p -> p.getId().equals(product.getId()));
        if (dequeHasProductId) {
            dequeProducts.remove(product);
        } else {
            if (dequeProducts.size() == 3) {
                dequeProducts.removeLast();
            }
        }
        dequeProducts.addFirst(product);
    }
}