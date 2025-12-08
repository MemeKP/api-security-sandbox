package com.securitysandbox.api_security_sandbox.service;

import com.securitysandbox.api_security_sandbox.logger.AttackLogger;
import com.securitysandbox.api_security_sandbox.logger.AuditLogger;
import com.securitysandbox.api_security_sandbox.model.Users;
import com.securitysandbox.api_security_sandbox.repo.UserRepo;
import com.securitysandbox.api_security_sandbox.repo.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepo repo;
    @Autowired
    HttpServletRequest request;
    @Autowired
    private AttackLogger attackLogger;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuditLogger auditLogger;

    // If you want to send something to database -> use repo layer
    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return user;
    }

// How do we verify if user are login?
    public String verify(Users user) {

        // Check if user exist?
        Users findUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // Check if this account have been locked?
        if (findUser.isLocked()){
            throw new RuntimeException("Account is locked!");
        }

        try{
            // Start authenticated
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(findUser.getUsername(), user.getPassword()));
            if (authentication.isAuthenticated()) {
                // If pass auth -> reset number of failed attempts
                findUser.setFailedAttempts(0);
                userRepository.save(findUser);
                auditLogger.logSuccessLogin(findUser.getUsername());
                return jwtService.generateToken(findUser.getUsername()); // when success -> generate token so we can use it later.
            }

        } catch (BadCredentialsException e) {
            // wrong password -> update failedAttempts
            int fails = findUser.getFailedAttempts() + 1;
            findUser.setFailedAttempts(fails);

            if (fails > 5) {
                findUser.setLocked(true);
            }
            userRepository.save(findUser);

            attackLogger.logBruteForce(findUser.getUsername(), request.getRemoteAddr());
            throw e;
        }
       throw  new RuntimeException("Authentication Failed!");
    }
}
