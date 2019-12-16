package ru.marina.tshop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Collections;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/login")
    public Collection<Role> login(@RequestBody final LoginRequest request, final HttpServletResponse response) {
        final var loginResult = userService.login(request.getLogin(), request.getPassword());
        final Cookie cookie = new Cookie("token", loginResult.getFirst());
        cookie.setPath("/app");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return loginResult.getSecond();
    }

    @PostMapping("/users/register")
    public void register(@RequestBody final RegisterRequest request, final HttpServletResponse response) {
        userService.register(
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getBirthDate(),
                request.getPhone(),
                request.getPassword(),
                Collections.singletonList(Role.USER));
        final String token = userService.login(request.getEmail(), request.getPassword()).getFirst();
        final Cookie cookie = new Cookie("token", token);
        cookie.setPath("/app");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable("userId") final String userId) {
        return userService.getUser(userId);
    }
}