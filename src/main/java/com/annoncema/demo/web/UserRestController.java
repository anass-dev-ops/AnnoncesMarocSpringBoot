package com.annoncema.demo.web;

import com.annoncema.demo.entities.AppRole;
import com.annoncema.demo.entities.AppUser;
import com.annoncema.demo.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class UserRestController {
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping(value = "/users")
    public List<AppUser> appUserList() {
        return authenticationService.getAllUsers();
    }
    @GetMapping(value = "/users/{username}")
    public AppUser getAppUser(@PathVariable String username) {
        return authenticationService.getUserByUsername(username);
    }
    @PostMapping(value = "/register")
    public AppUser saveAppUser(@RequestBody RegisterForm registerForm) {
        if (authenticationService.getUserByUsername(registerForm.getUsername()) != null) {
            throw new RuntimeException("This Username already exist !!");
        }
        if (!registerForm.getPassword().equals(registerForm.getRepassword())) {
            throw new RuntimeException("Password incorrect !!");
        }
        Collection<AppRole> roles = new ArrayList<>();
        roles.add(authenticationService.getRoleByRoleName("USER"));

        AppUser appUser = new AppUser();
        appUser.setUsername(registerForm.getUsername());
        appUser.setPassword(registerForm.getPassword());
        appUser.setRoles(roles);
        authenticationService.saveUser(appUser);
        return appUser;
    }
}
