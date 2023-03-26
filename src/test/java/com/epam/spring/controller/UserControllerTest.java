package com.epam.spring.controller;

import com.epam.spring.Application;
import com.epam.spring.dto.CarDto;
import com.epam.spring.dto.RoleDto;
import com.epam.spring.dto.StatusDto;
import com.epam.spring.dto.UserDto;
import com.epam.spring.entity.CarEntity;
import com.epam.spring.entity.RoleEntity;
import com.epam.spring.entity.StatusEntity;
import com.epam.spring.entity.UserEntity;
import com.epam.spring.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {UserController.class, WebSecurityConfigurer.class})
class UserControllerTest {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @WithMockUser(value = "aNti")
    @Test
    @SneakyThrows
    void shouldFindAllUsers() {
        UserDto userDto = getUserDto();
        when(userService.findAllUsers()).thenReturn(List.of(userDto));

        mockMvc.perform(get("/user/allUsers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(userDto))));
    }

    @WithMockUser(value = "aNti")
    @Test
    @SneakyThrows
    void shouldFindUserByUsername() {
        UserDto userDto = getUserDto();
        when(userService.findUserByUsername(anyString())).thenReturn(userDto);

        ResultActions result = mockMvc.perform(get("/user/username/{username}", "aNti"))
                .andDo(print())
                .andExpect(status().isOk());

        verifyJson(result);
    }

    @WithMockUser(value = "aNti")
    @Test
    @SneakyThrows
    void shouldFindUserById() {
        UserDto userDto = getUserDto();
        when(userService.findUserById(anyLong())).thenReturn(userDto);

        ResultActions result = mockMvc.perform(get("/user/id/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk());

        verifyJson(result);
    }

    @WithMockUser(value = "aNti")
    @Test
    @SneakyThrows
    void shouldDeleteUserById() {
        userService.deleteUserById(1);

        mockMvc.perform(delete("/user/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(value = "aNti")
    @Test
    @SneakyThrows
    void shouldAddUser() {
        UserDto userDto = getUserDto();
        when(userService.addUser(userDto)).thenReturn(userDto);

        ResultActions result = mockMvc.perform(post("/user")
                        .content(objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verifyJson(result);
    }

    @WithMockUser(value = "aNti")
    @Test
    @SneakyThrows
    void shouldUpdateUser() {
        UserDto userDto = getUserDto();
        when(userService.updateUser(userDto)).thenReturn(userDto);

        ResultActions result = mockMvc.perform(patch("/user")
                        .content(objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verifyJson(result);
    }

    private UserDto getUserDto() {
        return new UserDto(1, "aNti", "Maxim", "Zhabinko", "maxim_zhabinko@mail.ru",
                "$2a$10$RuEK/jYy4lDSFXEtPVXNL.oTwAAF.vJigh0evEzXChnFl3vmI46T6", RoleDto.ADMIN, StatusDto.ACTIVE, null);
    }

    private void verifyJson(ResultActions actions) throws Exception {
        actions
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.username", Matchers.is("aNti")))
                .andExpect(jsonPath("$.firstName", Matchers.is("Maxim")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Zhabinko")))
                .andExpect(jsonPath("$.email", Matchers.is("maxim_zhabinko@mail.ru")))
                .andExpect(jsonPath("$.password", Matchers.is("$2a$10$RuEK/jYy4lDSFXEtPVXNL.oTwAAF.vJigh0evEzXChnFl3vmI46T6")))
                .andExpect(jsonPath("$.role", Matchers.is(RoleDto.ADMIN.name())))
                .andExpect(jsonPath("$.status", Matchers.is(StatusDto.ACTIVE.name())))
                .andExpect(jsonPath("$.cars", Matchers.nullValue()));
    }
}