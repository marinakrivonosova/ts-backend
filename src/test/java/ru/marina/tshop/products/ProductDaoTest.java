package ru.marina.tshop.products;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductDaoTest {
    private ProductDao productDao;

    @BeforeEach
    void setupDB() throws Exception {
        final JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        productDao = new ProductDao(namedParameterJdbcTemplate);
        Connection connection = ds.getConnection();
        final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        final Liquibase liquibase = new Liquibase("test-migration.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.dropAll();
        liquibase.update("test");
    }

    @Test
    void filterProductsTest() {
        productDao.addProduct(new Product("prId1", "tv", "full hd", new BigDecimal("10000"), 2, 1, 100, "ctId1"));
        productDao.addProduct(new Product("prId2", "tv", "full hd", new BigDecimal("12000"), 3, 1, 50, "ctId1"));
        productDao.addProduct(new Product("prId3", "tv", "full hd", new BigDecimal("8000"), 1, 1, 20, "ctId1"));
        productDao.addProduct(new Product("prId4", "Best Armchair", "very soft", new BigDecimal("2000"), 5, 3, 10, "ctId2"));

        final List<Product> tv = productDao.filterProducts("tv", 1, 1);
        assertEquals(1, tv.size());
        assertEquals("tv", tv.get(0).getName());

        assertEquals(1, productDao.filterProducts("cha", 0, 20).size());
        assertEquals(1, productDao.filterProducts("arm", 0, 20).size());
        assertEquals(1, productDao.filterProducts("BEST", 0, 20).size());
    }

    @Test
    public void addAndGetProductTest(){
        productDao.addProduct(new Product("prId1", "tv", "full hd", new BigDecimal("10000"), 2, 1, 100, "ctId1"));
        productDao.addProduct(new Product("prId2", "tv", "full hd", new BigDecimal("12000"), 3, 1, 50, "ctId1"));
        productDao.addProduct(new Product("prId3", "tv", "full hd", new BigDecimal("8000"), 1, 1, 20, "ctId1"));
        productDao.addProduct(new Product("prId4", "armchair", "very soft", new BigDecimal("2000"), 5, 3, 10, "ctId2"));

        assertEquals(new Product("prId3", "tv", "full hd", new BigDecimal("8000.00"), 1, 1, 20, "ctId1"),
                productDao.getProduct("prId3"));
        assertEquals(new Product("prId4", "armchair", "very soft", new BigDecimal("2000.00"), 5, 3, 10, "ctId2"),
                productDao.getProduct("prId4"));
    }

    @Test
    public void updateOrderTest() {
        final Product product = new Product("prId1", "tv", "full hd", new BigDecimal("10000.00"), 2, 1, 100, "ctId1");
        productDao.addProduct(product);

        assertEquals(product, productDao.getProduct("prId1"));

        product.setName("tv samsung");
        product.setCount(250);

        productDao.updateProduct(product);
        assertEquals(product.getCount(), productDao.getProduct("prId1").getCount());
        assertEquals(product.getName(), productDao.getProduct("prId1").getName());
    }
}