package ru.marina.tshop.users;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
@ContextConfiguration("classpath:application-context-test.xml")
@WebAppConfiguration
public class UserDaoTest {
    @Autowired
    private UserDao userDao;


    @Test
    void addUser() {
        final User expectedUser = new User("uId15", "Tom", "Smith", LocalDate.of(2000, 10, 1),
                "em@hotmail.com", "fsdfsdf", "89004456699");
        userDao.addUser(expectedUser);
        assertEquals(expectedUser, userDao.getUser("uId15"));
    }

    @Test
    void addUserRole() {
        userDao.addUserRole("urId", "uId1", Role.USER);
        assertEquals(Collections.singletonList(Role.USER), userDao.getRoles("uId1"));
    }

    @Test
    void findByEmail() {
        final User expectedUser = new User("uId20", "Tom", "Smith", LocalDate.of(2000, 10, 1),
                "emt@hotmail.com", "fsdfsdf", "89004456699");
        userDao.addUser(expectedUser);
        final Optional<User> user = userDao.findByEmail("emt@hotmail.com");
        assertTrue(user.isPresent());
        assertEquals(expectedUser, user.get());
    }
}