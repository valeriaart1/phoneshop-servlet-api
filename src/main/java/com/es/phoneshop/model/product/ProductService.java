package com.es.phoneshop.model.product;

import java.math.BigDecimal;
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

    public List<Product> findProductsAdvancedSearch(String description, String minPrice, String maxPrice,
                                               String minStock, String maxStock) {
        if(minPrice.isEmpty()) minPrice = "0";
        if(maxPrice.isEmpty()) maxPrice = sortByParameter(findProducts(null), "price", "desc").get(0).getPrice().toString();
        BigDecimal finalMinPrice = new BigDecimal(minPrice);
        BigDecimal finalMaxPrice = new BigDecimal(maxPrice);

        if(minStock.isEmpty()) minStock = "1";
        if(maxStock.isEmpty()) maxStock = String.valueOf(sortByParameter(findProducts(null), "stock", "desc").get(0).getStock());
        int finalMinStock = Integer.parseInt(minStock);
        int finalMaxStock = Integer.parseInt(maxStock);

        return findProducts(description)
                .stream()
                .filter(product -> product.getPrice().compareTo(finalMinPrice) >= 0 && product.getPrice().compareTo(finalMaxPrice) <= 0)
                 .filter(product -> product.getStock() >= finalMinStock && product.getStock() <= finalMaxStock)
                .collect(Collectors.toList());

    }
}
