package ru.marina.tshop.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class Configuration {

    private final Environment environment;

    @Autowired
    public Configuration(final Environment environment) {
        this.environment = environment;
    }

    public String getInitialOrderStatus() {
        return environment.getProperty("orders.initial.status");
    }

    public String getPaidPaymentStatusId() {
        return environment.getProperty("orders.payment.status.id.paid");
    }

    public String getNotPaidPaymentStatusId() {
        return environment.getProperty("order.payment.status.id.not.paid");
    }
}