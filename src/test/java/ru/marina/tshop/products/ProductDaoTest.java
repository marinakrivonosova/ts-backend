package ru.marina.tshop.products;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
@ContextConfiguration("classpath:application-context-test.xml")
@WebAppConfiguration
class ProductDaoTest {
    @Autowired
    private ProductDao productDao;

    @Test
    void filterProductsTest() {

        final List<Product> tv = productDao.filterProducts("tv", 1, 1);
        assertEquals(1, tv.size());
        assertEquals("tv", tv.get(0).getName());

        assertEquals(2, productDao.filterProducts("t", 0, 20).size());
        assertEquals(1, productDao.filterProducts("cha", 0, 20).size());
    }

    @Test
    public void addAndGetProductTest() {
        productDao.addProduct(new Product("prId4", "bed", "very soft", new BigDecimal("2000"), 5, 3, 10, "ctId2"));

        assertEquals(new Product("prId3", "chair", "wooden", new BigDecimal("1000.00"), 1, 1, 20, "ctId2"),
                productDao.getProduct("prId3"));
        assertEquals(new Product("prId4", "bed", "very soft", new BigDecimal("2000.00"), 5, 3, 10, "ctId2"),
                productDao.getProduct("prId4"));
    }

    @Test
    public void updateOrderTest() {
        final Product product = productDao.getProduct("prId1");

        product.setName("tv samsung");
        product.setCount(250);

        productDao.updateProduct(product);
        assertEquals(product.getCount(), productDao.getProduct("prId1").getCount());
        assertEquals(product.getName(), productDao.getProduct("prId1").getName());
    }
}