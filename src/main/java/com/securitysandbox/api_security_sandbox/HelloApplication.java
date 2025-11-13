package com.securitysandbox.api_security_sandbox;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloApplication {
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World";
    }
}
