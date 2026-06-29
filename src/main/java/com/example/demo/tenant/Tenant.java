package com.example.demo.tenant;

import java.time.LocalDateTime;

public class Tenant {
    private Integer id;
    private String tenantCode;
    private String tenantName;
    private String phone;
    private String email;
    private LocalDateTime validStartTime;
    private LocalDateTime validEndTime;
    private String remark;
    private Integer status;
    public Tenant() {}
    public Tenant(Integer id, String tenantCode, String tenantName, String phone, String email, LocalDateTime validStartTime, LocalDateTime validEndTime, String remark, Integer status) {
        this.id = id;
        this.email = email;
        this.tenantCode = tenantCode;
        this.tenantName = tenantName;
        this.phone = phone;
        this.validStartTime = validStartTime;
        this.validEndTime = validEndTime;
        this.remark = remark;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public LocalDateTime getValidStartTime() {
        return validStartTime;
    }

    public void setValidStartTime(LocalDateTime validStartTime) {
        this.validStartTime = validStartTime;
    }

    public LocalDateTime getValidEndTime() {
        return validEndTime;
    }

    public void setValidEndTime(LocalDateTime validEndTime) {
        this.validEndTime = validEndTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }
}
