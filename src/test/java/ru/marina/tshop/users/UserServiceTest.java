package ru.marina.tshop.users;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.marina.tshop.security.JwtTokenProvider;
import ru.marina.tshop.utils.IdGenerator;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;


public class UserServiceTest {
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
    private final UserDao userDao = mock(UserDao.class);
    private final IdGenerator idGenerator = new IdGenerator();
    private final UserService userService = new UserService(idGenerator, userDao, passwordEncoder, jwtTokenProvider);

    @Test
    void register() {
        when(passwordEncoder.encode(any())).thenReturn("hashed");

        final String userId = userService.register("email", "Tom", "Smith", LocalDate.of(2000, 10, 1),
                "000", "password", Collections.singletonList(Role.USER));

        assertNotNull(userId);

        verify(userDao).addUser(new User(userId, "Tom", "Smith", LocalDate.of(2000, 10, 1),
                "email", "hashed", "000"));
        verify(userDao).addUserRole(any(), eq(userId), eq(Role.USER));
    }

    @Test
    void login() {
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(jwtTokenProvider.createToken(any(), anyList())).thenReturn("token");
        when(userDao.getRoles(any())).thenReturn(Collections.singletonList(Role.USER));
        when(userDao.findByEmail(any())).thenReturn(Optional.of(
                new User("userId", "Tom", "Smith", LocalDate.of(2000, 10, 1),
                        "email", "hashedPassword", "0")));
        final var loginResult = userService.login("email", "password");

        assertNotNull(loginResult);
        assertEquals("token", loginResult.getFirst());
        assertEquals(Collections.singletonList(Role.USER), loginResult.getSecond());
        verify(passwordEncoder).matches("password", "hashedPassword");
        verify(jwtTokenProvider).createToken("userId", Collections.singletonList(Role.USER));
    }
}