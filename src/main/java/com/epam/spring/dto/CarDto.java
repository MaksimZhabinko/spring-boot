package com.epam.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private long id;
    private String brand;
    private String carNumber;
    private UserDto user;
}
