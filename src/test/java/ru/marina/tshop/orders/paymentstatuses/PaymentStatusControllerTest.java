package ru.marina.tshop.orders.paymentstatuses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.marina.tshop.orders.delivery.DeliveryMethod;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class PaymentStatusControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private PaymentStatusService paymentStatusService = mock(PaymentStatusService.class);

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PaymentStatusController(paymentStatusService)).build();
    }

    @Test
    void listPaymentStatus() throws Exception {
        when(paymentStatusService.listPaymentStatuses()).thenReturn(Arrays.asList(
                new PaymentStatus("psId1", "paid"),
                new PaymentStatus("psId2", "not paid")));
        mockMvc.perform(get("/payment-statuses")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value(new PaymentStatus("psId1",
                        "paid")))
                .andExpect(jsonPath("$[1]").value(new PaymentStatus("psId2",
                        "not paid")));
    }

    @Test
    void getPaymentStatus() throws Exception {
        when(paymentStatusService.getPaymentStatus("psId1")).thenReturn(
                new PaymentStatus("psId1", "paid"));
        mockMvc.perform(get("/payment-statuses/psId1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("psId1"))
                .andExpect(jsonPath("$.paymentStatus").value("paid"));
    }
}