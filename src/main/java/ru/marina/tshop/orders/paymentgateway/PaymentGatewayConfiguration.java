package ru.marina.tshop.orders.paymentgateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class PaymentGatewayConfiguration {
    private final Environment environment;

    @Autowired
    public PaymentGatewayConfiguration(final Environment environment) {
        this.environment = environment;
    }

    public String paymentGatewayUri() {
        return environment.getProperty("payments.gateway.url");
    }
}
