package com.annoncema.demo.dao;

import com.annoncema.demo.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, String> {
    public AppRole findByRoleName(String roleName);
}
