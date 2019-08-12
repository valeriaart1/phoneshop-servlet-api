package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private static ProductService instance = new ProductService();
    private ProductDaoImpl productDao = ProductDaoImpl.getInstance();

    public static ProductService getInstance() {
        return instance;
    }

    public List<Product> findProducts(String query) {
        List<Product> productsWithFilter = new ArrayList<>();
        if (query != null) {
            String[] words = query.split("\\s");
            for (String wordForSearching : words) {
                productsWithFilter.addAll(productDao.findProducts()
                        .stream()
                        .filter(products -> products.getDescription().contains(wordForSearching))
                        .collect(Collectors.toList()));
            }
        } else {
            productsWithFilter = productDao.findProducts();
        }
        return productsWithFilter;
    }

    public List<Product> sortByParameter(List<Product> products, String sort, String order) {
        if (products == null) return null;
        List<Product> resultListProducts;
        if (sort != null && order != null) {
            String sortOrder = sort + " " + order;
            resultListProducts = products
                    .stream()
                    .sorted(SortBy.getSortOrder(sortOrder))
                    .collect(Collectors.toList());
        } else {
            resultListProducts = products;
        }
        return resultListProducts;
    }
}
