package com.example.demo.product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * 使用 JDBC 直接操作 product 表。
 */
@Repository
public class JdbcProductRepository implements ProductRepository {

    private final String jdbcUrl;
    private final String username;
    private final String password;

    public JdbcProductRepository(
            @Value("${spring.datasource.url}") String jdbcUrl,
            @Value("${spring.datasource.username:}") String username,
            @Value("${spring.datasource.password:}") String password
    ) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Product> findAll() {
        String sql = """
                SELECT id, product_name, description, price, stock, status, created_at, updated_at
                FROM product
                ORDER BY id
                """;

        List<Product> products = new ArrayList<>();
        try (
                Connection connection = openConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                products.add(mapRow(resultSet));
            }
            return products;
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to query products", ex);
        }
    }

    @Override
    public List<Product> findByProductNameContaining(String productName) {
        String sql = """
                SELECT id, product_name, description, price, stock, status, created_at, updated_at
                FROM product
                WHERE product_name LIKE ?
                ORDER BY id
                """;

        List<Product> products = new ArrayList<>();
        try (
                Connection connection = openConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, "%" + productName + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(mapRow(resultSet));
                }
            }

            return products;
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to query products by product name", ex);
        }
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = """
                SELECT id, product_name, description, price, stock, status, created_at, updated_at
                FROM product
                WHERE id = ?
                """;

        try (
                Connection connection = openConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                return Optional.of(mapRow(resultSet));
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to query product by id", ex);
        }
    }

    @Override
    public Product save(Product product) {
        String sql = """
                INSERT INTO product (product_name, description, price, stock, status)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = openConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, product.productName());
            statement.setString(2, product.description());
            statement.setBigDecimal(3, product.price());
            statement.setInt(4, product.stock());
            statement.setInt(5, product.status());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new IllegalStateException("Failed to read generated product id");
                }

                long id = generatedKeys.getLong(1);
                return findById(id).orElseThrow(
                        () -> new IllegalStateException("Failed to reload saved product: " + id)
                );
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to save product", ex);
        }
    }

    @Override
    public boolean update(Product product) {
        String sql = """
                UPDATE product
                SET product_name = ?, description = ?, price = ?, stock = ?, status = ?
                WHERE id = ?
                """;

        try (
                Connection connection = openConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, product.productName());
            statement.setString(2, product.description());
            statement.setBigDecimal(3, product.price());
            statement.setInt(4, product.stock());
            statement.setInt(5, product.status());
            statement.setLong(6, product.id());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to update product", ex);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";

        try (
                Connection connection = openConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to delete product", ex);
        }
    }

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    private Product mapRow(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getLong("id"),
                resultSet.getString("product_name"),
                resultSet.getString("description"),
                resultSet.getBigDecimal("price"),
                resultSet.getInt("stock"),
                resultSet.getInt("status"),
                toLocalDateTime(resultSet.getTimestamp("created_at")),
                toLocalDateTime(resultSet.getTimestamp("updated_at"))
        );
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
