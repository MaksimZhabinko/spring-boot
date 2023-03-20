package com.epam.spring.service;

import com.epam.spring.dao.UserRepository;
import com.epam.spring.dto.CarDto;
import com.epam.spring.dto.RoleDto;
import com.epam.spring.dto.StatusDto;
import com.epam.spring.dto.UserDto;
import com.epam.spring.entity.CarEntity;
import com.epam.spring.entity.RoleEntity;
import com.epam.spring.entity.StatusEntity;
import com.epam.spring.entity.UserEntity;
import com.epam.spring.exception.NoSuchEntityException;
import com.epam.spring.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService sut;

    @Test
    void shouldFindAllUsers() {
        UserEntity userEntity = getUserEntity();
        UserDto userDto = getUserDto();
        when(userRepository.findAll()).thenReturn(List.of(userEntity));
        when(userMapper.userEntityToUserDto(userEntity)).thenReturn(userDto);

        List<UserDto> actual = sut.findAllUsers();

        assertThat(actual, is(Arrays.asList(userDto)));
    }

    @Test
    void shouldFindUserByUsername() {
        UserEntity userEntity = getUserEntity();
        UserDto userDto = getUserDto();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userEntity));
        when(userMapper.userEntityToUserDto(userEntity)).thenReturn(userDto);

        UserDto actual = sut.findUserByUsername("username");

        assertThat(actual, is(userDto));
    }

    @Test
    void shouldNotFindUserByUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> sut.findUserByUsername(anyString()));
    }

    @Test
    void shouldFindUserById() {
        UserEntity userEntity = getUserEntity();
        UserDto userDto = getUserDto();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));
        when(userMapper.userEntityToUserDto(userEntity)).thenReturn(userDto);

        UserDto actual = sut.findUserById(1);

        assertThat(actual, is(userDto));
    }

    @Test
    void shouldNotFindUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> sut.findUserById(anyLong()));
    }

    @Test
    void shouldDeleteUserById() {
        UserEntity userEntity = getUserEntity();
        userRepository.delete(userEntity);
        verify(userRepository).delete(userEntity);
    }

    @Test
    void shouldNotDeleteUserById() {
        assertThrows(NoSuchEntityException.class, () -> sut.deleteUserById(anyLong()));
    }

    @Test
    void shouldAddUser() {
        UserDto userDto = getUserDto();
        UserEntity userEntity = getUserEntity();
        when(userMapper.userDtoToUserEntity(userDto)).thenReturn(userEntity);
        when(passwordEncoder.encode(anyString())).thenReturn("1q2w3e");
        userEntity.setPassword("1q2w3e");
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        UserDto expected = getUserDto();
        expected.setPassword("1q2w3e");
        when(userMapper.userEntityToUserDto(userEntity)).thenReturn(expected);

        UserDto actual = sut.addUser(userDto);

        assertThat(actual, is(expected));
    }


    @Test
    void shouldUpdateUser() {
        UserDto userDto = getUserDto();
        userDto.setEmail("new email");
        UserEntity userEntity = getUserEntity();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));
        UserEntity userEntityChanged = getUserEntity();
        userEntityChanged.setEmail("new email");
        when(userMapper.userDtoToUserEntity(userDto)).thenReturn(userEntityChanged);
        when(userRepository.save(userEntityChanged)).thenReturn(userEntityChanged);
        when(userMapper.userEntityToUserDto(userEntityChanged)).thenReturn(userDto);

        UserDto actual = sut.updateUser(userDto);

        assertThat(actual, is(userDto));
    }

    @Test
    void shouldNotUpdateUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> sut.updateUser(getUserDto()));
    }

    private UserEntity getUserEntity() {
        return new UserEntity(1, "username", "firstName", "lastName", "email",
                "pass", RoleEntity.USER, StatusEntity.ACTIVE, List.of(getCarEntity()));
    }

    private UserDto getUserDto() {
        return new UserDto(1, "username", "firstName", "lastName", "email",
                "pass", RoleDto.USER, StatusDto.ACTIVE, List.of(getCarDto()));
    }
    private CarEntity getCarEntity() {
        return new CarEntity(1, "Opel", "6959EX-7", null);
    }

    private CarDto getCarDto() {
        return new CarDto(1, "Opel", "6959EX-7", null);
    }

}