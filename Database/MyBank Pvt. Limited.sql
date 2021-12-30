# Customer Table

CREATE TABLE `mybank_pvt_ltd`.`customer` (
  `customer_id` INT NOT NULL AUTO_INCREMENT,
  `account_number` VARCHAR(45) NULL,
  `full_name` VARCHAR(45) NULL,
  `balance` VARCHAR(45) NULL,
  `registered_date` DATE NULL,
  PRIMARY KEY (`customer_id`));
  
  
  # Teller Table
  CREATE TABLE `mybank_pvt_ltd`.`teller` (
  `teller_id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NOT NULL,
  `pin` INT NOT NULL,
  PRIMARY KEY (`teller_id`));