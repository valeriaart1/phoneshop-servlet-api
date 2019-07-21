package com.es.phoneshop.model.product;

import com.es.phoneshop.web.SampleDataListener;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao instance = new ArrayListProductDao();
    private List<Product> products= new SampleDataListener().getSampleProducts();

    private ArrayListProductDao(){}

    public static ArrayListProductDao getInstance(){
        return instance;
    }

    @Override
    public Product getProduct(Long id) {
        return products
                .stream()
                .filter(products -> products.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Product with id: " + id + " does not exist"));
    }

    @Override
    public List<Product> findProducts(String query, String sort, String order) {
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
        productsWithFilter
                .stream()
                .distinct()
                .filter(products -> products.getPrice() != null && products.getStock() > 0);
        List<Product> productsWithFilterAndSorting = new ArrayList<>();
        if(sort == "description") {
            if(order == "asc") {
                productsWithFilterAndSorting.addAll(productsWithFilter
                        .stream()
                        .sorted(Comparator.comparing(Product::getDescription))
                        .collect(Collectors.toList()));
            }
            if (order == "desc") {
                productsWithFilterAndSorting.addAll(productsWithFilter
                        .stream()
                        .sorted(Comparator.comparing(Product::getDescription).reversed())
                        .collect(Collectors.toList()));
            }
        }
        if(sort == "price") {
            if(order == "asc") {
                productsWithFilterAndSorting.addAll(productsWithFilter
                        .stream()
                        .sorted(Comparator.comparing(Product::getPrice))
                        .collect(Collectors.toList()));
            }
            if (order == "desc") {
                productsWithFilterAndSorting.addAll(productsWithFilter
                        .stream()
                        .sorted(Comparator.comparing(Product::getPrice).reversed())
                        .collect(Collectors.toList()));
            }
        }
        if(sort == null){
            productsWithFilterAndSorting = productsWithFilter;
        }
        return productsWithFilterAndSorting
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        if(products
                .stream()
                .anyMatch(p -> p.getId().equals(product.getId()))) {
            products.remove(product.getId());
        }
        products.add(product);
    }

    @Override
    public void delete(Long id) {
        products.removeIf(p -> p.getId().equals(id));
    }
}

