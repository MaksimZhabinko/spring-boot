package com.epam.spring.dao;

import com.epam.spring.entity.RoleEntity;
import com.epam.spring.entity.StatusEntity;
import com.epam.spring.entity.UserEntity;
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
class UserRepositoryTest {
    private UserEntity userEntity;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        userEntity = getUserEntity();
        userRepository.save(userEntity);
    }

    @Order(1)
    @Test
    void shouldFindAllUsers() {
        List<UserEntity> actual = userRepository.findAll();
        assertThat(actual.size(), is(1));
    }

    @Order(2)
    @Test
    void shouldFindUserByUsername() {
        UserEntity user = getUserEntity();
        user.setId(2);
        Optional<UserEntity> actual = userRepository.findByUsername("username");
        assertThat(actual.get(), is(user));
    }

    @Order(3)
    @Test
    void shouldFindUserById() {
        UserEntity user = getUserEntity();
        user.setId(3);
        Optional<UserEntity> actual = userRepository.findById(3L);
        assertThat(actual.get(), is(user));
    }

    @Order(5)
    @Test
    void shouldDeleteUser() {
        UserEntity user = getUserEntity();
        user.setId(5);
        userRepository.delete(user);
        Optional<UserEntity> actual = userRepository.findById(5L);
        assertThat(actual, is(Optional.empty()));
    }

    @Order(4)
    @Test
    void shouldUpdateUser() {
        UserEntity user = getUserEntity();
        user.setId(4);
        user.setEmail("new Email");
        UserEntity actual = userRepository.save(user);
        assertThat(actual, is(user));
    }

    private UserEntity getUserEntity() {
        return new UserEntity(1, "username", "firstName", "lastName", "email",
                "pass", RoleEntity.USER, StatusEntity.ACTIVE, null);
    }
}