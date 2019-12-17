package ru.marina.tshop.orders.paymentgateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
@ContextConfiguration("classpath:application-context-test.xml")
class PaymentGatewayServiceTest {
    @Autowired
    private PaymentGatewayService service;
    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void doPayment() throws URISyntaxException {
        mockServer.expect(
                ExpectedCount.once(),
                requestTo(new URI("http://localhost/paymentgateway/api/pay"))
        )
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"paymentReference\":\"abc-def-geh\"}")
                );

        final String paymentReference = service.doPayment("1234 5678 9012 3456", "123", YearMonth.of(2030, 2), "IVAN IVANOV", new BigDecimal("12.34"));

        assertEquals("abc-def-geh", paymentReference);

        mockServer.verify();
    }
}