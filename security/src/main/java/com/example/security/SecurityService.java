package com.example.security;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    @SecurityOneAnnotation("This is a value")
    public void secureMethod() {
        System.out.println("This is a test 3");
    }

    @SecurityTwoAnnotation
    public String secureMethod2(String message) {
        return message;
    }
}
