package ru.marina.tshop.orders.deliverymethods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeliveryMethodDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DeliveryMethodDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<DeliveryMethod> listDeliveryMethods() {
        final String query = "SELECT * FROM delivery_methods";
        return namedParameterJdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(DeliveryMethod.class));
    }

    public String getDeliveryMethod(final String deliveryMethodId) {
        final String query = "SELECT delivery_method FROM delivery_methods WHERE id= :deliveryMethodId";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", deliveryMethodId);
        return namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, String.class);
    }

    public String getDeliveryMethodId(final String deliveryMethod) {
        final String query = "SELECT id FROM delivery_methods WHERE delivery_method = :delivery_method";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource("delivery_method", deliveryMethod);
        return namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, String.class);
    }
}
