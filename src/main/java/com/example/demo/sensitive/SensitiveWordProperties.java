package com.example.demo.sensitive;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 敏感词存储使用的库名和表名配置。
 */
@Component
@ConfigurationProperties(prefix = "app.sensitive-word")
public class SensitiveWordProperties {

    /** 存放敏感词表的数据库名。 */
    private String schema = "e-commerce";
    /** 优先使用的正式表名，历史表名仍会作为兜底候选。 */
    private String table = "sensitive_word";

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
