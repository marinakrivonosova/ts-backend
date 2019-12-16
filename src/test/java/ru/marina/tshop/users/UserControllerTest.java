package ru.marina.tshop.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.marina.tshop.admin.AdminController;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {
    private MockMvc mockMvc;
    private MockMvc mockMvcAdmin;
    @MockBean
    private UserService userService = mock(UserService.class);

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
        mockMvcAdmin = MockMvcBuilders.standaloneSetup(new AdminController(userService)).build();
    }

    @Test
    void login() throws Exception {
        when(userService.login(any(), any())).thenReturn(Pair.of("token", Collections.singletonList(Role.USER)));

        mockMvc.perform(post("/users/login")
                .content("{\"login\": \"tom@email.com\", \"password\": \"passw0rd\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(cookie().value("token", "token"))
                .andExpect(content().string("[\"USER\"]"));
    }

    @Test
    void register() throws Exception {
        when(userService.register(any(), any(), any(), any(), any(), any(), anyList())).thenReturn("userId");
        when(userService.login(any(), any())).thenReturn(Pair.of("token", Collections.singletonList(Role.USER)));

        mockMvc.perform(post("/users/register")
                .content("{	\"firstName\": \"Jack\", \"lastName\": \"Black\", \"birthDate\":\"2000-04-03\"," +
                        " \"password\": \"passw0rd\", \"email\": \"tom@emaeil.com\", \"phone\": \"004565\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(cookie().value("token", "token"))
                .andExpect(status().isOk());
    }

    @Test
    void registerAdmin() throws Exception {
        mockMvcAdmin.perform(post("/admin/register")
                .content("{	\"firstName\": \"Jack\", \"lastName\": \"Black\", \"birthDate\":\"2000-04-03\"," +
                        " \"password\": \"passw0rd\", \"email\": \"tom@emaeil.com\", \"phone\": \"004565\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}