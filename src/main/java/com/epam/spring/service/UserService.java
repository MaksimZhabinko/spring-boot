package com.epam.spring.service;

import com.epam.spring.dao.UserRepository;
import com.epam.spring.dto.UserDto;
import com.epam.spring.entity.UserEntity;
import com.epam.spring.exception.NoSuchEntityException;
import com.epam.spring.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDto> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userEntityToUserDto)
                .toList();
    }

    public UserDto findUserByUsername(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        isPresentUser(user);
        return userMapper.userEntityToUserDto(user.get());
    }

    public UserDto findUserById(long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        isPresentUser(user);
        return userMapper.userEntityToUserDto(user.get());
    }

    public void deleteUserById(long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        isPresentUser(user);
        userRepository.delete(user.get());
    }

    public UserDto addUser(UserDto userDto) {
        UserEntity user = userMapper.userDtoToUserEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity savedUser = userRepository.save(user);
        return userMapper.userEntityToUserDto(savedUser);
    }

    public UserDto updateUser(UserDto userDto) {
        Optional<UserEntity> user = userRepository.findById(userDto.getId());
        isPresentUser(user);
        UserEntity userEntity = userMapper.userDtoToUserEntity(userDto);
        UserEntity savedUser = userRepository.save(userEntity);
        return userMapper.userEntityToUserDto(savedUser);
    }

    private void isPresentUser(Optional<UserEntity> userEntity) {
        if (!userEntity.isPresent()) {
            throw new NoSuchEntityException("User is not found.");
        }
    }
}
