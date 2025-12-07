package com.securitysandbox.api_security_sandbox.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuditLogger {

    public void logSuccessLogin(String username){
        log.info("[AUDIT] Login success for user: {}",  username);
    }

    public void logAccess(String username, String endpoint){
        log.info("[AUDIT] Access for user: {}, endpoint: {}",  username, endpoint);
    }

    public void logRegister(String username){
        log.info("[AUDIT] Register for user: {}",  username);
    }
}
