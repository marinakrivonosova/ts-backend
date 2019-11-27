package ru.marina.tshop.orders.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PaymentMethodControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private PaymentMethodService paymentMethodService = mock(PaymentMethodService.class);

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PaymentMethodController(paymentMethodService)).build();
    }

    @Test
    public void listPaymentMethods() throws Exception {
        when(paymentMethodService.listPaymentMethods()).thenReturn(Arrays.asList(new PaymentMethod("paymentMethodId1",
                "cash"), new PaymentMethod("paymentMethodId2", "card")));

        mockMvc.perform(get("/payment-methods")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value(new PaymentMethod("paymentMethodId1",
                        "cash")))
                .andExpect(jsonPath("$[1]").value(new PaymentMethod("paymentMethodId2", "card")));
    }

    @Test
    public void getPaymentMethod() throws Exception {
        when(paymentMethodService.getPaymentMethod(any())).thenReturn(new PaymentMethod("paymentMethodId1",
                "cash"));
        mockMvc.perform(get("/payment-methods/paymentMethodId1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("paymentMethodId1"))
                .andExpect(jsonPath("$.paymentMethod").value("cash"));
    }
}