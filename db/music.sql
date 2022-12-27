create database `mydb`;
use `mydb`;

CREATE TABLE IF NOT EXISTS `mydb`.`admin` (
  `adminId` INT NOT NULL AUTO_INCREMENT,
  `adminUsername` NVARCHAR(255) NULL,
  `adminPassword` NVARCHAR(255) NULL,
  `adminRegisterDate` DATETIME NULL,
  `adminLastDate` DATETIME NULL,
  PRIMARY KEY (`adminId`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `mydb`.`normaluser` (
  `userId` INT NOT NULL AUTO_INCREMENT,
  `userName` NVARCHAR(255) NULL,
  `userPassword` NVARCHAR(255) NULL,
  `userNickname` NVARCHAR(255) NULL,
  `userSex` INT NULL,
  `userEmail` VARCHAR(255) NULL,
  `userAvatar` VARCHAR(255) NULL,
  `userRegisterDate` DATETIME NULL,
  `userLastDate` DATETIME NULL,
  `userStatus` INT NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `mydb`.`singer` (
  `singerId` INT NOT NULL AUTO_INCREMENT,
  `singerName` VARCHAR(255) NULL,
  `singerSex` INT NULL,
  `singerThumbnail` VARCHAR(255) NULL,
  `singerIntroduction` VARCHAR(255) NULL,
  PRIMARY KEY (`singerId`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `mydb`.`album` (
  `albumId` INT NOT NULL AUTO_INCREMENT,
  `singerId` INT NOT NULL,
  `albumTitle` NVARCHAR(255) NULL,
  `albumPic` NVARCHAR(255) NULL,
  `albumPubDate` DATETIME NULL,
  `albumPubCom` NVARCHAR(255) NULL,
  PRIMARY KEY (`albumId`),
  CONSTRAINT `singerId`
    FOREIGN KEY (`singerId`)
    REFERENCES `mydb`.`singer` (`singerId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `mydb`.`song` (
  `songId` INT NOT NULL AUTO_INCREMENT,
  `singerId` INT NOT NULL,
  `albumId` INT NOT NULL,
  `songTitle` NVARCHAR(255) NULL,
  `songPlaytimes` INT NULL,
  `songDldtimes` INT NULL,
  `songFile` NVARCHAR(255) NULL,
  PRIMARY KEY (`songId`),
    FOREIGN KEY (`singerId`)
    REFERENCES `mydb`.`singer` (`singerId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
	FOREIGN KEY (`albumId`)
    REFERENCES `mydb`.`album` (`albumId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `mydb`.`comments` (
  `commentId` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NOT NULL,
  `songId` INT NOT NULL,
  `commentText` NVARCHAR(255) NULL,
  `commentDate` DATETIME NULL,
  PRIMARY KEY (`commentId`),
    FOREIGN KEY (`songId`)
    REFERENCES `mydb`.`song` (`songId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (`userId`)
    REFERENCES `mydb`.`normaluser` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `mydb`.`newsong` (
  `newSongId` INT NOT NULL AUTO_INCREMENT,
  `songId` INT NOT NULL,
  PRIMARY KEY (`newSongId`),
    FOREIGN KEY (`songId`)
    REFERENCES `mydb`.`song` (`songId`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `mydb`.`normaluseralbum` (
  `userId` INT NOT NULL,
  `albumId` INT NOT NULL,
  PRIMARY KEY (`userId`, `albumId`),
    FOREIGN KEY (`userId`)
    REFERENCES `mydb`.`normaluser` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (`albumId`)
    REFERENCES `mydb`.`album` (`albumId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `mydb`.`normalusersinger` (
  `userId` INT NOT NULL,
  `singerId` INT NOT NULL,
  PRIMARY KEY (`userId`, `singerId`),
    FOREIGN KEY (`userId`)
    REFERENCES `mydb`.`normaluser` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (`singerId`)
    REFERENCES `mydb`.`singer` (`singerId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `mydb`.`normalusersong` (
  `userId` INT NOT NULL,
  `songId` INT NOT NULL,
  PRIMARY KEY (`userId`, `songId`),
    FOREIGN KEY (`songId`)
    REFERENCES `mydb`.`song` (`songId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (`userId`)
    REFERENCES `mydb`.`normaluser` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `mydb`.`recommend` (
  `recmdId` INT NOT NULL AUTO_INCREMENT,
  `songId` INT NOT NULL,
  PRIMARY KEY (`recmdId`),
    FOREIGN KEY (`songId`)
    REFERENCES `mydb`.`song` (`songId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

INSERT INTO mydb.`admin` (adminId, adminUsername, adminPassword, adminRegisterDate, adminLastDate) VALUES (1, 'kimthuy', '123456', '2022-11-27', '2022-11-28');
