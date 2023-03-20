package com.epam.spring.mapper;

import com.epam.spring.dto.CarDto;
import com.epam.spring.dto.RoleDto;
import com.epam.spring.dto.StatusDto;
import com.epam.spring.dto.UserDto;
import com.epam.spring.entity.CarEntity;
import com.epam.spring.entity.RoleEntity;
import com.epam.spring.entity.StatusEntity;
import com.epam.spring.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface CarMapper {
    @Mapping(source = "user", target = "user", qualifiedByName = "getUserDto")
    CarDto carEntityToCarDto(CarEntity carEntity);
    @Mapping(source = "user", target = "user", qualifiedByName = "getUserEntity")
    CarEntity carDtoToCarEntity(CarDto carDto);

    @Named("getUserDto")
    static UserDto getUserDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId(userEntity.getId());
        userDto.setUsername(userEntity.getUsername());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        userDto.setRole(roleEntityToRoleDto(userEntity.getRole()));
        userDto.setStatus(statusEntityToStatusDto(userEntity.getStatus()));

        return userDto;
    }

    @Named("getUserEntity")
    static UserEntity getUserEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId(userDto.getId());
        userEntity.setUsername(userDto.getUsername());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setRole(roleDtoToRoleEntity(userDto.getRole()));
        userEntity.setStatus(statusDtoToStatusEntity(userDto.getStatus()));

        return userEntity;
    }

    static RoleDto roleEntityToRoleDto(RoleEntity roleEntity) {
        if (roleEntity == null) {
            return null;
        }

        RoleDto roleDto;

        switch (roleEntity) {
            case ADMIN: roleDto = RoleDto.ADMIN;
                break;
            case USER: roleDto = RoleDto.USER;
                break;
            default: throw new IllegalArgumentException("Unexpected enum constant: " + roleEntity);
        }

        return roleDto;
    }

    static StatusDto statusEntityToStatusDto(StatusEntity statusEntity) {
        if (statusEntity == null) {
            return null;
        }

        StatusDto statusDto;

        switch (statusEntity) {
            case ACTIVE: statusDto = StatusDto.ACTIVE;
                break;
            case DELETED: statusDto = StatusDto.DELETED;
                break;
            default: throw new IllegalArgumentException("Unexpected enum constant: " + statusEntity);
        }

        return statusDto;
    }

    static RoleEntity roleDtoToRoleEntity(RoleDto roleDto) {
        if (roleDto == null) {
            return null;
        }

        RoleEntity roleEntity;

        switch (roleDto) {
            case ADMIN: roleEntity = RoleEntity.ADMIN;
                break;
            case USER: roleEntity = RoleEntity.USER;
                break;
            default: throw new IllegalArgumentException("Unexpected enum constant: " + roleDto);
        }

        return roleEntity;
    }

    static StatusEntity statusDtoToStatusEntity(StatusDto statusDto) {
        if (statusDto == null) {
            return null;
        }

        StatusEntity statusEntity;

        switch (statusDto) {
            case ACTIVE: statusEntity = StatusEntity.ACTIVE;
                break;
            case DELETED: statusEntity = StatusEntity.DELETED;
                break;
            default: throw new IllegalArgumentException("Unexpected enum constant: " + statusDto);
        }

        return statusEntity;
    }
}
