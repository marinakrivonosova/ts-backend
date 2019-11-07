package orders.lineitems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LineItemDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public LineItemDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void addLineItem(final LineItem lineItem) {
        final String query = "INSERT INTO line_items (id, order_id, product_id, count, price)" +
                " VALUES (:id, :orderId, :productId, :count, :price)";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", lineItem.getId())
                .addValue("orderId", lineItem.getOrderId())
                .addValue("productId", lineItem.getProductId())
                .addValue("count", lineItem.getCount())
                .addValue("price", lineItem.getPrice());
        namedParameterJdbcTemplate.update(query, sqlParameterSource, new GeneratedKeyHolder());
    }

    public LineItem getLineItem(final String lineItemId) {
        final String query = "SELECT * FROM line_items WHERE id = :lineItemId";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("lineItemId", lineItemId);
        return namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, BeanPropertyRowMapper.newInstance(LineItem.class));
    }

    public List<LineItem> listLineItems(final String orderId) {
        final String query = "SELECT * FROM line_items WHERE order_id = :orderId";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("orderId", orderId);
        return namedParameterJdbcTemplate.query(query, sqlParameterSource, BeanPropertyRowMapper.newInstance(LineItem.class));
    }
}