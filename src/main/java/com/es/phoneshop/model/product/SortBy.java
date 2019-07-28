package com.es.phoneshop.model.product;

import java.util.Comparator;

public enum SortBy implements Comparator<Product> {
    DESCRIPTION_ASC {
        @Override
        public final int compare(final Product o1, final Product o2) {
            return o1.getDescription().compareTo(o2.getDescription());
        }
    },
    DESCRIPTION_DESC {
        @Override
        public final int compare(final Product o1, final Product o2) {
            return -o1.getDescription().compareTo(o2.getDescription());
        }
    },
    PRICE_ASC {
        @Override
        public final int compare(final Product o1, final Product o2) {
            return o1.getPrice().compareTo(o2.getPrice());
        }
    },
    PRICE_DESC {
        @Override
        public final int compare(final Product o1, final Product o2) {
            return -o1.getPrice().compareTo(o2.getPrice());
        }
    };

    public static SortBy getSortOrder(String sortOrder) {
        if (sortOrder.contains("description")) {
            return sortOrder.contains("asc") ? DESCRIPTION_ASC : DESCRIPTION_DESC;
        } else {
            return sortOrder.contains("asc") ? PRICE_ASC : PRICE_DESC;
        }
    }
}