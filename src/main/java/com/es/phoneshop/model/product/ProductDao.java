package com.es.phoneshop.model.product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Optional<Product> getProduct(Long id);

    List<Product> findProducts(String query);

    void save(Product product) throws ProductNotFoundException;

    void delete(Long id);

    List<Product> sortByParameter(List<Product> products, String sort, String order);
}
