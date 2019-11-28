package ru.marina.tshop.orders.delivery;

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

public class DeliveryMethodControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private DeliveryMethodService deliveryMethodService = mock(DeliveryMethodService.class);

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DeliveryMethodController(deliveryMethodService)).build();
    }

    @Test
    void listDeliveryMethods() throws Exception {
        when(deliveryMethodService.listDeliveryMethods()).thenReturn(Arrays.asList(new DeliveryMethod("dmId1", "post"),
                new DeliveryMethod("dmId2", "self-pick-up")));
        mockMvc.perform(get("/delivery-methods")
                .accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value(new DeliveryMethod("dmId1",
                        "post")))
                .andExpect(jsonPath("$[1]").value(new DeliveryMethod("dmId2",
                        "self-pick-up")));
    }

    @Test
    void getDeliveryMethod() throws Exception {
        when(deliveryMethodService.getDeliveryMethod(any())).thenReturn(new DeliveryMethod("dmId1", "post"));

        mockMvc.perform(get("/delivery-methods/dmId1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("dmId1"))
                .andExpect(jsonPath("$.deliveryMethod").value("post"));
    }
}
