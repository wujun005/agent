package com.example.demo.login;

import org.apache.ibatis.annotations.Param;

public record LoginRequest(@Param("account") String account, @Param("password") String password, @Param("tenantId") Integer tenantId) {
}
