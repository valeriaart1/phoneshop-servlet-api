package com.es.phoneshop.model.viewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.Deque;

public interface ViewedProductsService {
    Deque<Product> getViewedProducts(HttpSession session);

    void addViewedProducts(Deque<Product> dequeProducts, Product product);
}