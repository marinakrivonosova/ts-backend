package ru.marina.tshop.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
@Configuration
public class DatabaseConfig {
    @Bean
    public DataSource dataSource() throws Exception {
        final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        final EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.H2)
                .build();

        Connection connection = db.getConnection();
        final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        final Liquibase liquibase = new Liquibase("migration.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.dropAll();
        liquibase.update("prod");

        return db;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() throws Exception {
        return new NamedParameterJdbcTemplate(dataSource());
    }

}
