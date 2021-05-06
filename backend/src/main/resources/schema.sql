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
-- Table `baseball`.`team`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baseball`.`team` ;

CREATE TABLE IF NOT EXISTS `baseball`.`team` (
    `id` INT,
    `team_name` VARCHAR(50),
    `selected` VARCHAR(50),
    PRIMARY KEY (`team_name`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `baseball`.`record`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baseball`.`record` ;

CREATE TABLE IF NOT EXISTS `baseball`.`record`(
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `player_name` VARCHAR (50),
    `at_bat` INT,
    `hits` INT,
    `outs` INT,
    `average` DECIMAL (4,3))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `baseball`.`player`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baseball`.`player` ;

CREATE TABLE IF NOT EXISTS `baseball`.`player`(
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `player_name` VARCHAR (50),
    `team` VARCHAR (50),
    `record` INT,
    CONSTRAINT player_team_foreign_key FOREIGN KEY (`team`) REFERENCES `team` (team_name),
    CONSTRAINT dish_sale_sale_foreign_key FOREIGN KEY (`record`) REFERENCES `record` (id)
);

-- -----------------------------------------------------
-- Table `baseball`.`game`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baseball`.`game` ;

CREATE TABLE IF NOT EXISTS `baseball`.`game`
(
    id        BIGINT auto_increment primary key,
    user_type varchar(20) not null,
    home      varchar(20) references team (team_name),
    away      varchar(20) references team (team_name)
);

-- -----------------------------------------------------
-- Table `baseball`.`score_board`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baseball`.`score_board` ;

CREATE TABLE IF NOT EXISTS `baseball`.`score_board`
(
    id   BIGINT auto_increment primary key,
    game BIGINT references game (id),
    team varchar(20) references team (team_name)
);

-- -----------------------------------------------------
-- Table `baseball`.`innings`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baseball`.`innings` ;

CREATE TABLE IF NOT EXISTS `baseball`.`innings`
(
    id              BIGINT auto_increment primary key,
    score           int not null,
    score_board     BIGINT references score_board (id),
    score_board_key int
);
