/*
SQLyog Community v13.2.0 (64 bit)
MySQL - 10.4.28-MariaDB : Database - veterinaria
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`veterinaria` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `veterinaria`;

/*Table structure for table `animales` */

DROP TABLE IF EXISTS `animales`;

CREATE TABLE `animales` (
  `idanimal` int(11) NOT NULL AUTO_INCREMENT,
  `nombreanimal` varchar(40) NOT NULL,
  PRIMARY KEY (`idanimal`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `animales` */

insert  into `animales`(`idanimal`,`nombreanimal`) values 
(1,'Perro'),
(2,'Gato'),
(3,'Loro');

/*Table structure for table `clientes` */

DROP TABLE IF EXISTS `clientes`;

CREATE TABLE `clientes` (
  `idcliente` int(11) NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(40) NOT NULL,
  `nombres` varchar(40) NOT NULL,
  `dni` char(8) NOT NULL,
  `claveacceso` varchar(100) NOT NULL,
  PRIMARY KEY (`idcliente`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `clientes` */

insert  into `clientes`(`idcliente`,`apellidos`,`nombres`,`dni`,`claveacceso`) values 
(1,'Cusi','Luis David','73195465','$2y$10$dd9CtlKw1s9CFvA/9MB82.WEYjt8PfoEipd3DF42UOBoWunIjAtfC'),
(2,'Gallardo','Alejandro Jesus','73195423','123');

/*Table structure for table `mascotas` */

DROP TABLE IF EXISTS `mascotas`;

CREATE TABLE `mascotas` (
  `idmascota` int(11) NOT NULL AUTO_INCREMENT,
  `idcliente` int(11) NOT NULL,
  `idraza` int(11) NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `fotografia` varchar(200) DEFAULT NULL,
  `color` varchar(30) NOT NULL,
  `genero` varchar(30) NOT NULL,
  PRIMARY KEY (`idmascota`),
  KEY `fk_idc_ma` (`idcliente`),
  KEY `fk_idr_ma` (`idraza`),
  CONSTRAINT `fk_idc_ma` FOREIGN KEY (`idcliente`) REFERENCES `clientes` (`idcliente`),
  CONSTRAINT `fk_idr_ma` FOREIGN KEY (`idraza`) REFERENCES `razas` (`idraza`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `mascotas` */

insert  into `mascotas`(`idmascota`,`idcliente`,`idraza`,`nombre`,`fotografia`,`color`,`genero`) values 
(1,1,1,'Destructor',NULL,'Crema','Macho'),
(2,2,2,'Princesa',NULL,'Blanco','Macho'),
(8,1,1,'Pepe','foto.png','Negro','Macho'),
(9,1,1,'milaneso','mila.png','Blanco','Macho');

/*Table structure for table `razas` */

DROP TABLE IF EXISTS `razas`;

CREATE TABLE `razas` (
  `idraza` int(11) NOT NULL AUTO_INCREMENT,
  `idanimal` int(11) NOT NULL,
  `nombreraza` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`idraza`),
  KEY `fk_ida_ra` (`idanimal`),
  CONSTRAINT `fk_ida_ra` FOREIGN KEY (`idanimal`) REFERENCES `animales` (`idanimal`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `razas` */

insert  into `razas`(`idraza`,`idanimal`,`nombreraza`) values 
(1,1,'Pastor aleman'),
(2,1,'Pitbul');

/* Procedure structure for procedure `spu_add_clientes` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_add_clientes` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_add_clientes`(
IN _apellidos VARCHAR(40),
IN _nombres	VARCHAR(40),
IN _dni		CHAR(8),
IN _claveacceso VARCHAR(100)
)
BEGIN
	INSERT INTO clientes (apellidos, nombres, dni, claveacceso) VALUES
			(_apellidos, _nombres, _dni, _claveacceso);
END */$$
DELIMITER ;

/* Procedure structure for procedure `spu_add_mascotas` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_add_mascotas` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_add_mascotas`(
IN _idcliente INT,
IN _idraza INT ,
IN _nombre VARCHAR(40),
IN _fotografia VARCHAR(200),
IN _color VARCHAR(30),
IN _genero VARCHAR(30)
)
BEGIN
	INSERT INTO mascotas (idcliente, idraza, nombre, fotografia, color, genero) VALUES
			(_idcliente, _idraza, _nombre, _fotografia, _color, _genero);
END */$$
DELIMITER ;

/* Procedure structure for procedure `spu_buscar_cliente` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_buscar_cliente` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_buscar_cliente`(IN _dni CHAR(8))
BEGIN 

	SELECT CLI.nombres, CLI.apellidos,
	 RA.nombreraza, MA.nombre, MA.color, MA.genero
	FROM clientes CLI
	INNER JOIN mascotas MA ON MA.idcliente = CLI.idcliente
    INNER JOIN razas RA ON RA.idraza = MA.idraza
    WHERE CLI.dni = _dni;

END */$$
DELIMITER ;

/* Procedure structure for procedure `spu_buscar_mascota` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_buscar_mascota` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_buscar_mascota`(IN _dni CHAR(8))
BEGIN 

	SELECT RA.nombreraza, MA.nombre, MA.color, MA.genero
	FROM clientes CLI
	INNER JOIN mascotas MA ON MA.idcliente = CLI.idcliente
    INNER JOIN razas RA ON RA.idraza = MA.idraza
    WHERE CLI.dni = _dni;

END */$$
DELIMITER ;

/* Procedure structure for procedure `spu_getMascotas` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_getMascotas` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_getMascotas`()
BEGIN 

	SELECT CONCAT(CLI.nombres , ' ' , CLI.apellidos) as cliente ,
	 RA.nombreraza, MA.nombre, MA.color, MA.genero
	FROM clientes CLI
	INNER JOIN mascotas MA ON MA.idcliente = CLI.idcliente
    INNER JOIN razas RA ON RA.idraza = MA.idraza;

END */$$
DELIMITER ;

/* Procedure structure for procedure `spu_getRazas` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_getRazas` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_getRazas`()
BEGIN 

	select idraza, nombreraza
	from razas; 

END */$$
DELIMITER ;

/* Procedure structure for procedure `spu_login` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_login` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_login`( 
IN _dni CHAR(8)
)
BEGIN 
	SELECT *
	FROM clientes
	WHERE dni = _dni;

END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
