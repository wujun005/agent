package com.example.demo.login;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {
    LoginResponse register(@Param("tenantId") Integer tenantId, @Param("userName") String userName, @Param("account") String account, @Param("password") String password);
    LoginResponse Login(@Param("tenantId") Integer tenantId, @Param("account") String account, @Param("password") String password);
}
