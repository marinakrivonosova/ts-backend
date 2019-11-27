package ru.marina.tshop.orders.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    public DeliveryMethodDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Cacheable("deliveryMethodsAll")
    public List<DeliveryMethod> listDeliveryMethods() {
        final String query = "SELECT * FROM delivery_methods";
        return namedParameterJdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(DeliveryMethod.class));
    }

    @Cacheable("deliveryMethods")
    public DeliveryMethod getDeliveryMethod(final String deliveryMethodId) {
        final String query = "SELECT * FROM delivery_methods WHERE id= :deliveryMethodId";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("deliveryMethodId", deliveryMethodId);
        return namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, BeanPropertyRowMapper.newInstance(DeliveryMethod.class));
    }
}
