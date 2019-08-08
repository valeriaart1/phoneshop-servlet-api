package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ProductDaoImpl implements ProductDao{
    private static ProductDaoImpl instance;
    private List<Product> products = new CopyOnWriteArrayList<>();

    protected ProductDaoImpl() {
    }

    public static ProductDaoImpl getInstance() {
        if(instance == null) {
            synchronized (ProductDaoImpl.class) {
                if(instance == null) {
                    instance = new ProductDaoImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Product> getProduct(Long id) {
        products
                .stream()
                .filter(products -> products.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product with  id: " + id + " isn't"));
        return products;
    }
    public List<Product> findProducts(String query) {
        List<Product> productsWithFilter = new ArrayList<>();
        if(query != null) {
            String[] words = query.split("\\s");
            for(String wordForSearching : words) {
                productsWithFilter.addAll(products
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

    public List<Product> sortByParameter(List<Product> products, String sort, String order){
        if(products == null) return null;
        List<Product> resultListProducts;
        if(sort != null && order != null) {
            String sortOrder = sort + " " + order;
            resultListProducts = products
                    .stream()
                    .sorted(SortBy.getSortOrder(sortOrder))
                    .collect(Collectors.toList());
        }
        else {
            resultListProducts = products;
        }
        return resultListProducts;
    }

    @Override
    public void save(Product product){
        if(product != null) {
            boolean flag = products
                    .stream()
                    .anyMatch(p -> p.getId().equals(product.getId()));
            if (flag) {
                products.remove(getProduct(product.getId()));
            }
            products.add(product);
        }
    }

    @Override
    public void delete(Long id) {
        if(id != null) {
            products.removeIf(p -> p.getId().equals(id));
        }
    }
}