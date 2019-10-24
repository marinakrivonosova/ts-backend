package orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public OrderDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void addOrder(final Order order) {
        final String query = "INSERT INTO orders (id, user_id, address, payment_method_id, payment_status_id, delivery_method_id, order_status_id)" +
                " VALUES (:id, :userId, :address, :paymentMethodId, :paymentStatusId, :deliveryMethodId, :orderStatusId)";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", order.getId())
                .addValue("userId", order.getUserId())
                .addValue("address", order.getAddress())
                .addValue("orderStatusId", order.getOrderStatusId())
                .addValue("deliveryMethodId", order.getDeliveryMethodId())
                .addValue("paymentMethodId", order.getPaymentMethodId())
                .addValue("paymentStatusId", order.getPaymentStatusId());
        namedParameterJdbcTemplate.update(query, sqlParameterSource, new GeneratedKeyHolder());
    }

    public List<PaymentMethod> getPaymentMethods() {
        final String query = "SELECT * FROM payment_methods";
        return namedParameterJdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(PaymentMethod.class));
    }

    public List<Order> listOrdersByStatus(final String orderStatusId) {
        final String query = "SELECT *" +
                " FROM order_statuses" +
                " INNER JOIN orders" +
                " ON orders.order_status_id = order_statuses.id" +
                " WHERE order_status_id = :orderStatusId";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("orderStatusId", orderStatusId);
        return namedParameterJdbcTemplate.query(query,
                sqlParameterSource, BeanPropertyRowMapper.newInstance(Order.class));
    }

    public void updateOrder(final Order order) {
        final String query = "UPDATE orders" +
                " SET user_id = :userId, address = :address, payment_method_id = :paymentMethodId," +
                " payment_status_id = :paymentStatusId, delivery_method_id = :deliveryMethodId, order_status_id = :orderStatusId" +
                " WHERE id = :id";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", order.getId())
                .addValue("userId", order.getUserId())
                .addValue("address", order.getAddress())
                .addValue("orderStatusId", order.getOrderStatusId())
                .addValue("deliveryMethodId", order.getDeliveryMethodId())
                .addValue("paymentMethodId", order.getPaymentMethodId())
                .addValue("paymentStatusId", order.getPaymentStatusId());
        namedParameterJdbcTemplate.update(query, sqlParameterSource);
    }

    public List<Order> getAllOrders() {
        final String query = "SELECT * FROM orders";
        return namedParameterJdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Order.class));
    }

    public Order getOrder(final String orderId) {
        final String query = "SELECT * FROM orders WHERE id = :orderId";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("orderId", orderId);
        return namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, BeanPropertyRowMapper.newInstance(Order.class));
    }
}