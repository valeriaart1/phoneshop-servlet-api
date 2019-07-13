package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    private Currency usd;
    private Product productOld;
    private Product productSave;

    @Before
    public void setup() {
        usd = Currency.getInstance("USD");
        productDao = new ArrayListProductDao();
        productOld = new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg");
        productSave = new Product(14L, "samsung", "Samsung Galaxy A5 2017", new BigDecimal(100), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg");
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue("No result!", productDao.findProducts().isEmpty());
    }

    @Test
    public void testSaveProduct() {
        int sizeFirst = productDao.findProducts().size();
        productDao.save(productSave);
        assertEquals("Error of saving object!", sizeFirst + 1, productDao.findProducts().size());
    }

    @Test
    public void testDeleteProduct() {
        int sizeFirst = productDao.findProducts().size();
        productDao.delete(productOld.getId());
        assertEquals("Error of deleting object!", sizeFirst - 1, productDao.findProducts().size());
    }

    @Test
    public void testFindProduct() {
        int sizeFirst = productDao.findProducts().size();
        Product productFirst = new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg");
        Product productSecond = new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg");
        productDao.save(productFirst);
        productDao.save(productSecond);
        assertEquals("Error of finding object!", sizeFirst + 1, productDao.findProducts().size());
    }
}
