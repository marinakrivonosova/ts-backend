package ru.marina.tshop.orders.comments;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.marina.tshop.orders.Order;
import ru.marina.tshop.orders.OrderDao;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderCommentsDaoTest {
    private OrderCommentDao orderCommentDao;
    private OrderDao orderDao;

    @BeforeEach
    void setupDB() throws Exception {
        final JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        orderCommentDao = new OrderCommentDao(namedParameterJdbcTemplate);
        orderDao = new OrderDao(namedParameterJdbcTemplate);
        Connection connection = ds.getConnection();
        final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        final Liquibase liquibase = new Liquibase("test-migration.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.dropAll();
        liquibase.update("test");
    }

    @Test
    void addOrderComment() {
        orderDao.addOrder(new Order("id", "12345", "uId", "address", "osId1", "dmId1", "pmId1", "psId2"));
        orderCommentDao.addOrderComment(new OrderComment("ocId1", "id", "there is a comment"));

        assertEquals("there is a comment", orderCommentDao.getOrderComment("ocId1").getComment());
    }
}