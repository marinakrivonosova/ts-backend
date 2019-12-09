package ru.marina.tshop.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private UserService userService = mock(UserService.class);

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
    }

    @Test
    void login() throws Exception {
        when(userService.login(any(), any())).thenReturn("token");

        mockMvc.perform(post("/users/login")
                .content("{\"login\": \"tom@email.com\", \"password\": \"passw0rd\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("token"));
    }

    @Test
    void register() throws Exception {
        when(userService.register(any(), any(), any(), any(), any(), any(), anyList())).thenReturn("userId");

        mockMvc.perform(post("/users/register")
                .content("{	\"firstName\": \"Jack\", \"lastName\": \"Black\", \"birthDate\":\"2000-04-03\"," +
                        " \"password\": \"passw0rd\", \"email\": \"tom@emaeil.com\", \"phone\": \"004565\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("userId"));
    }
}