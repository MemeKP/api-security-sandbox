package com.securitysandbox.api_security_sandbox.logger;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuditLogger {
    private static final Logger auditLog = LoggerFactory.getLogger("AUDIT");

    public void logSuccessLogin(String username){
        auditLog.info("[AUDIT] Login success for user: {}",  username);
    }

    public void logAccess(String username, String endpoint){
        auditLog.info("[AUDIT] Access for user: {}, endpoint: {}",  username, endpoint);
    }

    public void logRegister(String username){
        auditLog.info("[AUDIT] Register for user: {}",  username);
    }
}
