package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.Null;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest
{
    private ArrayListProductDao productDao;
    private Currency usd;
    private Product productAAAWithPrice2000;
    private Product productWithId14Stock0;
    private Product productZZZWithId15Price10;
    private Product productAAAWithId16Price100;

    @Before
    public void setup() {
        usd = Currency.getInstance("USD");
        productDao = ArrayListProductDao.getInstance();
        productAAAWithPrice2000 = new Product(17L, null, "AAA1", new BigDecimal(2000), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg",
                productDao.history(new BigDecimal(200)));
        productWithId14Stock0 = new Product(14L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg",
                productDao.history(new BigDecimal(200)));
        productZZZWithId15Price10 = new Product(15L, "samsung", "ZZZ", new BigDecimal(10), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg",
            productDao.history(new BigDecimal(100)));
        productAAAWithId16Price100 = new Product(16L, "simsxg75", "AAA2", new BigDecimal(100), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg",
                productDao.history(new BigDecimal(200)));
    }

    @Test
    public void testSizeSavingWithSameId() {
        int size = productDao.findProducts(null, null, null).size();
        Product testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setStock(200);
        productDao.save(testProduct);
        assertEquals("Error of size in saving object with the same id!", size, productDao.findProducts(null, null, null).size());
    }

    @Test
    public void testSaveWithSameId() {
        Product testProduct = new Product();
        testProduct.setId(5L);
        testProduct.setStock(300);
        productDao.save(testProduct);
        assertEquals("Error of saving object with the same id!", 300, productDao.getProduct(5L).getStock());
    }

    @Test
    public void testSizeSavingProduct() {
        int size = productDao.findProducts(null, null, null).size();
        productDao.save(productAAAWithId16Price100);
        assertEquals("Error of size in saving object with the same id!", size + 1, productDao.findProducts(null, null, null).size());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testSaveProduct() {
        assertEquals("Error of saving object!", productAAAWithId16Price100.getId(),
                productDao.getProduct(productAAAWithId16Price100.getId()).getId());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteProduct() {
        productDao.delete(2L);
        assertFalse("Error of deleting object!", productDao.equals(productDao.getProduct(2L)));
    }

    @Test
    public void testFindProducts() {
        productDao.save(productWithId14Stock0);
        assertFalse("Error of finding object!",
                productDao.findProducts(null, null, null).equals(productWithId14Stock0));
    }

    @Test(expected = NullPointerException.class)
    public void testTrueFindProductsWithFilter() {
        productDao.save(productAAAWithPrice2000);
        assertTrue("Error of true finding object with filter!",
                productDao.findProducts("AAA", null, null).contains(productAAAWithPrice2000));
    }

    @Test(expected = NullPointerException.class)
    public void testFalseFindProductsWithFilter() {
        assertFalse("Error of false finding object with filter!",
                productDao.findProducts("FFF", null, null).contains(productAAAWithPrice2000));
    }

    @Test(expected = NullPointerException.class)
    public void testFindFirstProductWithComplexFilter() {
        assertTrue("Error of finding first object with complex filter!",
                productDao.findProducts("AAA GGG", null, null).contains(productAAAWithPrice2000));
    }

    @Test(expected = NullPointerException.class)
    public void testFindSecondProductWithComplexFilter() {
        assertTrue("Error of finding second object with complex filter!",
                productDao.findProducts("GGG AAA", null, null).contains(productAAAWithPrice2000));
    }

    @Test(expected = NullPointerException.class)
    public void testFindProductsWithDescriptionAsc() {
        assertTrue("Error of finding object with description asc!",
                productDao.findProducts(null, "description", "asc").get(0).equals(productAAAWithPrice2000));
    }

    @Test
    public void testFindProductsWithDescriptionDesc() {
        productDao.save(productZZZWithId15Price10);
        assertTrue("Error of finding object with description desc!",
                productDao.findProducts(null, "description", "desc").get(0).equals(productZZZWithId15Price10));
    }

    @Test(expected = NullPointerException.class)
    public void testFindProductsWithPriceAsc() {
        productDao.save(productZZZWithId15Price10);
        assertTrue("Error of finding object with price asc!",
                productDao.findProducts(null, "price", "asc").get(0).equals(productZZZWithId15Price10));
    }

    @Test(expected = NullPointerException.class)
    public void testFindProductsWithPriceDesc() {
        assertTrue("Error of finding object with price desc!",
                productDao.findProducts(null, "price", "desc").get(1).equals(productZZZWithId15Price10));
    }

    @Test(expected = NullPointerException.class)
    public void testFindProductsWithFilterAndDescriptionAsc() {
        assertTrue("Error of finding object with filter and description asc!",
                productDao.findProducts("AAA", "description", "asc").get(0).equals(productAAAWithPrice2000));
    }

    @Test(expected = NullPointerException.class)
    public void testFindProductsWithFilterAndDescriptionDesc() {
        assertTrue("Error of finding object with filter and description desc!",
                productDao.findProducts("AAA", "description", "desc").get(1).equals(productAAAWithPrice2000));
    }

    @Test(expected = NullPointerException.class)
    public void testFindProductsWithFilterAndPriceAsc() {
        assertTrue("Error of finding object with filter and price asc!",
                productDao.findProducts("AAA", "price", "asc").get(0).equals(productAAAWithId16Price100));
    }

    @Test(expected = NullPointerException.class)
    public void testFindProductsWithFilterAndPriceDesc() {
        assertTrue("Error of finding object with filter and price desc!",
                productDao.findProducts("AAA", "price", "desc").get(0).equals(productAAAWithPrice2000));
    }
}


