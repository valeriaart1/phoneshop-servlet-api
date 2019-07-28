package com.es.phoneshop.model.product;

import java.util.*;
import java.util.stream.Collectors;

public class ProductDaoImpl implements ProductDao {
    private static ProductDaoImpl instance;
    private List<Product> products = new ArrayList<>();

    private ProductDaoImpl() {
    }

    synchronized public static ProductDaoImpl getInstance() {
        if (instance == null) {
            instance = new ProductDaoImpl();
        }
        return instance;
    }

    @Override
    public Product getProduct(Long id) {
        return products
                .stream()
                .filter(products -> products.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public List<Product> findProducts(String query) {
        List<Product> productsWithFilter = new ArrayList<>();
        if(query != null) {
            String[] words = query.split("\\s");
            for(String wordForSearching : words) {
                productsWithFilter.addAll(this.products
                        .stream()
                        .filter(products -> products.getDescription().contains(wordForSearching))
                        .collect(Collectors.toList()));
            }
        }
        else {
            productsWithFilter = products;
        }
        List<Product> filtered = new ArrayList<>();
        productsWithFilter
                .stream()
                .filter(products -> products.getPrice() != null && products.getStock() > 0)
                .forEach(products->filtered.add(products));
        return filtered;
    }

    @Override
    public List<Product> sortByParameter(List<Product> products, String sortOrder){
        return products
                .stream()
                .sorted(SortBy.getSortOrder(sortOrder))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        boolean flag = products
                .stream()
                .anyMatch(p -> p.getId().equals(product.getId()));
        if(flag) {
            products.remove(getProduct(product.getId()));
        }
        products.add(product);
    }

    @Override
    public void delete(Long id) {
        products.removeIf(p -> p.getId().equals(id));
    }
}
