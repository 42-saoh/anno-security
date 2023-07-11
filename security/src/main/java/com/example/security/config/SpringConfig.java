package com.example.security.config;

import com.example.security.service.SecurityService;
import com.example.security.repository.UserJPARepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityService securityService(UserJPARepository userJPARepository, BCryptPasswordEncoder passwordEncoder) {
        return new SecurityService(userJPARepository, passwordEncoder);
    }
}
