package ru.marina.tshop.orders.payment;

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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentMethodDaoTest {
    private PaymentMethodDao paymentMethodDao;

    @BeforeEach
    void setupDB() throws Exception {
        final JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        paymentMethodDao = new PaymentMethodDao(namedParameterJdbcTemplate);
        try (final Connection connection = ds.getConnection()) {
            final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            final Liquibase liquibase = new Liquibase("test-migration.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.dropAll();
            liquibase.update("test");
        }
    }

    @Test
    void listPaymentMethods() {
        final List<PaymentMethod> paymentMethodList = Arrays.asList(new PaymentMethod("pmId1", "card"),
                new PaymentMethod("pmId2", "cash"), new PaymentMethod("pmId3", "check"));
        assertEquals(paymentMethodList, paymentMethodDao.listPaymentMethods());
    }

    @Test
    void getPaymentMethod() {
        assertEquals("cash", paymentMethodDao.getPaymentMethod("pmId2").getPaymentMethod());
        assertEquals("check", paymentMethodDao.getPaymentMethod("pmId3").getPaymentMethod());
    }
}
