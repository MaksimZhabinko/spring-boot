package com.epam.spring.dto;

import lombok.Data;

@Data
public class AuthenticationDto {
    private final String username;
    private final String password;
}
