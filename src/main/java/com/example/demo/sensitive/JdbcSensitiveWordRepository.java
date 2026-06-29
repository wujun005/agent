package com.example.demo.sensitive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * 通过 JDBC 直接从 MySQL 读取已启用的敏感词。
 */
@Repository
public class JdbcSensitiveWordRepository implements SensitiveWordRepository {

    private final String jdbcUrl;
    private final String username;
    private final String password;
    private final SensitiveWordProperties properties;

    public JdbcSensitiveWordRepository(
            @Value("${spring.datasource.url}") String jdbcUrl,
            @Value("${spring.datasource.username:}") String username,
            @Value("${spring.datasource.password:}") String password,
            SensitiveWordProperties properties
    ) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        this.properties = properties;
    }

    /**
     * 从配置的库和实际可用的表中读取全部启用词条。
     */
    @Override
    public List<SensitiveWordRule> findActiveWords() {
        List<SensitiveWordRule> words = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection(jdbcUrl, username, password)
        ) {
            // 这个项目里敏感词表名改动过，所以先解析当前实际存在的表名，再执行查询。
            String tableName = resolveTableName(connection);
            String sql = """
                    SELECT word
                    FROM %s.%s
                    WHERE status = 1
                      AND word IS NOT NULL
                      AND word <> ''
                    ORDER BY id
                    """.formatted(
                    quoteIdentifier(properties.getSchema()),
                    quoteIdentifier(tableName)
            );

            try (
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    words.add(new SensitiveWordRule(resultSet.getString("word")));
                }
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to load sensitive words", ex);
        }

        return words;
    }

    /**
     * 在当前配置和历史表名候选中，找到第一个真实存在的表。
     */
    private String resolveTableName(Connection connection) throws SQLException {
        List<String> candidates = new ArrayList<>();
        if (StringUtils.hasText(properties.getTable())) {
            candidates.add(properties.getTable());
        }
        addCandidate(candidates, "sensitive_word");
        addCandidate(candidates, "sensitive");

        for (String candidate : candidates) {
            if (tableExists(connection, properties.getSchema(), candidate)) {
                return candidate;
            }
        }

        throw new IllegalStateException(
                "No sensitive word table found in schema '%s'".formatted(properties.getSchema())
        );
    }

    /**
     * 通过 information_schema 判断表是否存在，避免直接查询业务表时报错。
     */
    private boolean tableExists(Connection connection, String schema, String tableName) throws SQLException {
        String sql = """
                SELECT 1
                FROM information_schema.tables
                WHERE table_schema = ?
                  AND table_name = ?
                LIMIT 1
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, schema);
            statement.setString(2, tableName);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    /**
     * 保持候选表名不重复，同时保留检查顺序。
     */
    private void addCandidate(List<String> candidates, String candidate) {
        if (!candidates.contains(candidate)) {
            candidates.add(candidate);
        }
    }

    /**
     * 对库名和表名加反引号，避免像 e-commerce 这类名称触发 SQL 语法问题。
     */
    private String quoteIdentifier(String identifier) {
        return "`" + identifier.replace("`", "``") + "`";
    }
}
