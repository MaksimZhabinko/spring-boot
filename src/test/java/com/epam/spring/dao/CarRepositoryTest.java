package com.epam.spring.dao;

import com.epam.spring.entity.CarEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CarRepositoryTest {
    private CarEntity carEntity;

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    public void init() {
        carEntity = getCarEntity();
        carRepository.save(carEntity);
    }

    @Order(1)
    @Test
    void shouldFindAllCars() {
        List<CarEntity> actual = carRepository.findAll();
        assertThat(actual.size(), is(1));
    }

    @Order(2)
    @Test
    void shouldFindCarById() {
        CarEntity car = getCarEntity();
        car.setId(2);
        Optional<CarEntity> actual = carRepository.findById(2L);
        assertThat(actual.get(), is(car));
    }

    @Order(3)
    @Test
    void shouldFindCarByBrand() {
        CarEntity car = getCarEntity();
        car.setId(3);
        Optional<CarEntity> actual = carRepository.findByBrand("Opel");
        assertThat(actual.get(), is(car));
    }

    @Order(4)
    @Test
    void shouldFindCarByCarNumber() {
        CarEntity car = getCarEntity();
        car.setId(4);
        Optional<CarEntity> actual = carRepository.findByCarNumber("6959EX-7");
        assertThat(actual.get(), is(car));
    }

    @Order(5)
    @Test
    void shouldUpdateCar() {
        CarEntity car = getCarEntity();
        car.setId(5);
        car.setBrand("Mazda");
        CarEntity actual = carRepository.save(car);
        assertThat(actual, is(car));
    }

    @Order(6)
    @Test
    void shouldDeleteCar() {
        CarEntity car = getCarEntity();
        car.setId(6);
        carRepository.delete(car);
        Optional<CarEntity> actual = carRepository.findByBrand("Opel");
        assertThat(actual, is(Optional.empty()));
    }

    private CarEntity getCarEntity() {
        return new CarEntity(1, "Opel", "6959EX-7",null);
    }
}