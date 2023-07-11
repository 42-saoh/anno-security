package com.example.security.controller;

import com.example.security.entity.UserDTO;
import com.example.security.entity.UserEntity;
import com.example.security.entity.UserRole;
import com.example.security.jwt.JwtResponse;
import com.example.security.jwt.JwtUtil;
import com.example.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping
public class SecurityController {

    private final SecurityService securityService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public SecurityController(SecurityService securityService, JwtUtil jwtUtil,
                              AuthenticationManager authenticationManager) {
        this.securityService = securityService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
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
        UserEntity newUser = securityService.signup(new UserEntity(user.getUsername(), user.getPassword(), UserRole.valueOf(user.getRoles())));
        if (newUser == null) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDTO user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Incorrect username or password");
        }
        return ResponseEntity.ok(new JwtResponse(jwtUtil.generateToken(user.getUsername())));
    }
}
