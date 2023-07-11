package com.example.security.repository;

import com.example.security.entity.UserEntity;

public interface UserRepository {
    UserEntity save(UserEntity userEntity);
    UserEntity findById(int id);
    UserEntity findByUsername(String username);
}
