package com.example.demo.tenant;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenant")
public class TenantController {
    private TenantService tenantService;
    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/list")
    public TenantPageResponse list() {
        return tenantService.findAll();
    }

    @GetMapping("/page")
    public TenantPageResponse page(TenantQuery query) {
        return tenantService.findPage(query);
    }

    @PostMapping("/insert")
    public TenantResponse insert(@RequestBody Tenant tenant) {
        return tenantService.insert(tenant);
    }

    @PostMapping("/update")
    public TenantResponse update(@RequestBody Tenant tenant) {
        return tenantService.update(tenant);
    }

    @DeleteMapping("/{id}")
    public TenantResponse delete(
            @PathVariable Long id) {
        return tenantService.delete(id);
    }



}
