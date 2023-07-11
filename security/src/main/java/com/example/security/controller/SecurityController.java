package com.example.security.controller;

import com.example.security.entity.UserDTO;
import com.example.security.entity.UserEntity;
import com.example.security.entity.UserRole;
import com.example.security.jwt.JwtResponse;
import com.example.security.jwt.JwtUtil;
import com.example.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping
public class SecurityController {

    private final SecurityService securityService;
    private final JwtUtil jwtUtil;

    @Autowired
    public SecurityController(SecurityService securityService, JwtUtil jwtUtil) {
        this.securityService = securityService;
        this.jwtUtil = jwtUtil;
    }

    @RequestMapping("/")
    public String home() {
        return "This is a home page";
    }

    @RequestMapping("/test2")
    public String test2() {
        return securityService.secureMethod2("This is a message");
    }

    @PostMapping("sign")
    public ResponseEntity<?> signup(@RequestBody UserDTO user) {
        UserEntity newUser = securityService.signup(new UserEntity(user.getName(), user.getEmail(), user.getNaverId(), UserRole.valueOf(user.getRoles())));
        if (newUser == null) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        return ResponseEntity.ok(new JwtResponse(jwtUtil.generateToken(user.getNaverId())));
    }

    @GetMapping("me")
    public ResponseEntity<?> me(@RequestAttribute("naverId") String naverId) {
        UserEntity user = securityService.getUserByNaverId(naverId);
        if (user == null) {
            return ResponseEntity.badRequest().body("User does not exist");
        }
        return ResponseEntity.ok(user);
    }
}
