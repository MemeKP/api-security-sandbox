package com.securitysandbox.api_security_sandbox.repo;

import com.securitysandbox.api_security_sandbox.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);

    Optional<Users> findByUsername(String username);
}
