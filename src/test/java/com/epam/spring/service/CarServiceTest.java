package com.epam.spring.service;

import com.epam.spring.dao.CarRepository;
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
import com.epam.spring.mapper.CarMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
class CarServiceTest {
    @Mock
    private CarRepository carRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CarMapper carMapper;
    @InjectMocks
    private CarService sut;

    @Test
    void shouldFindAllCars() {
        CarEntity carEntity = getCarEntity();
        CarDto carDto = getCarDto();
        when(carRepository.findAll()).thenReturn(Arrays.asList(carEntity));
        when(carMapper.carEntityToCarDto(carEntity)).thenReturn(carDto);

        List<CarDto> actual = sut.findAllCars();

        assertThat(actual, is(Arrays.asList(carDto)));
    }

    @Test
    void shouldFindCarById() {
        CarEntity carEntity = getCarEntity();
        CarDto carDto = getCarDto();
        when(carRepository.findById(1L)).thenReturn(Optional.of(carEntity));
        when(carMapper.carEntityToCarDto(carEntity)).thenReturn(carDto);

        CarDto actual = sut.findCarById(1);

        assertThat(actual, is(carDto));
    }

    @Test
    void shouldNotFindCarById() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> sut.findCarById(anyLong()));
    }

    @Test
    void shouldFindCarByBrand() {
        CarEntity carEntity = getCarEntity();
        CarDto carDto = getCarDto();
        when(carRepository.findByBrand("Opel")).thenReturn(Optional.of(carEntity));
        when(carMapper.carEntityToCarDto(carEntity)).thenReturn(carDto);

        CarDto actual = sut.findCarByBrand("Opel");

        assertThat(actual, is(carDto));
    }

    @Test
    void shouldNotFindCarByBrand() {
        when(carRepository.findByBrand(anyString())).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> sut.findCarByBrand(anyString()));
    }

    @Test
    void shouldFindCarByCarNumber() {
        CarEntity carEntity = getCarEntity();
        CarDto carDto = getCarDto();
        when(carRepository.findByCarNumber("6959EX-7")).thenReturn(Optional.of(carEntity));
        when(carMapper.carEntityToCarDto(carEntity)).thenReturn(carDto);

        CarDto actual = sut.findCarByCarNumber("6959EX-7");

        assertThat(actual, is(carDto));
    }

    @Test
    void shouldNotFindCarByCarNumber() {
        when(carRepository.findByCarNumber(anyString())).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> sut.findCarByCarNumber(anyString()));
    }

    @Test
    void shouldAddCar() {
        CarDto carDto = getCarDto();
        CarEntity carEntity = getCarEntity();
        UserEntity userEntity = getUserEntity();
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(carMapper.carDtoToCarEntity(carDto)).thenReturn(carEntity);
        when(carRepository.save(carEntity)).thenReturn(carEntity);
        when(carMapper.carEntityToCarDto(carEntity)).thenReturn(carDto);

        CarDto actual = sut.addCar(carDto, 1L);

        assertThat(actual, is(carDto));
    }

    @Test
    void shouldNotAddCar() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> sut.addCar(getCarDto(), 4L));
    }

    @Test
    void shouldDeleteCarById() {
        CarEntity carEntity = getCarEntity();
        carRepository.delete(carEntity);
        verify(carRepository).delete(carEntity);
    }

    @Test
    void shouldNotDeleteCarById() {
        assertThrows(NoSuchEntityException.class, () -> sut.deleteCarById(anyLong()));
    }

    @Test
    void shouldUpdateCar() {
        CarDto carDto = getCarDto();
        carDto.setBrand("Mazda");
        CarEntity carEntity = getCarEntity();
        UserEntity userEntity = getUserEntity();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(carEntity));
        CarEntity carEntityChanged= getCarEntity();
        carEntityChanged.setBrand("Mazda");
        when(carMapper.carDtoToCarEntity(carDto)).thenReturn(carEntityChanged);
        when(carRepository.save(carEntityChanged)).thenReturn(carEntityChanged);
        when(carMapper.carEntityToCarDto(carEntityChanged)).thenReturn(carDto);

        CarDto actual = sut.updateCar(carDto, 1L);

        assertThat(actual, is(carDto));
    }

    @Test
    void shouldNotUpdateCar() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> sut.updateCar(getCarDto(), 4L));
    }

    private CarEntity getCarEntity() {
        return new CarEntity(1, "Opel", "6959EX-7", getUserEntity());
    }

    private CarDto getCarDto() {
        return new CarDto(1, "Opel", "6959EX-7", getUserDto());
    }

    private UserEntity getUserEntity() {
        return new UserEntity(1, "username", "firstName", "lastName", "email",
                "pass", RoleEntity.USER, StatusEntity.ACTIVE, null);
    }

    private UserDto getUserDto() {
        return new UserDto(1, "username", "firstName", "lastName", "email",
                "pass", RoleDto.USER, StatusDto.ACTIVE, null);
    }
}