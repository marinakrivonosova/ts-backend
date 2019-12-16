package ru.marina.tshop.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
    @Bean
    public DataSource dataSource(@Value("${db.migration.script}") final String migrationScript,
                                 @Value("${db.migration.contexts}") final String context,
                                 @Value("${db.password}") final String password,
                                 @Value("${db.username}") final String username,
                                 @Value("${db.driver}") final String driver,
                                 @Value("${db.url}") final String url) throws Exception {
        final DriverManagerDataSource db = new DriverManagerDataSource(url);
        db.setUsername(username);
        db.setPassword(password);
        db.setDriverClassName(driver);
        try (final Connection connection = db.getConnection()) {
            final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            final Liquibase liquibase = new Liquibase(migrationScript, new ClassLoaderResourceAccessor(), database);
            liquibase.update(context);
            return db;
        }
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(final DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager txManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
