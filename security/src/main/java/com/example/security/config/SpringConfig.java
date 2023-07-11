package com.example.security.config;

import com.example.security.jwt.JwtUtil;
import com.example.security.service.CustomOAuth2UserService;
import com.example.security.service.SecurityService;
import com.example.security.repository.UserJPARepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public SecurityService securityService(UserJPARepository userJPARepository) {
        return new SecurityService(userJPARepository);
    }

    @Bean
    public CustomOAuth2UserService customOAuth2UserService(UserJPARepository userJPARepository, JwtUtil jwtUtil) {
        return new CustomOAuth2UserService(userJPARepository, jwtUtil);
    }
}
