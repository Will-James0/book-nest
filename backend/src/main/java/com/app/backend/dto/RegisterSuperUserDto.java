package com.app.backend.dto;

import lombok.*;

@Getter
@Setter
public class RegisterSuperUserDto {
    private String name;
    private String surname;
    private String email;
    private String password;
}
