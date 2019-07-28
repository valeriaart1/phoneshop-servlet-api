package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductDaoImpl;
import com.es.phoneshop.model.product.PriceHistory;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SampleDataListener implements ServletContextListener{

    private static final Currency USD = Currency.getInstance("USD");

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String insertSampleData = servletContextEvent.getServletContext().getInitParameter("insertSampleData");
        if("true".equals(insertSampleData)) {
            ProductDao productDao = ProductDaoImpl.getInstance();
            getSampleProducts().forEach(productDao::save);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    public List<Product> getSampleProducts() {
        List<Product> result = new ArrayList<>();
        result.add(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), USD, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg",
                history(new BigDecimal(100))));
        result.add(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), USD, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg",
                history(new BigDecimal(200))));
        result.add(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), USD, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg",
                history(new BigDecimal(300))));
        result.add(new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), USD, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg",
                history(new BigDecimal(200))));
        result.add(new Product(5L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), USD, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg",
                history(new BigDecimal(1000))));
        result.add(new Product(6L, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), USD, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg",
                history(new BigDecimal(320))));
        result.add(new Product(7L, "sec901", "Sony Ericsson C901", new BigDecimal(420), USD, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg",
                history(new BigDecimal(420))));
        result.add(new Product(8L, "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), USD, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg",
                history(new BigDecimal(120))));
        result.add(new Product(9L, "nokia3310", "Nokia 3310", new BigDecimal(70), USD, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg",
                history(new BigDecimal(70))));
        result.add(new Product(10L, "palmp", "Palm Pixi", new BigDecimal(170), USD, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg",
                history(new BigDecimal(170))));
        result.add(new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), USD, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg",
                history(new BigDecimal(70))));
        result.add(new Product(12L, "simc61", "Siemens C61", new BigDecimal(80), USD, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg",
                history(new BigDecimal(80))));
        result.add(new Product(13L, "simsxg75", "Siemens SXG75", new BigDecimal(150), USD, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg",
                history(new BigDecimal(150))));
        return result;
    }

    private ArrayList<PriceHistory> history(BigDecimal price) {
        ArrayList<PriceHistory> historyArray = new ArrayList<>();

        BigDecimal value06 = new BigDecimal("0.6");
        BigDecimal value08 = new BigDecimal("0.8");
        BigDecimal value09 = new BigDecimal("0.9");

        BigDecimal price1 = price.multiply(value06);
        BigDecimal price2 = price.multiply(value08);
        BigDecimal price3 = price.multiply(value09);

        LocalDate date1 = LocalDate.of(2018, 9, 6);
        LocalDate date2 = LocalDate.of(2019, 1,9);
        LocalDate date3 = LocalDate.of(2019, 3, 22);
        LocalDate date4 = LocalDate.of(2019, 6,30);

        historyArray.add(new PriceHistory(date1, price1, USD));
        historyArray.add(new PriceHistory(date2, price2, USD));
        historyArray.add(new PriceHistory(date3, price3, USD));
        historyArray.add(new PriceHistory(date4, price, USD));

        return historyArray;
    }
}
