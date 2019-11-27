package ru.marina.tshop.orders;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.marina.tshop.orders.lineitems.LineItemDao;
import ru.marina.tshop.products.Product;
import ru.marina.tshop.products.ProductDao;
import ru.marina.tshop.utils.IdGenerator;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class OrderTransactionTest {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private LineItemDao lineItemDao;
    @Autowired
    private OrderService orderService;
    @Autowired
    private Configuration configuration;
    @Autowired
    private IdGenerator idGenerator;

    @org.springframework.context.annotation.Configuration
    @EnableTransactionManagement
    @ComponentScan
    static class Config {
        @Bean
        public DataSource dataSource() throws Exception {
            final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            final EmbeddedDatabase db = builder
                    .setType(EmbeddedDatabaseType.H2)
                    .build();

            try (final Connection connection = db.getConnection()) {
                final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
                final Liquibase liquibase = new Liquibase("test-migration.xml", new ClassLoaderResourceAccessor(), database);
                liquibase.dropAll();
                liquibase.update("test");
            }

            return db;
        }

        @Bean
        public PlatformTransactionManager txManager(final DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(final DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public Configuration configSetup() {
            final Configuration configuration = mock(Configuration.class);
            when(configuration.getInitialOrderStatus()).thenReturn("created");
            when(configuration.getNotPaidPaymentStatusId()).thenReturn("psId2");
            return configuration;
        }

        @Bean
        public IdGenerator idGeneratorSetup() {
            final IdGenerator idGenerator = mock(IdGenerator.class);
            when(idGenerator.generateId()).thenReturn("mockedId");
            return idGenerator;
        }

        @Bean
        public ProductDao productDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new ProductDao(namedParameterJdbcTemplate);
        }
    }

    @BeforeEach
    void setupDB() {
        productDao.addProduct(new Product("prId1", "tv", "full hd", new BigDecimal("10000.00"), 2, 1, 100, "ctId1"));
        productDao.addProduct(new Product("prId2", "tv", "full hd", new BigDecimal("12000.00"), 3, 1, 50, "ctId1"));
        productDao.addProduct(new Product("prId3", "chair", "wooden", new BigDecimal("1000.00"), 1, 1, 20, "ctId2"));
    }

    @Test
    public void addOrder() {
        assertThrows(RuntimeException.class, () -> orderService.addOrder(
                "uId1",
                asList(
                        new CreateLineItem("prId1", 3),
                        new CreateLineItem("prId2", 1),
                        new CreateLineItem("prId3", 1)),
                "address",
                "dmId1",
                "pmId2"));

        assertThrows(EmptyResultDataAccessException.class, () -> orderDao.getOrder(idGenerator.generateId()));
        assertEquals(0, lineItemDao.listLineItems(idGenerator.generateId()).size());
    }
}


