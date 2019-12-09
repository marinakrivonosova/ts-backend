package ru.marina.tshop.orders.delivery;

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

public class DeliveryMethodServiceTest {
    private DeliveryMethodDao deliveryMethodDao;
    private DeliveryMethodService deliveryMethodService;

    @BeforeEach
    void setupDB() throws Exception {
        final JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        DeliveryMethodDao deliveryMethodDao = new DeliveryMethodDao(namedParameterJdbcTemplate);
        try (final Connection connection = ds.getConnection()) {
            final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            final Liquibase liquibase = new Liquibase("test-migration.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.dropAll();
            liquibase.update("test");
        }
        deliveryMethodService = new DeliveryMethodService(deliveryMethodDao);
    }

    @Test
    void listDeliveryMethods() {
        final List<DeliveryMethod> deliveryMethods = Arrays.asList(
                new DeliveryMethod("dmId1", "self-pickup"),
                new DeliveryMethod("dmId2", "post"));

        assertEquals(deliveryMethods, deliveryMethodService.listDeliveryMethods());
    }

    @Test
    void getDeliveryMethod() {
        assertEquals("post", deliveryMethodService.getDeliveryMethod("dmId2").getDeliveryMethod());
    }
}
