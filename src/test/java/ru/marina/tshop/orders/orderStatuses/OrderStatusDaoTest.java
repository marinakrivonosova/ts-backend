package ru.marina.tshop.orders.orderStatuses;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderStatusDaoTest {
    private OrderStatusDao orderStatusDao;

    @BeforeEach
    void setupDB() throws Exception {
        final JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        orderStatusDao = new OrderStatusDao(namedParameterJdbcTemplate);
        Connection connection = ds.getConnection();
        final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        final Liquibase liquibase = new Liquibase("test-migration.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.dropAll();
        liquibase.update("test");
    }

    @Test
    void getOrderStatusId() {
        assertEquals("osId1", orderStatusDao.getOrderStatusId("created"));
        assertEquals("osId2", orderStatusDao.getOrderStatusId("active"));
    }

    @Test
    void getOrderStatus() {
        assertEquals(new OrderStatus("osId1", "created"), orderStatusDao.getOrderStatus("osId1"));
    }

    @Test
    void addOrderStatus() {
        final OrderStatus orderStatus = new OrderStatus("osId5", "updated");
        orderStatusDao.addOrderStatus(orderStatus);
        assertEquals(orderStatusDao.getOrderStatus(orderStatus.getId()), orderStatusDao.getOrderStatus("osId5"));
    }
}
