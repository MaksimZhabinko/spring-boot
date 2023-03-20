INSERT INTO `users` (`username`, `first_name`, `last_name`, `email`, `password`, `role`, `status`)
VALUES ('aNti', 'Maxim', 'Zhabinko', 'maxim_zhabinko@mail.ru', '$2a$10$RuEK/jYy4lDSFXEtPVXNL.oTwAAF.vJigh0evEzXChnFl3vmI46T6', 'ADMIN', 'ACTIVE');
INSERT INTO `users` (`username`, `first_name`, `last_name`, `email`, `password`, `role`, `status`)
VALUES ('QQ', 'Anton', 'Tabolich', 'anton_tabolicho@mail.ru', '$2a$10$RuEK/jYy4lDSFXEtPVXNL.oTwAAF.vJigh0evEzXChnFl3vmI46T6', 'USER', 'ACTIVE');
INSERT INTO `cars` (`brand`, `car_number`, `fk_user_id`) VALUES ('Opel', 'HB-002-VB', 2);