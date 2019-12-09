package ru.marina.tshop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@PropertySource("classpath:application.properties")
public class SecurityConfiguration {
    private final Environment environment;

    @Autowired
    public SecurityConfiguration(final Environment environment) {
        this.environment = environment;
    }

    public byte[] getJwtSecretKey() {
        return Base64.getDecoder().decode(environment.getProperty("security.jwt.secret-key"));
    }

    public int jwtExpiresInSeconds() {
        return Integer.parseInt(environment.getProperty("security.jwt.expire-in-seconds"));
    }

    public String getJwtIssuer() {
        return environment.getProperty("security.jwt.issuer");
    }
}