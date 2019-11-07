package ru.marina.tshop.orders;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import ru.marina.tshop.orders.lineitems.LineItem;
import ru.marina.tshop.orders.lineitems.LineItemDao;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.marina.tshop.products.Product;
import ru.marina.tshop.products.ProductDao;
import ru.marina.tshop.utils.IdGenerator;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceTest {
    private ProductDao productDao;
    private LineItemDao lineItemDao;
    private OrderService orderService;

    @MockBean
    private OrderDao orderDao = mock(OrderDao.class);
    private Configuration configuration = mock(Configuration.class);


    @BeforeEach
    void setupDB() throws Exception {
        final JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        productDao = new ProductDao(namedParameterJdbcTemplate);
        orderDao = new OrderDao(namedParameterJdbcTemplate);
        lineItemDao = new LineItemDao(namedParameterJdbcTemplate);
        Connection connection = ds.getConnection();
        final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        final Liquibase liquibase = new Liquibase("test-migration.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.dropAll();
        liquibase.update("test");
        orderService = new OrderService(orderDao, new IdGenerator(), lineItemDao, productDao, configuration);

        when(configuration.getInitialOrderStatusId()).thenReturn("osId1");
        when(configuration.getNotPaidPaymentStatusId()).thenReturn("psId2");
        productDao.addProduct(new Product("prId1", "tv", "full hd", new BigDecimal("10000.00"), 2, 1, 100, "ctId1"));
        productDao.addProduct(new Product("prId2", "tv", "full hd", new BigDecimal("12000.00"), 3, 1, 50, "ctId1"));
        productDao.addProduct(new Product("prId3", "chair", "wooden", new BigDecimal("1000.00"), 1, 1, 20, "ctId2"));
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
    void getPaymentMethods() {
        final List<PaymentMethod> expectedPaymentMethodList = asList(
                new PaymentMethod("pmId1", "card"),
                new PaymentMethod("pmId2", "cash"),
                new PaymentMethod("pmId3", "check")
        );
        assertEquals(expectedPaymentMethodList, orderService.getPaymentMethods());
    }

    @Test
    void listOrderByStatus() {
        orderDao.addOrder(new Order("id1", "uId", "address", "osId1", "dmId1", "pmId1", "psId1"));
        orderDao.addOrder(new Order("id2", "uId", "address", "osId2", "dmId2", "pmId1", "psId1"));

        assertEquals(singletonList(new Order("id2", "uId", "address", "osId2", "dmId2", "pmId1", "psId1")),
                orderService.listOrdersByStatus("osId2"));
        assertEquals(singletonList(new Order("id1", "uId", "address", "osId1", "dmId1", "pmId1", "psId1")),
                orderService.listOrdersByStatus("osId1"));


    }
}