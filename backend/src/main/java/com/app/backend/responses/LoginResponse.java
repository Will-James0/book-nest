package com.app.backend.responses;

import lombok.*;

import java.util.Set;

@Getter
@Setter
public class LoginResponse {
    private String name;
    private String surname;
    private String email;
    private String token;
    private long expiresIn;
    private Set<String> roles;
    private String redirectUrl;
    private Boolean is_superuser;

    public LoginResponse(String name, String surname, String email, String token, long expiresIn, Set<String> roles, Boolean is_superuser) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.token = token;
        this.expiresIn = expiresIn;
        this.roles = roles;
        this.is_superuser = is_superuser;
    }
}
