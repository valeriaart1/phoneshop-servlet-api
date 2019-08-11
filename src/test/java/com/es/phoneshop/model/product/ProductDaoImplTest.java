package com.es.phoneshop.model.product;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class ProductDaoImplTest {
    private ProductDaoImpl productDao;
    private Currency usd;
    private Product productAAA1WithPrice2000;
    private Product productWithId14Stock0;
    private Product productZZZWithId15Price10;
    private Product productAAA2WithId16Price100;

    @Before
    public void init() throws ProductNotFoundException {
        usd = Currency.getInstance("USD");
        productDao = ProductDaoImpl.getInstance();
        productAAA1WithPrice2000 = new Product(17L, null, "AAA1", new BigDecimal(2000), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg",
                null);
        productWithId14Stock0 = new Product(14L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg",
                null);
        productZZZWithId15Price10 = new Product(15L, "samsung", "ZZZ", new BigDecimal(10), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg",
                null);
        productAAA2WithId16Price100 = new Product(16L, "simsxg75", "AAA2", new BigDecimal(100), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg",
                null);

        productDao.save(productAAA1WithPrice2000);
        productDao.save(productWithId14Stock0);
        productDao.save(productZZZWithId15Price10);
        productDao.save(productAAA2WithId16Price100);
    }

    @After
    public void destroy() {
        productDao.delete(productAAA1WithPrice2000.getId());
        productDao.delete(productWithId14Stock0.getId());
        productDao.delete(productZZZWithId15Price10.getId());
        productDao.delete(productAAA2WithId16Price100.getId());
    }

    @Test
    public void getProductAssertEquals() {
        assertEquals("Error of equals getting product!", productAAA2WithId16Price100,
                productDao.getProduct(16L));
    }

    @Test(expected = ProductNotFoundException.class)
    public void getProductAssertFalse(){
        assertFalse("Error of false getting product!", productDao.equals(productDao.getProduct(20L)));
    }

    @Test
    public void findProductsStock0() {
        assertFalse("Error of finding product with stock = 0!", productDao.findProducts(null).contains(productWithId14Stock0));
    }

    @Test
    public void testSizeFindingProducts() {
        assertEquals("Error of size finding product!", 3, productDao.findProducts(null).size());
    }

    @Test
    public void findProductsAssertTrue() {
        assertTrue("Error of true finding product!", productDao.findProducts("AAA2").contains(productAAA2WithId16Price100));
    }

    @Test
    public void findProductsAssertFalse() {
        assertFalse("Error of false finding product!", productDao.findProducts("AAA1").contains(productAAA2WithId16Price100));
    }

    @Test
    public void findProductsComplexFirstNameTrue() {
        assertTrue("Error of true finding complex first name of product!", productDao.findProducts("AAA2 Lena").contains(productAAA2WithId16Price100));
    }

    @Test
    public void findProductsComplexFirstNameFalse() {
        assertFalse("Error of false finding complex first name of product!", productDao.findProducts("AAA2 Lena").contains(productAAA1WithPrice2000));
    }

    @Test
    public void findProductsComplexSecondNameTrue() {
        assertTrue("Error of true finding complex second name of product!", productDao.findProducts("Lena AAA1").contains(productAAA1WithPrice2000));
    }

    @Test
    public void findProductsComplexSecondNameFalse() {
        assertFalse("Error of false finding complex second name of product!", productDao.findProducts("Lena AAA1").contains(productAAA2WithId16Price100));
    }
}
