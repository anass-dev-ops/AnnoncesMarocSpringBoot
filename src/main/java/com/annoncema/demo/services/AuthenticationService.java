package com.annoncema.demo.services;

import com.annoncema.demo.entities.AppRole;
import com.annoncema.demo.entities.AppUser;

import java.util.List;

public interface AuthenticationService {
    public AppUser saveUser(AppUser appUser);
    public AppRole saveRole(AppRole appRole);
    public void addRoleToUser(AppRole appRole, AppUser appUser);

    public AppUser getUserByUsername(String username);
    public AppRole getRoleByRoleName(String roleName);

    public List<AppUser> getAllUsers();
}
