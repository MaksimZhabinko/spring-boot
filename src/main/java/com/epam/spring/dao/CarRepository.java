package com.epam.spring.dao;

import com.epam.spring.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
    Optional<CarEntity> findByBrand(String brand);
    Optional<CarEntity> findByCarNumber(String carNumber);
}
