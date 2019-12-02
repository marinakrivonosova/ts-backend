package ru.marina.tshop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.marina.tshop.security.JwtTokenProvider;
import ru.marina.tshop.utils.IdGenerator;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private final IdGenerator idGenerator;
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserService(final IdGenerator idGenerator, final UserDao userDao,
                       final PasswordEncoder passwordEncoder, final JwtTokenProvider jwtTokenProvider) {
        this.idGenerator = idGenerator;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public String register(final String email,
                           final String firstName,
                           final String lastName,
                           final LocalDate birthDate,
                           final String phone,
                           final String password,
                           final List<Role> roles) {
        final String userId = idGenerator.generateId();

        if (userDao.findByEmail(email).isPresent()) {
            throw new RuntimeException("User with this login is already exist");
        }
        userDao.addUser(new User(
                userId,
                firstName,
                lastName,
                birthDate,
                email,
                passwordEncoder.encode(password),
                phone
        ));
        for (final Role role : roles) {
            userDao.addUserRole(idGenerator.generateId(), userId, role);
        }
        return userId;
    }

    @Transactional(readOnly = true)
    public String login(final String email, final String password) {
        final User user = userDao.findByEmail(email).orElseThrow(() -> new RuntimeException("Invalid username/password supplied"));

        if (passwordEncoder.matches(password, user.getHashedPassword())) {
            return jwtTokenProvider.createToken(user.getId(), userDao.getRoles(user.getId()));
        } else {
            throw new RuntimeException("Invalid username/password supplied");
        }
    }
}
