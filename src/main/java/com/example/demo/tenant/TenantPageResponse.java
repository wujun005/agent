package com.example.demo.tenant;


import java.util.List;

public record TenantPageResponse(Integer code, String msg, List<Tenant> records, Long total) {
}
