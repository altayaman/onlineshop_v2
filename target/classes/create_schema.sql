CREATE SCHEMA `phones_db` ;

CREATE TABLE `phones_db`.`clients_info` (
  `client_id` INT NOT NULL AUTO_INCREMENT,
  `client_firstname` VARCHAR(45) NULL,
  `client_lastname` VARCHAR(45) NULL,
  `client_email` VARCHAR(45) NULL,
  PRIMARY KEY (`client_id`));
  
CREATE TABLE `phones_db`.`clients_passwords` (
  `client_id` INT NOT NULL,
  `client_email` VARCHAR(45) NULL,
  `client_password` VARCHAR(45) NULL,
  PRIMARY KEY (`client_id`));
  
insert into phones_db.clients_info values(1, 'Altay', 'Amanbay', 'altay.amanbay@gmail.com');
insert into phones_db.clients_info values(2, 'John', 'Smith', 'john.smith@gmail.com');
insert into phones_db.clients_info values(3, 'Anna', 'Marie', 'anna.marie@gmail.com');

insert into phones_db.clients_passwords values(1, 'altay.amanbay@gmail.com', 'altay');
insert into phones_db.clients_passwords values(2, 'john.smith@gmail.com', 'john');
insert into phones_db.clients_passwords values(3, 'anna.marie@gmail.com', 'anna');