package com.example.security.repository;

import com.example.security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPARepository extends UserRepository, JpaRepository<UserEntity, Integer> {
}
