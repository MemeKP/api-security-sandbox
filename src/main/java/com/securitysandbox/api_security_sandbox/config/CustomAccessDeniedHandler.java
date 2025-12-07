package com.securitysandbox.api_security_sandbox.config;

import com.securitysandbox.api_security_sandbox.logger.AttackLogger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Autowired
    private AttackLogger attackLogger;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException ex) throws IOException {
        attackLogger.logUnauthorizedAccess(request.getRequestURI(), request.getRemoteAddr());

        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }
}
