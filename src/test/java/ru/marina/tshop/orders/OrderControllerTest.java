package ru.marina.tshop.orders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class OrderControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService = mock(OrderService.class);

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new OrderController(orderService)).build();
    }

    @Test
    public void submitOrderTest() throws Exception {
        try (final InputStream inputStream = new ClassPathResource("create-order.json").getInputStream()) {
            mockMvc.perform(post("/orders")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(inputStream.readAllBytes())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
        }
    }

    @Test
    public void getPaymentMethodsTest() throws Exception {
        when(orderService.getPaymentMethods()).thenReturn(Arrays.asList(new PaymentMethod("paymentMethodId1",
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
    public void getOrdersByStatusTest() throws Exception {
        when(orderService.listOrdersByStatus("statusId1")).thenReturn(Arrays.asList(
                new Order("orderId1", "userId1", "Earth", "statusId1",
                        "deliveryMethodId1", "paymentMethodId1", "paymentStatusId1"),
                new Order("orderId2", "userId2", "Mars", "statusId1",
                        "deliveryMethodId2", "paymentMethodId2", "paymentStatusId2")));

        mockMvc.perform(get("/orders")
                .param("status", "statusId1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id=='orderId1')].id").value("orderId1"))
                .andExpect(jsonPath("$[?(@.id=='orderId1')].userId").value("userId1"))
                .andExpect(jsonPath("$[?(@.id=='orderId1')].address").value("Earth"))
                .andExpect(jsonPath("$[?(@.id=='orderId1')].orderStatusId").value("statusId1"))
                .andExpect(jsonPath("$[?(@.id=='orderId1')].deliveryMethodId").value("deliveryMethodId1"))
                .andExpect(jsonPath("$[?(@.id=='orderId1')].paymentMethodId").value("paymentMethodId1"))
                .andExpect(jsonPath("$[?(@.id=='orderId1')].paymentStatusId").value("paymentStatusId1"))
                .andExpect(jsonPath("$[?(@.id=='orderId2')].id").value("orderId2"))
                .andExpect(jsonPath("$[?(@.id=='orderId2')].userId").value("userId2"))
                .andExpect(jsonPath("$[?(@.id=='orderId2')].address").value("Mars"))
                .andExpect(jsonPath("$[?(@.id=='orderId2')].orderStatusId").value("statusId1"))
                .andExpect(jsonPath("$[?(@.id=='orderId2')].deliveryMethodId").value("deliveryMethodId2"))
                .andExpect(jsonPath("$[?(@.id=='orderId2')].paymentMethodId").value("paymentMethodId2"))
                .andExpect(jsonPath("$[?(@.id=='orderId2')].paymentStatusId").value("paymentStatusId2"));
    }

    @Test
    public void updateOrderTest() throws Exception {
        try (final InputStream inputStream = new ClassPathResource("update-order.json").getInputStream()) {
            mockMvc.perform(put("/orders/abc")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(inputStream.readAllBytes())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }
}