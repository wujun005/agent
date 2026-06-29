package com.example.demo.login;

import org.apache.ibatis.annotations.Param;

public record UserInfoResponse(@Param("useName") String userName, @Param("userId") Integer userId, @Param("tenantId") Integer tenantId) {
}
