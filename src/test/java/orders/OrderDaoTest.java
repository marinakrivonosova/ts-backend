package orders;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderDaoTest {
    private OrderDao orderDao;

    @BeforeEach
    void setupDB() throws Exception {
        final JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        orderDao = new OrderDao(namedParameterJdbcTemplate);
        Connection connection = ds.getConnection();
        final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        final Liquibase liquibase = new Liquibase("test-migration.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.dropAll();
        liquibase.update("test");
    }

    @Test
    void addOrderDaoTest() {
        final Order order = new Order("id", "uId", "address", "osId1", "dmId1", "pmId1", "psId2");
        orderDao.addOrder(order);
        assertEquals(order, orderDao.getOrder("id"));
    }

    @Test
    void getPaymentMethodsTest() {
        final List<PaymentMethod> paymentMethodList = Arrays.asList(new PaymentMethod("pmId1", "card"),
                new PaymentMethod("pmId2", "cash"), new PaymentMethod("pmId3", "check"));
        assertEquals(paymentMethodList, orderDao.getPaymentMethods());
    }

    @Test
    void getOrdersByStatus() {
        final Order order1 = new Order("id1", "uId", "address", "osId1", "dmId1", "pmId1", "psId1");
        final Order order2 = new Order("id2", "uId", "address", "osId2", "dmId2", "pmId1", "psId1");
        orderDao.addOrder(order1);
        orderDao.addOrder(order2);

        final List<Order> ordersActive = singletonList(order2);
        assertEquals(ordersActive, orderDao.listOrdersByStatus("osId2"));

        final List<Order> ordersCompleted = singletonList(order1);
        assertEquals(ordersCompleted, orderDao.listOrdersByStatus("osId1"));
    }

    @Test
    void updateOrder() {
        final Order order = new Order("id", "uId", "address", "osId1", "dmId1", "pmId1", "psId1");
        orderDao.addOrder(order);
        assertEquals(order, orderDao.getOrder("id"));

        order.setAddress("new address");
        order.setOrderStatusId("osId2");
        order.setDeliveryMethodId("dmId2");
        order.setPaymentMethodId("pmId2");
        order.setPaymentStatusId("psId2");
        orderDao.updateOrder(order);

        assertEquals(order, orderDao.getOrder("id"));
    }

    @Test
    void getOrderByIdTest() {
        orderDao.addOrder(new Order("id1", "uId", "address", "osId1", "dmId1", "pmId1", "psId2"));
        orderDao.addOrder(new Order("id2", "uId", "address", "osId1", "dmId2", "pmId1", "psId1"));
        orderDao.addOrder(new Order("id3", "uId1", "address", "osId2", "dmId2", "pmId2", "psId1"));

        assertEquals(new Order("id1", "uId", "address", "osId1", "dmId1", "pmId1", "psId2"), orderDao.getOrder("id1"));
        assertEquals(new Order("id2", "uId", "address", "osId1", "dmId2", "pmId1", "psId1"), orderDao.getOrder("id2"));
        assertEquals(new Order("id3", "uId1", "address", "osId2", "dmId2", "pmId2", "psId1"), orderDao.getOrder("id3"));
    }

    @Test
    void getAllOrdersTest() {
        orderDao.addOrder(new Order("id1", "uId", "address", "osId1", "dmId1", "pmId1", "psId2"));
        orderDao.addOrder(new Order("id2", "uId", "address", "osId1", "dmId2", "pmId1", "psId1"));
        orderDao.addOrder(new Order("id3", "uId1", "address", "osId2", "dmId2", "pmId2", "psId1"));

        final List<Order> ordersList = Arrays.asList(
                new Order("id1", "uId", "address", "osId1", "dmId1", "pmId1", "psId2"),
                new Order("id2", "uId", "address", "osId1", "dmId2", "pmId1", "psId1"),
                new Order("id3", "uId1", "address", "osId2", "dmId2", "pmId2", "psId1"));
        assertEquals(orderDao.getAllOrders(), ordersList);
    }
}