package com.securitysandbox.api_security_sandbox.repo;

import com.securitysandbox.api_security_sandbox.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users,Long> {
    Users findByUsername(String username);
}
