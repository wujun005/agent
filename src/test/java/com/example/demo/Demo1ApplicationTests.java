package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 冒烟测试，确认 Spring 应用上下文可以正常启动。
 */
@SpringBootTest(properties = "openai.api-key=test-key")
class Demo1ApplicationTests {

    /**
     * 只要有必需的 Bean 无法创建，这个测试就会失败。
     */
    @Test
    void contextLoads() {
    }

}
