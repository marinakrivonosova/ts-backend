package ru.marina.tshop.orders.paymentgateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.YearMonth;

@Component
public class PaymentGatewayService {
    private final PaymentGatewayConfiguration configuration;
    private final RestTemplate restTemplate;

    @Autowired
    public PaymentGatewayService(final PaymentGatewayConfiguration configuration, final RestTemplate restTemplate) {
        this.configuration = configuration;
        this.restTemplate = restTemplate;
    }

    public String doPayment(final String cardNumber,
                            final String cvc,
                            final YearMonth expirationDate,
                            final String cardHolder,
                            final BigDecimal chargedAmount) {
        final PaymentResult result = restTemplate.postForObject(
                configuration.paymentGatewayUri() + "/paymentgateway/api/pay",
                new PaymentRequest(cardNumber, cvc, expirationDate, cardHolder, chargedAmount),
                PaymentResult.class);

        return result.getPaymentReference();
    }
}
