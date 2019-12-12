package ru.marina.tshop.orders;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.marina.tshop.orders.lineitems.LineItem;
import ru.marina.tshop.orders.orderstatuses.OrderStatus;
import ru.marina.tshop.users.UserService;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class OrderControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService = mock(OrderService.class);
    private UserService userService = mock(UserService.class);

    @BeforeAll
    public static void setupJsonPath() {
        com.jayway.jsonpath.Configuration.setDefaults(new Configuration.Defaults() {
            private final JsonProvider jsonProvider = new JacksonJsonProvider();
            private final MappingProvider mappingProvider = new JacksonMappingProvider();

            @Override
            public JsonProvider jsonProvider() {
                return jsonProvider;
            }

            @Override
            public MappingProvider mappingProvider() {
                return mappingProvider;
            }

            @Override
            public Set<Option> options() {
                return EnumSet.noneOf(Option.class);
            }
        });
    }
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new OrderController(orderService, userService)).build();
    }

    @Test
    public void submitOrderTest() throws Exception {
        when(userService.getCurrentUserId()).thenReturn("uId1");
        try (final InputStream inputStream = new ClassPathResource("create-order.json").getInputStream()) {
            mockMvc.perform(post("/orders")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(inputStream.readAllBytes())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
        }
    }

    @Test
    public void getOrdersByStatusTest() throws Exception {
        when(userService.getCurrentUserId()).thenReturn("userId1");
        when(orderService.listOrdersByStatus("statusId1", "userId1")).thenReturn(Arrays.asList(
                new Order("orderId1", "userId1", "Earth", "statusId1",
                        "deliveryMethodId1", "paymentMethodId1", "paymentStatusId1"),
                new Order("orderId2", "userId1", "Mars", "statusId1",
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
                .andExpect(jsonPath("$[?(@.id=='orderId2')].userId").value("userId1"))
                .andExpect(jsonPath("$[?(@.id=='orderId2')].address").value("Mars"))
                .andExpect(jsonPath("$[?(@.id=='orderId2')].orderStatusId").value("statusId1"))
                .andExpect(jsonPath("$[?(@.id=='orderId2')].deliveryMethodId").value("deliveryMethodId2"))
                .andExpect(jsonPath("$[?(@.id=='orderId2')].paymentMethodId").value("paymentMethodId2"))
                .andExpect(jsonPath("$[?(@.id=='orderId2')].paymentStatusId").value("paymentStatusId2"));
    }

    @Test
    public void updateOrderTest() throws Exception {
        doNothing().when(orderService).updateOrder("oId", "osId2");
        mockMvc.perform(put("/orders/oId")
                .content("{\"orderStatusId\":\" + osId2 + \"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void getOrderStatuses() throws Exception {
        when(orderService.getOrderStatuses()).thenReturn(Arrays.asList(new OrderStatus("osId1", "created"),
                new OrderStatus("osId2", "active"),
                new OrderStatus("osId3", "completed"),
                new OrderStatus("osId4", "canceled")));

        mockMvc.perform(get("/order-statuses")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value(new OrderStatus("osId1",
                        "created")))
                .andExpect(jsonPath("$[1]").value(new OrderStatus("osId2", "active")))
                .andExpect(jsonPath("$[2]").value(new OrderStatus("osId3", "completed")))
                .andExpect(jsonPath("$[3]").value(new OrderStatus("osId4", "canceled")));
    }

    @Test
    void listLineItems() throws Exception {
        final LineItem lineItem1 = new LineItem("liId1", "id2", "prId1", 3, new BigDecimal("10000.00"));
        final LineItem lineItem2 = new LineItem("liId2", "id2", "prId2", 1, new BigDecimal("12000.00"));

        when(orderService.listLineItems(any())).thenReturn(Arrays.asList(lineItem1, lineItem2));

        mockMvc.perform(get("/id2/line-items")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value(lineItem1))
                .andExpect(jsonPath("$[1]").value(lineItem2));
    }
}