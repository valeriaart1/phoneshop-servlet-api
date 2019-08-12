package com.es.phoneshop.model.product;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ProductDaoImpl implements ProductDao {
    private static ProductDaoImpl instance;
    private List<Product> products = new CopyOnWriteArrayList<>();

    protected ProductDaoImpl() {
    }

    public static ProductDaoImpl getInstance() {
        if (instance == null) {
            synchronized (ProductDaoImpl.class) {
                if (instance == null) {
                    instance = new ProductDaoImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Product getProduct(Long id) {
        Product product = products
                .stream()
                .filter(products -> products.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product with  id: " + id + " isn't"));
        return product;
    }

    @Override
    public List<Product> findProducts() {
        return products.stream()
                .filter(product -> (product.getPrice() != null && product.getStock() > 0))
                .collect(Collectors.toList());

    }

    @Override
    public void save(Product product) {
        if (product != null) {
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
        if (id != null) {
            products.removeIf(p -> p.getId().equals(id));
        }
    }
}