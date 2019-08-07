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
    private Product productAAAWithPrice2000;
    private Product productWithId14Stock0;
    private Product productZZZWithId15Price10;
    private Product productAAAWithId16Price100;

    @Before
    public void init() throws ProductNotFoundException {
        usd = Currency.getInstance("USD");
        productDao = ProductDaoImpl.getInstance();
        productAAAWithPrice2000 = new Product(17L, null, "AAA1", new BigDecimal(2000), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg",
                null);
        productWithId14Stock0 = new Product(14L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg",
                null);
        productZZZWithId15Price10 = new Product(15L, "samsung", "ZZZ", new BigDecimal(10), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg",
                null);
        productAAAWithId16Price100 = new Product(16L, "simsxg75", "AAA2", new BigDecimal(100), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg",
                null);

        productDao.save(productAAAWithPrice2000);
        productDao.save(productWithId14Stock0);
        productDao.save(productZZZWithId15Price10);
        productDao.save(productAAAWithId16Price100);
    }

    @After
    public void destroy() {
        productDao.delete(productAAAWithPrice2000.getId());
        productDao.delete(productWithId14Stock0.getId());
        productDao.delete(productZZZWithId15Price10.getId());
        productDao.delete(productAAAWithId16Price100.getId());
    }

    @Test
    public void testSaveWithSameId(){
        Product testProduct = new Product();
        testProduct.setId(16L);
        testProduct.setStock(300);
        testProduct.setDescription("Lera");
        productDao.save(testProduct);
        assertFalse("Error of saving object with the same id!", productDao.findProducts("Lera").isEmpty());
    }

    @Test
    public void testSizeSavingProduct(){
        int size = productDao.findProducts(null).size();
        Product testProduct = new Product();
        testProduct.setId(20L);
        testProduct.setPrice(new BigDecimal(100));
        testProduct.setStock(1);
        productDao.save(testProduct);
        assertEquals("Error of size in saving object with the same id!", size + 1, productDao.findProducts(null).size());
    }

    @Test
    public void testSaveProduct(){
        Product testProduct = new Product();
        testProduct.setId(21L);
        testProduct.setDescription("Lera");
        testProduct.setPrice(new BigDecimal(100));
        testProduct.setStock(1);
        productDao.save(testProduct);
        assertTrue("Error of saving object!", productDao.findProducts("Lera").get(0).equals(testProduct));
    }

    @Test
    public void testSizeDeletingProduct(){
        int size = productDao.findProducts("AAA").size();
        productDao.delete(16L);
        assertEquals("Error in size of deleting object!", size - 1,
                productDao.findProducts("AAA").size());
        productDao.save(productAAAWithId16Price100);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteProduct(){
        productDao.delete(16L);
        assertFalse("Error of deleting object!", productDao.equals(productDao.getProduct(16L)));
        productDao.save(productAAAWithId16Price100);
    }

    @Test
    public void testFindProducts() {
        assertFalse("Error of finding object!",
                productDao.findProducts(null).equals(productWithId14Stock0));
    }

    @Test
    public void testFalseFindProductsWithFilter() {
        assertFalse("Error of false finding object with filter!",
                productDao.findProducts("ZZZ").get(0).equals(productAAAWithPrice2000));
    }

    @Test
    public void testFindFirstProductWithComplexFilter() {
        assertTrue("Error of finding first object with complex filter!",
                productDao.findProducts("AAA1 TTT").get(0).equals(productAAAWithPrice2000));
    }

    @Test
    public void testFindSecondProductWithComplexFilter() {
        assertTrue("Error of finding second object with complex filter!",
                productDao.findProducts("TTT AAA1").get(0).equals(productAAAWithPrice2000));
    }

    @Test
    public void testFindProductsWithDescriptionAsc() {
        assertTrue("Error of finding object with description asc!",
                productDao.sortByParameter(productDao.findProducts(null), "description", "asc")
                        .contains(productAAAWithPrice2000));
    }

    @Test
    public void testFindProductsWithDescriptionDesc() {
        assertTrue("Error of finding object with description desc!",
                productDao.sortByParameter(productDao.findProducts(null), "description", "desc")
                        .get(0).equals(productZZZWithId15Price10));
    }

    @Test
    public void testFindProductsWithPriceAsc() {
        assertTrue("Error of finding object with price asc!",
                productDao.sortByParameter(productDao.findProducts(null), "price", "asc")
                        .get(0).equals(productZZZWithId15Price10));
    }

    @Test
    public void testFindProductsWithPriceDesc() {
        assertTrue("Error of finding object with price desc!",
                productDao.sortByParameter(productDao.findProducts(null), "price", "desc")
                        .get(0).equals(productAAAWithPrice2000));
    }

    @Test
    public void testFindProductsWithFilterAndDescriptionAsc() {
        assertTrue("Error of finding object with filter and description asc!",
                productDao.sortByParameter(productDao.findProducts("AAA"), "description", "asc")
                        .get(0).equals(productAAAWithPrice2000));
    }

    @Test
    public void testFindProductsWithFilterAndDescriptionDesc() {
        assertTrue("Error of finding object with filter and description desc!",
                productDao.sortByParameter(productDao.findProducts("AAA"), "description", "desc")
                        .get(1).equals(productAAAWithPrice2000));
    }

    @Test
    public void testFindProductsWithFilterAndPriceAsc() {
        assertTrue("Error of finding object with filter and price asc!",
                productDao.sortByParameter(productDao.findProducts("AAA"), "price", "asc")
                        .get(1).equals(productAAAWithPrice2000));
    }

    @Test
    public void testFindProductsWithFilterAndPriceDesc() {
        assertTrue("Error of finding object with filter and description desc!",
                productDao.sortByParameter(productDao.findProducts("AAA"), "price", "desc")
                        .get(0).equals(productAAAWithPrice2000));
    }
}
