package com.es.phoneshop.model.product;

import java.util.Comparator;

public enum SortBy implements Comparator<Product> {
    DESCRIPTION_ASC (Comparator.comparing(Product::getDescription)),
    DESCRIPTION_DESC (Comparator.comparing(Product::getDescription).reversed()),
    PRICE_ASC (Comparator.comparing(Product::getPrice)),
    PRICE_DESC (Comparator.comparing(Product::getPrice).reversed());

    private Comparator<Product> comparator;

    SortBy(Comparator<Product> productComparator) {
        comparator = productComparator;
    }

    @Override
    public int compare(Product o1, Product o2) {
        return comparator.compare(o1, o2);
    }
}