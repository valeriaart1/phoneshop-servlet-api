package com.es.phoneshop.model.product;

import java.util.Comparator;

public enum SortBy implements Comparator<Product> {
    DESCRIPTION_ASC(Comparator.comparing(Product::getDescription), "description asc"),
    DESCRIPTION_DESC(Comparator.comparing(Product::getDescription).reversed(), "description desc"),
    PRICE_ASC(Comparator.comparing(Product::getPrice), "price asc"),
    PRICE_DESC(Comparator.comparing(Product::getPrice).reversed(), "price desc");

    private Comparator<Product> comparator;
    private String sortOrder;

    SortBy(Comparator<Product> productComparator, String sortOrder) {
        comparator = productComparator;
        this.sortOrder = sortOrder;
    }

    public Comparator<Product> getComparator() {
        return comparator;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    @Override
    public int compare(Product o1, Product o2) {
        return comparator.compare(o1, o2);
    }

    public static Comparator<Product> getSortOrder(String sortOrder) {
        for (SortBy comparator : values()) {
            if (comparator.getSortOrder().equals(sortOrder)) {
                return comparator.getComparator();
            }
        }
        return null;
    }
}