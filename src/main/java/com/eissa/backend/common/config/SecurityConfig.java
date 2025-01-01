package com.eissa.backend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**") // Match all requests
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**").permitAll() // Allow public access to /auth endpoints
                        .anyRequest().authenticated() // Require authentication for other endpoints
                )
                .csrf(csrf -> csrf.disable()); // Disable CSRF for simplicity (not recommended for production)

        return http.build();
    }
}
