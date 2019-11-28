package ru.marina.tshop.orders.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentMethodDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public PaymentMethodDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    @Cacheable("paymentMethodsAll")
    public List<PaymentMethod> listPaymentMethods() {
        final String query = "SELECT * FROM payment_methods";
        return namedParameterJdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(PaymentMethod.class));
    }
    @Cacheable("paymentMethods")
    public PaymentMethod getPaymentMethod(final String paymentMethodId) {
        final String query = "SELECT * FROM payment_methods WHERE id = :paymentMethodId";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("paymentMethodId", paymentMethodId);
        return namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, BeanPropertyRowMapper.newInstance(PaymentMethod.class));
    }
}
