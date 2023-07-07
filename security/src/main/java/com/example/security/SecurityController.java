package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SecurityController {

    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @RequestMapping("/test1")
    public String test() {
        System.out.println("This is a test 1");
        securityService.secureMethod();
        System.out.println("This is a test 5");
        return "This is a test";
    }

    @RequestMapping("/test2")
    public String test2() {
        return securityService.secureMethod2("This is a message");
    }
}
