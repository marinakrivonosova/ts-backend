package ru.marina.tshop.orders.lineitems;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.marina.tshop.orders.Order;
import ru.marina.tshop.orders.OrderDao;
import ru.marina.tshop.products.Product;
import ru.marina.tshop.products.ProductDao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
@ContextConfiguration("classpath:application-context-test.xml")
@WebAppConfiguration
public class LineItemDaoTest {
    @Autowired
    private LineItemDao lineItemDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private OrderDao orderDao;

    @Test
    void addLineItem() {
        orderDao.addOrder(new Order("id1", "12345", "uId", "address", "osId1", "dmId1", "pmId1", "psId2"));

        final LineItem lineItem = new LineItem("liId1", "id1", "prId1", 3, new BigDecimal("10000.00"));
        lineItemDao.addLineItem(lineItem);

        assertEquals(lineItem, lineItemDao.getLineItem("liId1"));
    }

    @Test
    void listLineItems() {
        orderDao.addOrder(new Order("id2","123456", "uId", "address", "osId1", "dmId1", "pmId1", "psId2"));
        lineItemDao.addLineItem(new LineItem("liId2", "id2", "prId1", 3, new BigDecimal("10000.00")));
        lineItemDao.addLineItem(new LineItem("liId3", "id2", "prId2", 1, new BigDecimal("12000.00")));

        final List<LineItem> expectedLineItemList = Arrays.asList(
                new LineItem("liId2", "id2", "prId1", 3, new BigDecimal("10000.00")),
                new LineItem("liId3", "id2", "prId2", 1, new BigDecimal("12000.00"))
        );

        assertEquals(expectedLineItemList, lineItemDao.listLineItems("id2"));
    }
}
