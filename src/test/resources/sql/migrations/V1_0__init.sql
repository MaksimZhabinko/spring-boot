CREATE TABLE `users` (
	`id` INTEGER  NOT NULL PRIMARY KEY AUTO_INCREMENT,
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