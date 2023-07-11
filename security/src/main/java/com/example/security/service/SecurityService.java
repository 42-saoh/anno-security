package com.example.security.service;

import com.example.security.annotation.SecurityOneAnnotation;
import com.example.security.annotation.SecurityTwoAnnotation;
import com.example.security.entity.UserEntity;
import com.example.security.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

public class SecurityService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public SecurityService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @SecurityOneAnnotation("This is a value")
    public void secureMethod() {
        System.out.println("This is a test 3");
    }

    @SecurityTwoAnnotation
    public String secureMethod2(String message) {
        return message;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(userEntity.getUsername(), userEntity.getPassword(), Collections.singletonList(userEntity.getRoles()));
    }

    public UserEntity signup(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
