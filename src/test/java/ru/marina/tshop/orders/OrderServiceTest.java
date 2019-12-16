package ru.marina.tshop.orders;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.marina.tshop.orders.lineitems.LineItem;
import ru.marina.tshop.orders.lineitems.LineItemDao;
import ru.marina.tshop.orders.orderstatuses.OrderStatusDao;
import ru.marina.tshop.products.ProductDao;
import ru.marina.tshop.utils.IdGenerator;
import ru.marina.tshop.utils.UniqueSeq;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceTest {
    private ProductDao productDao;
    private LineItemDao lineItemDao;
    private OrderStatusDao orderStatusDao;
    private OrderDao orderDao;
    private OrderService orderService;
    @MockBean
    private Configuration configuration = mock(Configuration.class);

    @MockBean
    private UniqueSeq uniqueSeq = mock(UniqueSeq.class);

    @BeforeEach
    void setupDB() throws Exception {
        final JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        productDao = new ProductDao(namedParameterJdbcTemplate);
        orderDao = new OrderDao(namedParameterJdbcTemplate);
        lineItemDao = new LineItemDao(namedParameterJdbcTemplate);
        orderStatusDao = new OrderStatusDao(namedParameterJdbcTemplate);
        Connection connection = ds.getConnection();
        final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        final Liquibase liquibase = new Liquibase("test-migration.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.dropAll();
        liquibase.update("test");
        orderService = new OrderService(orderDao, new IdGenerator(), uniqueSeq, lineItemDao, productDao, configuration, orderStatusDao);

        when(configuration.getInitialOrderStatus()).thenReturn("created");
        when(configuration.getNotPaidPaymentStatusId()).thenReturn("psId2");
        when(uniqueSeq.getNext()).thenReturn("12345");
    }

    @Test
    void addOrder() {
        final String orderId = orderService.addOrder(
                "uId1",
                asList(
                        new CreateLineItem("prId1", 3),
                        new CreateLineItem("prId2", 1),
                        new CreateLineItem("prId3", 1)),
                "address",
                "dmId1",
                "pmId2");

        assertEquals(new Order(
                orderId,
                "12345",
                "uId1",
                "address",
                "osId1",
                "dmId1",
                "pmId2",
                "psId2"
        ), orderDao.getOrder(orderId));

        final List<LineItem> lineItems = lineItemDao.listLineItems(orderId);

        assertEquals(3, lineItems.size());

        assertEquals(1, lineItems.stream().filter(lineItem ->
                "prId1".equals(lineItem.getProductId()) &&
                        lineItem.getCount() == 3 &&
                        new BigDecimal("10000.00").equals(lineItem.getPrice())).count());
        assertEquals(1, lineItems.stream().filter(lineItem ->
                "prId2".equals(lineItem.getProductId()) &&
                        lineItem.getCount() == 1 &&
                        new BigDecimal("12000.00").equals(lineItem.getPrice())).count());
        assertEquals(1, lineItems.stream().filter(lineItem ->
                "prId3".equals(lineItem.getProductId()) &&
                        lineItem.getCount() == 1 &&
                        new BigDecimal("1000.00").equals(lineItem.getPrice())).count());

        assertEquals(97, productDao.getProduct("prId1").getCount());
        assertEquals(49, productDao.getProduct("prId2").getCount());
        assertEquals(19, productDao.getProduct("prId3").getCount());
    }

    @Test
    void productExistenceValidation() {
        assertThrows(EmptyResultDataAccessException.class, () -> orderService.addOrder(
                "uId1",
                singletonList(new CreateLineItem("nonExistingProductId", 3)),
                "address",
                "dmId1",
                "pmId2")
        );
    }

    @Test
    void argumentValidation() {
        assertThrows(IllegalArgumentException.class, () -> orderService.addOrder(
                "uId1",
                emptyList(),
                "address",
                "dmId1",
                "pmId2")
        );
        assertThrows(IllegalArgumentException.class, () -> orderService.addOrder(
                "uId1",
                null,
                "address",
                "dmId1",
                "pmId2")
        );
    }

    @Test
    void productAvailabilityValidation() {
        assertThrows(IllegalArgumentException.class, () -> orderService.addOrder(
                "uId1",
                singletonList(new CreateLineItem("prId1", 150)),
                "address",
                "dmId1",
                "pmId2")
        );
    }

    @Test
    void listOrderByStatus() {
        final Order order1 = new Order("id1", "12345", "uId", "address", "osId1", "dmId1", "pmId1", "psId1");
        orderDao.addOrder(order1);
        final Order order2 = new Order("id2", "123456", "uId", "address", "osId2", "dmId2", "pmId1", "psId1");
        orderDao.addOrder(order2);

        assertEquals(singletonList(order2),
                orderService.listOrdersByStatus("osId2", "uId"));
        assertEquals(singletonList(order1),
                orderService.listOrdersByStatus("osId1", "uId"));
    }

    @Test
    void updateOrder() {
        final Order order = new Order("id1", "12345", "uId", "address", "osId1", "dmId1", "pmId1", "psId1");
        orderDao.addOrder(order);
        order.setOrderStatusId("osId2");
        orderService.updateOrder("id1", order.getOrderStatusId());
        assertEquals(order, orderDao.getOrder("id1"));
    }

    @Test
    void getAllOrders() {
        final List<Order> orders = asList(
                new Order("id1", "12345", "uId", "address", "osId1", "dmId1", "pmId1", "psId1"),
                new Order("id2", "123456", "uId", "address", "osId2", "dmId2", "pmId1", "psId1"));

        orderDao.addOrder(new Order("id1", "12345", "uId", "address", "osId1", "dmId1", "pmId1", "psId1"));
        orderDao.addOrder(new Order("id2", "123456", "uId", "address", "osId2", "dmId2", "pmId1", "psId1"));

        assertEquals(orders, orderService.getAllOrders());
    }

    @Test
    void getOrder() {
        final Order order1 = new Order("id1", "12345", "uId", "address", "osId1", "dmId1", "pmId1", "psId1");
        orderDao.addOrder(order1);
        final Order order2 = new Order("id2", "123456", "uId", "address", "osId2", "dmId2", "pmId1", "psId1");
        orderDao.addOrder(order2);

        assertEquals(order1, orderService.getOrder("id1"));
        assertEquals(order2, orderService.getOrder("id2"));
    }
}
