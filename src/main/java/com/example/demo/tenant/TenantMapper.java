package com.example.demo.tenant;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TenantMapper {
    List<Tenant> list();
    List<Tenant> getPage(@Param("query") TenantQuery query);
    Long count(@Param("query") TenantQuery query);
    Integer insert(@Param("tenant") Tenant tenant);
    Integer update(@Param("update") Tenant tenant);
    Integer delete(@Param("id") Long id);
}
