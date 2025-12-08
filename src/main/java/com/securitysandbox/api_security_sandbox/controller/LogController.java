package com.securitysandbox.api_security_sandbox.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/admin/logs")
public class LogController {

    @GetMapping("/audit")
    public ResponseEntity<String> getAudit(HttpServletRequest request) throws IOException {
        String logs = Files.readString(Path.of("logs/audit.log"));
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/attack")
    public ResponseEntity<String> getAttack(HttpServletRequest request) throws IOException {
        String logs = Files.readString(Path.of("logs/attack.log"));
        return ResponseEntity.ok(logs);
    }
}
