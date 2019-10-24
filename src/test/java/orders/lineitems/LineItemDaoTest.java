package orders.lineitems;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import orders.Order;
import orders.OrderDao;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import products.Product;
import products.ProductDao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineItemDaoTest {
    private LineItemDao lineItemDao;
    private ProductDao productDao;
    private OrderDao orderDao;

    @BeforeEach
    void setupDB() throws Exception {
        final JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        lineItemDao = new LineItemDao(namedParameterJdbcTemplate);
        productDao = new ProductDao(namedParameterJdbcTemplate);
        orderDao = new OrderDao(namedParameterJdbcTemplate);
        Connection connection = ds.getConnection();
        final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        final Liquibase liquibase = new Liquibase("test-migration.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.dropAll();
        liquibase.update("test");
    }

    @Test
    void addLineItem() {
        productDao.addProduct(new Product("prId1", "tv", "full hd", new BigDecimal("10000.00"), 2, 1, 100, "ctId1"));
        orderDao.addOrder(new Order("id", "uId", "address", "osId1", "dmId1", "pmId1", "psId2"));

        final LineItem lineItem = new LineItem("liId", "id", "prId1", 3, new BigDecimal("10000.00"));
        lineItemDao.addLineItem(lineItem);

        assertEquals(lineItem, lineItemDao.getLineItem("liId"));
    }

    @Test
    void listLineItems() {
        productDao.addProduct(new Product("prId1", "tv", "full hd", new BigDecimal("10000.00"), 2, 1, 100, "ctId1"));
        productDao.addProduct(new Product("prId2", "tv", "full hd", new BigDecimal("12000.00"), 3, 1, 50, "ctId1"));
        orderDao.addOrder(new Order("id", "uId", "address", "osId1", "dmId1", "pmId1", "psId2"));
        lineItemDao.addLineItem(new LineItem("liId1", "id", "prId1", 3, new BigDecimal("10000.00")));
        lineItemDao.addLineItem(new LineItem("liId2", "id", "prId2", 1, new BigDecimal("12000.00")));

        final List<LineItem> expectedLineItemList = Arrays.asList(
                new LineItem("liId1", "id", "prId1", 3, new BigDecimal("10000.00")),
                new LineItem("liId2", "id", "prId2", 1, new BigDecimal("12000.00"))
        );

        assertEquals(expectedLineItemList, lineItemDao.listLineItems("id"));
    }

}
