package com.epam.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private RoleDto role;
    private StatusDto status;
    private List<CarDto> cars;
}
