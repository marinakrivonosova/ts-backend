package ru.marina.tshop.orders.paymentstatuses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentStatusDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public PaymentStatusDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    @Cacheable("paymentStatusesAll")
    public List<PaymentStatus> listPaymentStatuses() {
        final String query = "SELECT * FROM payment_statuses";
        return namedParameterJdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(PaymentStatus.class));
    }
    @Cacheable("paymentStatuses")
    public PaymentStatus getPaymentStatus(final String paymentStatusId) {
        final String query = "SELECT * FROM payment_statuses WHERE id = :paymentStatusId";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("paymentStatusId", paymentStatusId);
        return namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, BeanPropertyRowMapper.newInstance(PaymentStatus.class));
    }
}