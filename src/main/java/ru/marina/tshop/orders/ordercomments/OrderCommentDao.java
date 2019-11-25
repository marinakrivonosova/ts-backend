package ru.marina.tshop.orders.ordercomments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.marina.tshop.orders.Order;

@Repository
public class OrderCommentDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public OrderCommentDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void addOrderComment(final OrderComment orderComment) {
        final String query = "INSERT INTO order_comments (id, order_id, comment) VALUES (:id, :orderId, :comment)";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", orderComment.getId())
                .addValue("orderId", orderComment.getOrderId())
                .addValue("comment", orderComment.getComment());
        namedParameterJdbcTemplate.update(query, sqlParameterSource, new GeneratedKeyHolder());
    }

    public OrderComment getOrderComment(final String id) {
        final String query = "SELECT * FROM order_comments WHERE id = :id";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, BeanPropertyRowMapper.newInstance(OrderComment.class));
    }
}
