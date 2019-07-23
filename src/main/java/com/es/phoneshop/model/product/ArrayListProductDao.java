package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao instance;
    private List<Product> products = getSampleProducts();

    private ArrayListProductDao() {
    }

    public static ArrayListProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    @Override
    public Product getProduct(Long id) {
        return products
                .stream()
                .filter(products -> products.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
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
            products.remove(getProduct(product.getId()));
        }
        products.add(product);
    }

    @Override
    public void delete(Long id) {
        products.removeIf(p -> p.getId().equals(id));
    }

    public List<Product> getSampleProducts() {
        List<Product> result = new CopyOnWriteArrayList<>();
        Currency usd = Currency.getInstance("USD");
        result.add(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg",
                history(new BigDecimal(100))));
        result.add(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg",
                history(new BigDecimal(200))));
        result.add(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg",
                history(new BigDecimal(300))));
        result.add(new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg",
                history(new BigDecimal(200))));
        result.add(new Product(5L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg",
                history(new BigDecimal(1000))));
        result.add(new Product(6L, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg",
                history(new BigDecimal(320))));
        result.add(new Product(7L, "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg",
                history(new BigDecimal(420))));
        result.add(new Product(8L, "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg",
                history(new BigDecimal(120))));
        result.add(new Product(9L, "nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg",
                history(new BigDecimal(70))));
        result.add(new Product(10L, "palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg",
                history(new BigDecimal(170))));
        result.add(new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg",
                history(new BigDecimal(70))));
        result.add(new Product(12L, "simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg",
                history(new BigDecimal(80))));
        result.add(new Product(13L, "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg",
                history(new BigDecimal(150))));
        return result;
    }

    public ArrayList<HistoryOfPrices> history(BigDecimal price) {
        Currency usd = Currency.getInstance("USD");

        ArrayList<HistoryOfPrices> arrayOfHistory = new ArrayList<>();

        BigDecimal value06 = new BigDecimal("0.6");
        BigDecimal value08 = new BigDecimal("0.8");
        BigDecimal value09 = new BigDecimal("0.9");

        arrayOfHistory.add(new HistoryOfPrices("06/09/2018", price.multiply(value06), usd));
        arrayOfHistory.add(new HistoryOfPrices("09.01.2019", price.multiply(value08), usd));
        arrayOfHistory.add(new HistoryOfPrices("22.03.2019", price.multiply(value09), usd));
        arrayOfHistory.add(new HistoryOfPrices("22.06.2019", price, usd));

        return arrayOfHistory;
    }
}
