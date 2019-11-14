package ru.marina.tshop.products;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService = mock(ProductService.class);

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService)).build();
    }

    @Test
    void filterProducts() throws Exception {
        when(productService.getProductCount("tv")).thenReturn(9);
        when(productService.filterProducts("tv", 1, 2)).thenReturn(Collections.singletonList(
                new Product("prId1", "tv", "full hd", new BigDecimal("10000"), 2, 1, 100, "ctId1")));
        //TODO why here "1000" is not OK
        mockMvc.perform(get("/products")
                .param("title", "tv")
                .param("offset", "1")
                .param("limit", "2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products[?(@.productId=='prId1')].productId").value("prId1"))
                .andExpect(jsonPath("$.products[?(@.productId=='prId1')].name").value("tv"))
                .andExpect(jsonPath("$.products[?(@.productId=='prId1')].description").value("full hd"))
                .andExpect(jsonPath("$.products[?(@.productId=='prId1')].price").value(10000))
                .andExpect(jsonPath("$.products[?(@.productId=='prId1')].weight").value(2))
                .andExpect(jsonPath("$.products[?(@.productId=='prId1')].volume").value(1))
                .andExpect(jsonPath("$.products[?(@.productId=='prId1')].count").value(100))
                .andExpect(jsonPath("$.products[?(@.productId=='prId1')].categoryId").value("ctId1"))
                .andExpect(jsonPath("$.overallSuitableProducts").value(9));
    }

    @Test
    void getProduct() throws Exception {
        when(productService.getProduct("prId1")).thenReturn(
                new Product("prId1", "tv", "full hd", new BigDecimal("10000"), 2, 1, 100, "ctId1"));
        //TODO why here "1000" is OK
        mockMvc.perform(get("/products/prId1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("prId1"))
                .andExpect(jsonPath("$.name").value("tv"))
                .andExpect(jsonPath("$.description").value("full hd"))
                .andExpect(jsonPath("$.price").value("10000"))
                .andExpect(jsonPath("$.weight").value(2))
                .andExpect(jsonPath("$.volume").value(1))
                .andExpect(jsonPath("$.count").value(100))
                .andExpect(jsonPath("$.categoryId").value("ctId1"));
    }
}