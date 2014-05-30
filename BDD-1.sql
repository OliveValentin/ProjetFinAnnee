﻿-- MySQL Script generated by MySQL Workbench
-- 05/26/14 20:03:25
-- Model: New Model    Version: 1.0
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema Cave
-- -----------------------------------------------------
-- Base de données vin
CREATE SCHEMA IF NOT EXISTS `Cave` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `Cave` ;

-- -----------------------------------------------------
-- Table `Cave`.`Vins`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cave`.`Vins` (
  `identifiantVin` INT NOT NULL AUTO_INCREMENT,
  `regionVin` VARCHAR(70) NULL,
  `domaineVin` VARCHAR(70) NULL,
  `châteauVin` VARCHAR(45) NULL,
  `couleur` VARCHAR(45) NULL,
  `cepageVin` VARCHAR(45) NULL,
  PRIMARY KEY (`identifiantVin`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cave`.`Utilisateur`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cave`.`Utilisateur` (
  `identifiantUtilisateur` INT NOT NULL AUTO_INCREMENT,
  `nomUtilisateur` VARCHAR(45) NOT NULL,
  `motDePasse` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`identifiantUtilisateur`),
  UNIQUE (`nomUtilisateur`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `Cave`.`CaveAVin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cave`.`CaveAVin` (
  `nomCave` VARCHAR(45) NOT NULL,
  `nombreDeBouteille` INT NULL,
  `commentaire` LONGTEXT NULL,
  `Utilisateur_identifiantUtilisateur` INT NOT NULL,
  PRIMARY KEY (`nomCave`, `Utilisateur_identifiantUtilisateur`),
  CONSTRAINT `fk_CaveAVin_Utilisateur`
    FOREIGN KEY (`Utilisateur_identifiantUtilisateur`)
    REFERENCES `Cave`.`Utilisateur` (`identifiantUtilisateur`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cave`.`Etagère`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cave`.`Etagère` (
  `identifiantEtagère` INT NOT NULL AUTO_INCREMENT,
  `positionEtagère` VARCHAR(100) NOT NULL,
  `CaveAVin_nomCave` VARCHAR(45) NOT NULL,
  `CaveAVin_Utilisateur_identifiantUtilisateur` INT NOT NULL,
  PRIMARY KEY (`identifiantEtagère`, `CaveAVin_nomCave`, `CaveAVin_Utilisateur_identifiantUtilisateur`),
  CONSTRAINT `fk_Etagère_CaveAVin1`
    FOREIGN KEY (`CaveAVin_nomCave` , `CaveAVin_Utilisateur_identifiantUtilisateur`)
    REFERENCES `Cave`.`CaveAVin` (`nomCave` , `Utilisateur_identifiantUtilisateur`)
	ON DELETE CASCADE
	ON UPDATE CASCADE
	)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cave`.`Bouteille`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cave`.`Bouteille` (
  `identifiantBouteille` INT NOT NULL AUTO_INCREMENT,
  `taille` FLOAT NULL,
  PRIMARY KEY (`identifiantBouteille`)
  )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `Cave`.`Bouteille_has_Vins`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cave`.`Bouteille_has_Vins` (
  `Bouteille_identifiantBouteille` INT NOT NULL,
  `Vins_identifiantVin` INT NOT NULL,
  `dateVin` INT NULL,
  `Etagère_identifiantEtagère` INT NOT NULL,
  PRIMARY KEY (`Bouteille_identifiantBouteille`, `Vins_identifiantVin`, `Etagère_identifiantEtagère`),
  CONSTRAINT `fk_Bouteille_has_Vins_Bouteille1`
    FOREIGN KEY (`Bouteille_identifiantBouteille`)
    REFERENCES `Cave`.`Bouteille` (`identifiantBouteille`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Bouteille_has_Vins_Vins1`
    FOREIGN KEY (`Vins_identifiantVin`)
    REFERENCES `Cave`.`Vins` (`identifiantVin`)
	ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Bouteille_has_Vins_Etagère1`
    FOREIGN KEY (`Etagère_identifiantEtagère`)
    REFERENCES `Cave`.`Etagère` (`identifiantEtagère`)
	ON DELETE CASCADE
	ON UPDATE CASCADE
	)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
