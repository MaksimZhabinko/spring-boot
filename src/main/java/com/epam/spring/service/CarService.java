package com.epam.spring.service;

import com.epam.spring.dao.CarRepository;
import com.epam.spring.dao.UserRepository;
import com.epam.spring.dto.CarDto;
import com.epam.spring.entity.CarEntity;
import com.epam.spring.entity.UserEntity;
import com.epam.spring.exception.NoSuchEntityException;
import com.epam.spring.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private CarRepository carRepository;
    private UserRepository userRepository;
    private CarMapper carMapper;

    @Autowired
    public CarService(CarRepository carRepository, UserRepository userRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.carMapper = carMapper;
    }

    public List<CarDto> findAllCars() {
       return carRepository.findAll()
               .stream()
               .map(carMapper::carEntityToCarDto)
               .toList();
    }

    public CarDto findCarById(long id) {
        Optional<CarEntity> car = carRepository.findById(id);
        isPresentCar(car);
        return carMapper.carEntityToCarDto(car.get());
    }

    public CarDto findCarByBrand(String brand) {
        Optional<CarEntity> car = carRepository.findByBrand(brand);
        isPresentCar(car);
        return carMapper.carEntityToCarDto(car.get());
    }

    public CarDto findCarByCarNumber(String carNumber) {
        Optional<CarEntity> car = carRepository.findByCarNumber(carNumber);
        isPresentCar(car);
        return carMapper.carEntityToCarDto(car.get());
    }

    public CarDto addCar(CarDto carDto, long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        isPresentUser(user);
        CarEntity carEntity = carMapper.carDtoToCarEntity(carDto);
        carEntity.setUser(user.get());
        CarEntity savedCarEntity = carRepository.save(carEntity);
        return carMapper.carEntityToCarDto(savedCarEntity);
    }

    public void deleteCarById(long id) {
        Optional<CarEntity> car = carRepository.findById(id);
        isPresentCar(car);
        carRepository.delete(car.get());
    }

    public CarDto updateCar(CarDto carDto, long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        isPresentUser(user);
        Optional<CarEntity> car = carRepository.findById(carDto.getId());
        isPresentCar(car);
        CarEntity carEntity = carMapper.carDtoToCarEntity(carDto);
        carEntity.setUser(user.get());
        CarEntity savedCarEntity = carRepository.save(carEntity);
        return carMapper.carEntityToCarDto(savedCarEntity);
    }

    private void isPresentCar(Optional<CarEntity> carEntity) {
        if (!carEntity.isPresent()) {
            throw new NoSuchEntityException("Car is not found.");
        }
    }

    private void isPresentUser(Optional<UserEntity> userEntity) {
        if (!userEntity.isPresent()) {
            throw new NoSuchEntityException("User is not found.");
        }
    }
}
