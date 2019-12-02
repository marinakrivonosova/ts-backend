package ru.marina.tshop.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.marina.tshop.users.RegisterRequest;
import ru.marina.tshop.users.Role;
import ru.marina.tshop.users.UserService;

import java.util.Arrays;

@RestController
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/admin/register")
    public String register(@RequestBody final RegisterRequest request) {
        return userService.register(
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getBirthDate(),
                request.getPhone(),
                request.getPassword(),
                Arrays.asList(Role.USER, Role.ADMIN));
    }
}
