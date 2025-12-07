package com.securitysandbox.api_security_sandbox.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AttackLogger {
    public void logBruteForce(String username, String ip) {
        log.warn("[ATTACK] Brute force detected. Username: {}, IP: {}", username, ip);
    }

    public void logInvalidJwt(String token, String ip) {
        log.warn("[ATTACK] Invalid JWT detected from IP: {} | Token: {}", ip, token);
    }

    public void logExpiredJwt(String username) {
        log.warn("[ATTACK] Expired JWT used by user: {}", username);
    }

    public void logUnauthorizedAccess(String endpoint, String ip) {
        log.warn("[ATTACK] Unauthorized access attempt to {} from IP {}", endpoint, ip);
    }

    public void logSuspiciousHeader(String ip, String userAgent) {
        log.warn("[ATTACK] Suspicious request header from IP {} | UA {}", ip, userAgent);
    }
}
