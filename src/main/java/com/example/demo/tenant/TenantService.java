package com.example.demo.tenant;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {
    private final TenantMapper tenantMapper;

    public TenantService(TenantMapper tenantMapper) {
        this.tenantMapper = tenantMapper;
    }

    public TenantPageResponse findAll() {
        List<Tenant> records = tenantMapper.list();
        return new TenantPageResponse(200, "success", records, (long) records.size());
    }

    public TenantPageResponse findPage(TenantQuery query) {
        return new TenantPageResponse(200, "success", tenantMapper.getPage(query), tenantMapper.count(query));
    }

    public TenantResponse insert(Tenant tenant) {
        tenantMapper.insert(tenant);
        return new TenantResponse(200, "success");
    }

    public TenantResponse update(Tenant tenant) {
        tenantMapper.update(tenant);
        return new TenantResponse(200, "success");
    }

    public TenantResponse delete(Long id) {
        tenantMapper.delete(id);
        return new TenantResponse(200, "success");
    }
}
