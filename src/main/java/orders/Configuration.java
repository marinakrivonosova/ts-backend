package orders;

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

    public String getInitialOrderStatusId() {
        return environment.getProperty("initial.orderStatusId");
    }

    public String getPaidPaymentStatusId() {
        return environment.getProperty("paid.paymentStatusId");
    }

    public String getNotPaidPaymentStatusId() {
        return environment.getProperty("notPaid.paymentStatusId");
    }
}
