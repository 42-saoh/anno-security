package com.example.security.entity;

import javax.persistence.*;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String name;
    private String email;
    private String naverId;
    private UserRole roles;

    public UserEntity() {
    }

    public UserEntity(String name, String email, String naverId, UserRole roles) {
        this.name = name;
        this.email = email;
        this.naverId = naverId;
        this.roles = roles;
    }

    public UserEntity(int id, String name, String email, String naverId, UserRole roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.naverId = naverId;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNaverId() {
        return naverId;
    }

    public void setNaverId(String naverId) {
        this.naverId = naverId;
    }

    public UserRole getRoles() {
        return roles;
    }

    public void setRoles(UserRole roles) {
        this.roles = roles;
    }
}