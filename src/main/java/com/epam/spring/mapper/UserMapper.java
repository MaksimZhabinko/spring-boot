package com.epam.spring.mapper;

import com.epam.spring.dto.CarDto;
import com.epam.spring.dto.UserDto;
import com.epam.spring.entity.CarEntity;
import com.epam.spring.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "cars", source = "cars", qualifiedByName = "getCarDtos")
    UserDto userEntityToUserDto(UserEntity userEntity);
    @Mapping(target = "cars", source = "cars", qualifiedByName = "getCarEntities")
    UserEntity userDtoToUserEntity(UserDto userDto);

    @Named("getCarDtos")
    static List<CarDto> carEntityListToCarDtoList(List<CarEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<CarDto> list1 = new ArrayList<CarDto>( list.size() );
        for ( CarEntity carEntity : list ) {
            list1.add( carEntityToCarDto( carEntity ) );
        }

        return list1;
    }

    @Named("getCarEntities")
    static List<CarEntity> carDtoListToCarEntityList(List<CarDto> list) {
        if ( list == null ) {
            return null;
        }

        List<CarEntity> list1 = new ArrayList<CarEntity>( list.size() );
        for ( CarDto carDto : list ) {
            list1.add( carDtoToCarEntity( carDto ) );
        }

        return list1;
    }

    static CarDto carEntityToCarDto(CarEntity carEntity) {
        if ( carEntity == null ) {
            return null;
        }

        CarDto carDto = new CarDto();

        carDto.setId( carEntity.getId() );
        carDto.setBrand( carEntity.getBrand() );
        carDto.setCarNumber( carEntity.getCarNumber() );

        return carDto;
    }

    static CarEntity carDtoToCarEntity(CarDto carDto) {
        if ( carDto == null ) {
            return null;
        }

        CarEntity carEntity = new CarEntity();

        carEntity.setId( carDto.getId() );
        carEntity.setBrand( carDto.getBrand() );
        carEntity.setCarNumber( carDto.getCarNumber() );

        return carEntity;
    }
}
