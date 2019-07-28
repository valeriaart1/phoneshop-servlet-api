package com.es.phoneshop.model.viewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class ViewedServiceImpl implements ViewedService{
    private static final String VIEWED_SESSION_ATTRIBUTE = "viewedProducts";

    private static ViewedServiceImpl instance;

    private List<Product> listViewedProducts;

    private ViewedServiceImpl() {
        listViewedProducts = new ArrayList<>();
    }

    public static ViewedServiceImpl getInstance() {
        if(instance == null) {
            instance = new ViewedServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Product> getViewedProducts(HttpSession session) {
        List<Product> result = (List<Product>) session.getAttribute(VIEWED_SESSION_ATTRIBUTE);
        if(result == null) {
            result = new ArrayList<>();
            session.setAttribute(VIEWED_SESSION_ATTRIBUTE, result);
        }
        return result;
    }

    @Override
    public void addViewedProducts(List<Product> listProducts, Product product) {
        boolean flag = listProducts
                .stream()
                .anyMatch(p -> p.getId().equals(product.getId()));
        if(flag) {
            listProducts.remove(product);
        }
        else {
            if(listProducts.size() == 3){
                listProducts.remove(0);
            }
        }
        listProducts.add(product);
    }
}
