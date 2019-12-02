package ru.marina.tshop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void addUser(final User user) {
        final String query = "INSERT INTO users (id, firstname, lastname, birthdate, email, hashed_password, phone)" +
                " VALUES (:id, :firstname, :lastname, :birthdate, :email, :hashedPassword, :phone)";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("firstname", user.getFirstName())
                .addValue("lastname", user.getLastName())
                .addValue("birthdate", user.getBirthDate())
                .addValue("email", user.getEmail())
                .addValue("hashedPassword", user.getHashedPassword())
                .addValue("phone", user.getPhone());
        namedParameterJdbcTemplate.update(query, sqlParameterSource, new GeneratedKeyHolder());
    }

    public Optional<User> findByEmail(final String email) {
        try {
            final String query = "SELECT * FROM users WHERE email = :email";
            final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("email", email);
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, BeanPropertyRowMapper.newInstance(User.class)));
        } catch (final DataAccessException ex) {
            return Optional.empty();
        }
    }

    public User getUser(final String userId) {
        final String query = "SELECT * FROM users WHERE id = :userId";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("userId", userId);
        return namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, BeanPropertyRowMapper.newInstance(User.class));
    }

    public List<Role> getRoles(final String userId) {
        final String query = "SELECT role FROM user_roles WHERE user_id = :userId";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("userId", userId);
        return namedParameterJdbcTemplate.query(query, sqlParameterSource, SingleColumnRowMapper.newInstance(Role.class));
    }

    public void addUserRole(final String id, final String userId, Role role) {
        final String query = "INSERT INTO user_roles (id, user_id, role)" +
                " VALUES (:id, :userId, :role)";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("userId", userId)
                .addValue("role", role.name());
        namedParameterJdbcTemplate.update(query, sqlParameterSource, new GeneratedKeyHolder());
    }
}