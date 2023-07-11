package com.example.security.service;

import com.example.security.annotation.SecurityOneAnnotation;
import com.example.security.annotation.SecurityTwoAnnotation;
import com.example.security.entity.UserEntity;
import com.example.security.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

public class SecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public UserDetails loadUserByUsername(String naverId) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByNaverId(naverId);
        if (userEntity == null) {
            throw new UsernameNotFoundException(naverId);
        }
        return new User(userEntity.getNaverId(), "", Collections.singletonList(userEntity.getRoles()));
    }

    public UserEntity signup(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity getUserByNaverId(String naverId) {
        return userRepository.findByNaverId(naverId);
    }
}
