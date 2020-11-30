package com.annoncema.demo.dao;

import com.annoncema.demo.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    public AppUser findByUsername(String username);
}
