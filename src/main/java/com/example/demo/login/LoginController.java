package com.example.demo.login;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    private final LoginService loginService;
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public LoginResponse login(LoginRequest loginRequest) {
        loginService.login(loginRequest.tenantId(), loginRequest.account(), loginRequest.password());
        return new LoginResponse(200, "success");
    }
}
