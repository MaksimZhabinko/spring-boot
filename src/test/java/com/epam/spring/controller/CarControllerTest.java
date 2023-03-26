package com.epam.spring.controller;

import com.epam.spring.dto.CarDto;
import com.epam.spring.dto.RoleDto;
import com.epam.spring.dto.StatusDto;
import com.epam.spring.dto.UserDto;
import com.epam.spring.entity.CarEntity;
import com.epam.spring.entity.RoleEntity;
import com.epam.spring.entity.StatusEntity;
import com.epam.spring.entity.UserEntity;
import com.epam.spring.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CarController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {CarController.class, WebSecurityConfigurer.class})
class CarControllerTest {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CarService carService;

    @WithMockUser(value = "aNti")
    @Test
    @SneakyThrows
    void shouldFindAllCars() {
        CarDto carDto = getCarDto();
        when(carService.findAllCars()).thenReturn(List.of(carDto));

        mockMvc.perform(get("/car/allCars"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(carDto))));
    }

    @WithMockUser(value = "aNti")
    @Test
    @SneakyThrows
    void shouldFindCarById() {
        CarDto carDto = getCarDto();
        when(carService.findCarById(anyLong())).thenReturn(carDto);

        mockMvc.perform(get("/car/carId/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(carDto)));
    }

    @WithMockUser(value = "aNti")
    @Test
    @SneakyThrows
    void shouldFindCarByBrand() {
        CarDto carDto = getCarDto();
        when(carService.findCarByBrand(anyString())).thenReturn(carDto);

        mockMvc.perform(get("/car/carBrand/{brand}", "Opel"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(carDto)));
    }

    @WithMockUser(value = "aNti")
    @Test
    @SneakyThrows
    void shouldFindCarByCarNumber() {
        CarDto carDto = getCarDto();
        when(carService.findCarByCarNumber(anyString())).thenReturn(carDto);

        mockMvc.perform(get("/car/carNumber/{carNumber}", "Opel"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(carDto)));
    }

    @WithMockUser(value = "aNti")
    @Test
    @SneakyThrows
    void shouldAddCar() {
        CarDto carDto = getCarDto();
        when(carService.addCar(carDto, 1)).thenReturn(carDto);

        mockMvc.perform(post("/car/{userId}", 1)
                .content(objectMapper.writeValueAsString(carDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(carDto)));
    }

    @WithMockUser(value = "aNti")
    @Test
    @SneakyThrows
    void shouldUpdateCar() {
        CarDto carDto = getCarDto();
        when(carService.updateCar(carDto, 1)).thenReturn(carDto);

        mockMvc.perform(patch("/car/{userId}", 1)
                .content(objectMapper.writeValueAsString(carDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(carDto)));
    }

    @WithMockUser(value = "aNti")
    @Test
    @SneakyThrows
    void shouldDeleteCarById() {
        carService.deleteCarById(1);

        mockMvc.perform(delete("/car/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    private CarDto getCarDto() {
        return new CarDto(1, "Opel", "6959EX-7", getUserDto());
    }

    private UserDto getUserDto() {
        return new UserDto(1, "username", "firstName", "lastName", "email",
                "pass", RoleDto.USER, StatusDto.ACTIVE, null);
    }
}