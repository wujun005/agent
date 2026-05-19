package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "openai.api-key=test-key")
class Demo1ApplicationTests {

    @Test
    void contextLoads() {
    }

}
