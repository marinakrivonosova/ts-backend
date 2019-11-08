package ru.marina.tshop.orders.orderStatuses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

public class OrderStatusDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public OrderStatusDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public String getOrderStatusId(final String orderStatus) {
        final String query = "SELECT id FROM order_statuses WHERE order_status = :orderStatus";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("orderStatus", orderStatus);
        return namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, String.class);
    }

    public OrderStatus getOrderStatus(final String orderStatusId) {
        final String query = "SELECT * FROM order_statuses WHERE id = :orderStatusId";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("orderStatusId", orderStatusId);
        return namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, BeanPropertyRowMapper.newInstance(OrderStatus.class));
    }

    public void addOrderStatus(final OrderStatus orderStatus) {
        final String query = "INSERT INTO order_statuses (id, order_status) VALUES (:id, :orderStatus)";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", orderStatus.getId())
                .addValue("orderStatus", orderStatus.getOrderStatus());
        namedParameterJdbcTemplate.update(query, sqlParameterSource, new GeneratedKeyHolder());
    }
}
