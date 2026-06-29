package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 应用启动入口。
 */
@SpringBootApplication
public class Demo1Application {

    /**
     * 启动整个 Spring 应用上下文。
     */
    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }

}
