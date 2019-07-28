package com.es.phoneshop.model.viewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ViewedService {
    List<Product> getViewedProducts(HttpSession session);

    void addViewedProducts(List<Product> listProducts, Product product);
}
