package com.example.demo.login;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final LoginMapper loginMapper;
    public LoginService(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    public RegisterResponse register(String userName, String account, String password, Integer tenantId) {
        loginMapper.register(tenantId, account, userName, password);
        return new RegisterResponse(200, "success");
    }

    public LoginResponse login(Integer tenantId, String account, String password) {
        loginMapper.Login(tenantId, account, password);
        return new LoginResponse(200, "success");
    }
}
