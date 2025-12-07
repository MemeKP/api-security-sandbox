package com.securitysandbox.api_security_sandbox.config;

import com.securitysandbox.api_security_sandbox.logger.AttackLogger;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AttackDetectionFilter extends OncePerRequestFilter {
    @Autowired
    private AttackLogger attackLogger;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        if (userAgent == null || userAgent.isBlank()) {
            attackLogger.logSuspiciousHeader(ip, "NO USER AGENT");
        }

        if (userAgent.toLowerCase().contains("sqlmap")
                || userAgent.toLowerCase().contains("python")
                || userAgent.toLowerCase().contains("curl")) {

            attackLogger.logSuspiciousHeader(ip, userAgent);
        }

        filterChain.doFilter(request, response);
    }
}
