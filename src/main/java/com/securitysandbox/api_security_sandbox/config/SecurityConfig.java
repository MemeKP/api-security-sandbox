package com.securitysandbox.api_security_sandbox.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // เพื่อเปิดใช้งาน preAuthorize สำหรับเช็ค role ใน admin controller
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private AttackDetectionFilter attackDetectionFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(customizer -> customizer.disable()) // no csrf
                .authorizeHttpRequests(request -> request
                        .requestMatchers("register", "login")// /register กับ /login จะไม่ต้อง auth ก่อน
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN") // /admin ต้องเป็น role admin จริงถึงจะดูได้
                        .anyRequest().authenticated()) // every req must log in first.
                .httpBasic(Customizer.withDefaults()) // return sessionID
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(attackDetectionFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter, AttackDetectionFilter.class) // เป็นการบอกว่าก่อนที่จะไป attackDetection ให้ทำ jwtfilter ก่อน
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // this will give the object

    }
}


