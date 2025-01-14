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
    private ProductService productService;

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
        productService = new ProductService();
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
        assertFalse("Error of false getting product!",
                productDao.equals(productDao.getProduct(20L)));
    }

    @Test
    public void findProductsStock0() {
        assertFalse("Error of finding product with stock = 0!",
                productService.findProducts(null).contains(productWithId14Stock0));
    }

    @Test
    public void testSizeFindingProducts() {
        assertEquals("Error of size finding product!", 3,
                productService.findProducts(null).size());
    }

    @Test
    public void findProductsAssertTrue() {
        assertTrue("Error of true finding product!",
                productService.findProducts("AAA2").contains(productAAA2WithId16Price100));
    }

    @Test
    public void findProductsAssertFalse() {
        assertFalse("Error of false finding product!",
                productService.findProducts("AAA1").contains(productAAA2WithId16Price100));
    }

    @Test
    public void findProductsComplexFirstNameTrue() {
        assertTrue("Error of true finding complex first name of product!",
                productService.findProducts("AAA2 Lena").contains(productAAA2WithId16Price100));
    }

    @Test
    public void findProductsComplexFirstNameFalse() {
        assertFalse("Error of false finding complex first name of product!",
                productService.findProducts("AAA2 Lena").contains(productAAA1WithPrice2000));
    }

    @Test
    public void findProductsComplexSecondNameTrue() {
        assertTrue("Error of true finding complex second name of product!",
                productService.findProducts("Lena AAA1").contains(productAAA1WithPrice2000));
    }

    @Test
    public void findProductsComplexSecondNameFalse() {
        assertFalse("Error of false finding complex second name of product!",
                productService.findProducts("Lena AAA1").contains(productAAA2WithId16Price100));
    }

    @Test
    public void sortByParameterDescriptionAsc() {
        assertTrue("Error of sorting products by description asc!",
                productService.sortByParameter(productService.findProducts(null), "description", "asc")
                        .get(0).equals(productAAA1WithPrice2000));
    }

    @Test
    public void sortByParameterDescriptionDesc() {
        assertTrue("Error of sorting products by description desc!",
                productService.sortByParameter(productService.findProducts(null), "description", "desc")
                        .get(0).equals(productZZZWithId15Price10));
    }

    @Test
    public void sortByParameterPriceAsc() {
        assertTrue("Error of sorting products by price asc!",
                productService.sortByParameter(productService.findProducts(null), "price", "asc")
                        .get(0).equals(productZZZWithId15Price10));
    }

    @Test
    public void sortByParameterPriceDescQuery() {
        assertTrue("Error of sorting products by price desc!",
                productService.sortByParameter(productService.findProducts(null), "price", "desc")
                        .get(0).equals(productAAA1WithPrice2000));
    }

    @Test
    public void saveProductWithSameId() {
        Product testProduct = new Product();
        testProduct.setId(16L);
        testProduct.setStock(3);
        testProduct.setPrice(new BigDecimal(200));
        productDao.save(testProduct);
        assertEquals("Error of saving product with the same id!", testProduct.getPrice(),
                productDao.getProduct(16L).getPrice());
        productDao.delete(testProduct.getId());
    }

    @Test
    public void testSizeSavingProductWithSameId() {
        int size = productService.findProducts(null).size();
        Product testProduct = new Product();
        testProduct.setId(16L);
        testProduct.setStock(3);
        testProduct.setPrice(new BigDecimal(200));
        productDao.save(testProduct);
        assertEquals("Error of size saving product with the same id!", size,
                productService.findProducts(null).size());
        productDao.delete(testProduct.getId());
    }

    @Test
    public void testSizeSavingProduct() {
        int size = productService.findProducts(null).size();
        Product testProduct = new Product();
        testProduct.setId(21L);
        testProduct.setStock(3);
        testProduct.setPrice(new BigDecimal(200));
        productDao.save(testProduct);
        assertEquals("Error of size saving product!", size + 1,
                productService.findProducts(null).size());
        productDao.delete(testProduct.getId());
    }

    @Test
    public void testSavingProduct() {
        Product testProduct = new Product();
        testProduct.setId(21L);
        testProduct.setStock(3);
        testProduct.setPrice(new BigDecimal(200));
        productDao.save(testProduct);
        assertTrue("Error of saving product!", productService.findProducts(null).contains(testProduct));
        productDao.delete(testProduct.getId());
    }

    @Test
    public void deleteProduct() {
        productDao.delete(productAAA2WithId16Price100.getId());
        assertFalse("Error of deleting product!",
                productService.findProducts(null).contains(productAAA2WithId16Price100));
        productDao.save(productAAA2WithId16Price100);
    }

    @Test
    public void testSizeTrueDeletingProduct() {
        int size = productService.findProducts(null).size();
        productDao.delete(productAAA2WithId16Price100.getId());
        assertEquals("Error of size deleting product!", size - 1,
                productService.findProducts(null).size());
        productDao.save(productAAA2WithId16Price100);
    }
}
