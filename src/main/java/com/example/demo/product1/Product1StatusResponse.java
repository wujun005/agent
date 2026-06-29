package com.example.demo.product1;

/**
 * product1 写操作的简单状态响应。
 */
public record Product1StatusResponse(
        int code,
        String msg
) {

    public static Product1StatusResponse success() {
        return new Product1StatusResponse(200, "success");
    }
}
