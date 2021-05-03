-- MySQL Workbench Forward Engineering

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema baseball
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `baseball` ;

-- -----------------------------------------------------
-- Schema baseball
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `baseball` DEFAULT CHARACTER SET utf8 ;
USE `baseball` ;

-- -----------------------------------------------------
-- Table `sidedish`.`team`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baseball`.`team` ;

CREATE TABLE IF NOT EXISTS `baseball`.`team` (
    `team_name` VARCHAR(50),
    PRIMARY KEY (`team_name`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `sidedish`.`dish_delivery`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baseball`.`player` ;

CREATE TABLE IF NOT EXISTS `baseball`.`player`(
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `player_name` VARCHAR (50),
    `team` VARCHAR (50),
    CONSTRAINT player_team_foreign_key FOREIGN KEY (`team`) REFERENCES `team` (team_name)
);


