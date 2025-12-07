package com.securitysandbox.api_security_sandbox.controller;

import com.securitysandbox.api_security_sandbox.model.Users;
import com.securitysandbox.api_security_sandbox.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepo repo;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/unlock/{username}")
    public String unlock(@PathVariable String username){
        Users user = repo.findByUsername(username);
        user.setLocked(false);
        user.setFailedAttempts(0);
        repo.save(user);

        return "Unlocked Account Successfully :)";
    }
}
