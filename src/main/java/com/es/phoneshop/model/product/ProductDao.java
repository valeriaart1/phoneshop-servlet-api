package com.es.phoneshop.model.product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);

    List<Product> findProducts();

    void save(Product product) throws ProductNotFoundException;

    void delete(Long id);
}
