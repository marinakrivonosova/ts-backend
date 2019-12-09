package ru.marina.tshop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/login")
    public String login(@RequestBody final LoginRequest request) {
        return userService.login(request.getLogin(), request.getPassword());
    }

    @PostMapping("/users/register")
    public String register(@RequestBody final RegisterRequest request) {
        userService.register(
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getBirthDate(),
                request.getPhone(),
                request.getPassword(),
                Collections.singletonList(Role.USER));
        return userService.login(request.getEmail(), request.getPassword());
    }
}