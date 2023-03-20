DROP SCHEMA IF EXISTS `spring_boot`;

CREATE SCHEMA IF NOT EXISTS `spring_boot`;

USE `spring_boot`;

CREATE TABLE `users` (
	`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(255) NOT NULL UNIQUE,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NUll UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `role` ENUM('USER', 'ADMIN') NOT NULL,
    `status` ENUM('ACTIVE', 'DELETED') NOT NULL
);

CREATE TABLE `cars` (
	`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `brand` VARCHAR(255) NOT NULL,
    `car_number` VARCHAR(255) NOT NULL UNIQUE,
    `fk_user_id` INTEGER NOT NULL,

     CONSTRAINT `fk_book_x_users` FOREIGN KEY (`fk_user_id`) REFERENCES `users` (`id`)
				ON DELETE CASCADE
				ON UPDATE CASCADE
);

INSERT INTO `users` (`username`, `first_name`, `last_name`, `email`, `password`, `role`, `status`)
VALUES ('aNti', 'Maxim', 'Zhabinko', 'maxim_zhabinko@mail.ru', '$2a$10$RuEK/jYy4lDSFXEtPVXNL.oTwAAF.vJigh0evEzXChnFl3vmI46T6', 'ADMIN', 'ACTIVE');
INSERT INTO `users` (`username`, `first_name`, `last_name`, `email`, `password`, `role`, `status`)
VALUES ('QQ', 'Anton', 'Tabolich', 'anton_tabolicho@mail.ru', '$2a$10$RuEK/jYy4lDSFXEtPVXNL.oTwAAF.vJigh0evEzXChnFl3vmI46T6', 'USER', 'ACTIVE');
INSERT INTO `cars` (`brand`, `car_number`, `fk_user_id`) VALUES ('Opel', 'HB-002-VB', 2);











