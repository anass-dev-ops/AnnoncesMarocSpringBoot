package com.annoncema.demo.services;

import com.annoncema.demo.dao.AppRoleRepository;
import com.annoncema.demo.dao.AppUserRepository;
import com.annoncema.demo.entities.AppRole;
import com.annoncema.demo.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthenticationServiceImp implements AuthenticationService {
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppRoleRepository appRoleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public AppUser saveUser(AppUser appUser) {
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole saveRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(AppRole appRole, AppUser appUser) {
        appUser.getRoles().add(appRole);
        appUserRepository.save(appUser);
    }

    @Override
    public AppUser getUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public AppRole getRoleByRoleName(String roleName) {
        return appRoleRepository.findByRoleName(roleName);
    }

    @Override
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }
}
