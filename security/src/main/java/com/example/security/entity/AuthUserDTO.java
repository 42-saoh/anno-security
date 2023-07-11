package com.example.security.entity;

public class AuthUserDTO {
    private String name;
    private String email;
    private String naverId;
    private String token;
    private boolean isNew;

    public AuthUserDTO() {
    }

    public AuthUserDTO(String name, String email, String naverId, String token, boolean isNew) {
        this.name = name;
        this.email = email;
        this.naverId = naverId;
        this.token = token;
        this.isNew = isNew;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}
