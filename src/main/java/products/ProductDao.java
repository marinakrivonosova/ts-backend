package products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ProductDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Product> filterProducts(final String title, final long offset, final int limit) {
        final String query = "SELECT * FROM products WHERE name = :name" +
                " LIMIT :limit OFFSET :offset";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", title)
                .addValue("offset", offset)
                .addValue("limit", limit);
        return namedParameterJdbcTemplate.query(query, sqlParameterSource, BeanPropertyRowMapper.newInstance(Product.class));
    }

    public void addProduct(final Product product) {
        final String query = "INSERT INTO products (product_id, name, description, price, weight, volume, count, category_id)" +
                " VALUES (:productId, :name, :description, :price, :weight, :volume, :count, :categoryId)";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("productId", product.getProductId())
                .addValue("name", product.getName())
                .addValue("description", product.getDescription())
                .addValue("price", product.getPrice())
                .addValue("weight", product.getWeight())
                .addValue("volume", product.getVolume())
                .addValue("count", product.getCount())
                .addValue("categoryId", product.getCategoryId());
        namedParameterJdbcTemplate.update(query, sqlParameterSource, new GeneratedKeyHolder());
    }

    public Product getProduct(final String productId) {
        final String query = "SELECT * FROM products WHERE product_id = :productId";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("productId", productId);
        return namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, BeanPropertyRowMapper.newInstance(Product.class));
    }

    public void updateProduct(final Product product) {
        final String query = "UPDATE products" +
                " SET name = :name, description = :description, price = :price," +
                " weight = :weight, volume = :volume, count = :count, category_id = :categoryId" +
                " WHERE product_id = :productId";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("productId", product.getProductId())
                .addValue("name", product.getName())
                .addValue("description", product.getDescription())
                .addValue("price", product.getPrice())
                .addValue("weight", product.getWeight())
                .addValue("volume", product.getVolume())
                .addValue("count", product.getCount())
                .addValue("categoryId", product.getCategoryId());
        namedParameterJdbcTemplate.update(query, sqlParameterSource);
    }
}
