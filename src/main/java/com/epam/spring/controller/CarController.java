package com.epam.spring.controller;

import com.epam.spring.dto.CarDto;
import com.epam.spring.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {
    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/allCars")
    @ResponseStatus(HttpStatus.OK)
    public List<CarDto> findAllCars() {
        return carService.findAllCars();
    }

    @GetMapping("/carId/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarDto findCarById(@PathVariable("id") long id) {
        return carService.findCarById(id);
    }

    @GetMapping("/carBrand/{brand}")
    @ResponseStatus(HttpStatus.OK)
    public CarDto findCarByBrand(@PathVariable("brand") String brand) {
        return carService.findCarByBrand(brand);
    }

    @GetMapping("/carNumber/{carNumber}")
    @ResponseStatus(HttpStatus.OK)
    public CarDto findCarByCarNumber(@PathVariable("carNumber") String carNumber) {
        return carService.findCarByCarNumber(carNumber);
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CarDto addCar(@RequestBody CarDto carDto, @PathVariable("userId") long userId) {
        return carService.addCar(carDto, userId);
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public CarDto updateCar(@RequestBody CarDto carDto, @PathVariable("userId") long userId) {
        return carService.updateCar(carDto, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCarById(@PathVariable("id") Long id) {
        carService.deleteCarById(id);
    }
}
