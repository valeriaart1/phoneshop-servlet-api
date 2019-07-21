ackage com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest
{
    private ArrayListProductDao productDao;
    private Currency usd;
    private Product productWithId14;
    private Product productWithStock0;

    @Before
    public void setup() {
        usd = Currency.getInstance("USD");
        productDao = ArrayListProductDao.getInstance();
        productWithStock0 = new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg");
        productWithId14 = new Product(14L, "samsung", "Samsung Galaxy A5 2017", new BigDecimal(100), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg");
    }

    @Test
    public void testGetProduct() {
        productDao.save(productWithStock0);
        assertEquals("Error of saving object!", productWithStock0.getId(),
                productDao.getProduct(productWithStock0.getId()).getId());
    }

    @Test
    public void testSaveWithSameId() {
        int size = productDao.findProducts(null, null, null).size();
        Product testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setStock(200);
        assertEquals("Error of size in saving object with the same id!", size, productDao.findProducts(null, null, null).size());
        assertEquals("Error of updating in saving object with the same id!", 100, productDao.getProduct(1L).getStock());
    }

    @Test
    public void testSaveProduct() {
        int size = productDao.findProducts(null, null, null).size();
        productDao.save(productWithId14);
        assertEquals("Error of size in saving object with the same id!", size + 1, productDao.findProducts(null, null, null).size());
        assertEquals("Error of saving object!", productWithId14.getId(),
               productDao.getProduct(productWithId14.getId()).getId());
    }

    @Test
    public void testDeleteProduct() {
        int size = productDao.findProducts(null, null, null).size();
        productDao.delete(3L);
        assertEquals("Error of deleting object!", size - 1, productDao.findProducts(null, null, null).size());
        try{
            assertEquals("Error of deleting object!", productDao.getProduct(3L));
        } catch(NullPointerException e) { }
    }

    @Test
    public void testFindProducts() {
        productDao.save(productWithStock0);
        assertFalse("Error of finding object!",
                productDao.findProducts(null, null, null).equals(productWithStock0));
    }

    @Test
    public void testFindProductsWithFilter() {
        Product testProductLera = new Product(15L, "simsxg75", "Lera", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        Product testProductVladimir = new Product(16L, "simsxg75", "Vladimir", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        productDao.save(testProductLera);
        productDao.save(testProductVladimir);
        assertTrue("Error of finding object with filter!",
                productDao.findProducts("Lera", null, null).contains(testProductLera));
        assertFalse("Error of finding object with filter!",
                productDao.findProducts("Lera", null, null).contains(testProductVladimir));

        assertFalse("Error of finding object with filter!",
                productDao.findProducts("Vladimir", null, null).contains(testProductLera));
        assertTrue("Error of finding object with filter!",
                productDao.findProducts("Vladimir", null, null).contains(testProductVladimir));

        assertTrue("Error of finding object with filter!",
                productDao.findProducts("Lera Vladimir", null, null).contains(testProductLera));
        assertTrue("Error of finding object with filter!",
                productDao.findProducts("Lera Vladimir", null, null).contains(testProductVladimir));
    }

    @Test
    public void testFindProductsWithSorting() {
        assertTrue("Error of finding object with sorting!",
                productDao.findProducts(null, "description", "asc").get(0).equals(productDao.getProduct(4L)));

        assertTrue("Error of finding object with sorting!",
                productDao.findProducts(null, "description", "desc").get(0).equals(productDao.getProduct(8L)));

        assertTrue("Error of finding object with sorting!",
                productDao.findProducts(null, "price", "asc").get(0).equals(productDao.getProduct(9L)));

        assertTrue("Error of finding object with sorting!",
                productDao.findProducts(null, "price", "desc").get(0).equals(productDao.getProduct(5L)));
    }

    @Test
    public void testFindProductsWithFilterAndSorting() {
        Product testProductLera1WithPrice200 = new Product(15L, "simsxg75", "Lera 1", new BigDecimal(200), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        Product testProductLera2WithPrice160 = new Product(16L, "simsxg75", "Lera 2", new BigDecimal(160), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        productDao.save(testProductLera1WithPrice200);
        productDao.save(testProductLera2WithPrice160);
        assertTrue("Error of finding object with sorting!",
                productDao.findProducts("Lera", "description", "asc").get(1).equals(testProductLera1WithPrice200));

        assertTrue("Error of finding object with sorting!",
                productDao.findProducts("Lera", "description", "desc").get(0).equals(testProductLera2WithPrice160));

        assertTrue("Error of finding object with sorting!",
                productDao.findProducts("Lera", "price", "asc").get(1).equals(testProductLera2WithPrice160));

        assertTrue("Error of finding object with sorting!",
                productDao.findProducts("Lera", "price", "desc").get(0).equals(testProductLera1WithPrice200));
    }
}
