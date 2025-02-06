package com.app.backend.dto;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String name;
    private String surname;
    private String email;
    private String password;
}
