CREATE DATABASE veterinaria;
USE veterinaria;

CREATE TABLE clientes
(
idcliente 	INT AUTO_INCREMENT PRIMARY KEY,
apellidos 	VARCHAR(40) NOT NULL,
nombres 	VARCHAR(40) NOT NULL,
dni 		CHAR(8) NOT NULL,
claveacceso VARCHAR(100) NOT NULL
)
ENGINE = INNODB;

INSERT INTO clientes (apellidos, nombres, dni , claveacceso) VALUES
			('Cusi','Luis David', 73195465 , '123'),
            ('Gallardo','Alejandro Jesus', 73195423 , '123');

CREATE TABLE animales
(
idanimal	INT AUTO_INCREMENT PRIMARY KEY,
nombreanimal		VARCHAR(40) NOT NULL
)
ENGINE = INNODB;

INSERT INTO animales (nombreanimal) VALUES
					('Perro'),
                    ('Gato'),
                    ('Loro');
	
CREATE TABLE razas 
(
idraza	INT AUTO_INCREMENT PRIMARY KEY,
idanimal INT NOT NULL,
nombreraza	VARCHAR(40),
CONSTRAINT fk_ida_ra FOREIGN KEY (idanimal) REFERENCES animales (idanimal)
)
ENGINE = INNODB;

INSERT INTO razas (idanimal ,nombreraza) VALUES
					(1, 'Pastor aleman'),
                    (1, 'Pitbul');

CREATE TABLE mascotas
(
idmascota INT AUTO_INCREMENT PRIMARY KEY,
idcliente INT NOT NULL,
idraza 	INT NOT NULL,
nombre	VARCHAR(40) NOT NULL,
fotografia	VARCHAR(200) NULL,
color	VARCHAR(30) NOT NULL,
genero	VARCHAR(30) NOT NULL,
CONSTRAINT fk_idc_ma FOREIGN KEY (idcliente) REFERENCES clientes(idcliente),
CONSTRAINT fk_idr_ma FOREIGN KEY (idraza) REFERENCES razas (idraza)

)
ENGINE = INNODB;

INSERT INTO mascotas (idcliente , idraza, nombre, color, genero) VALUES
					(1, 1 , 'Destructor', 'Crema', 'Macho'),
                    (2, 2 , 'Princesa', 'Blanco' , 'Macho') ;
                    
INSERT INTO mascotas (idcliente , idraza, nombre, color, genero) VALUES
		(4, 1 , 'MataPerras', 'Blanco y negro', 'Macho');

								
DELIMITER $$
CREATE PROCEDURE spu_add_clientes
(
IN _apellidos VARCHAR(40),
IN _nombres	VARCHAR(40),
IN _dni		CHAR(8),
IN _claveacceso VARCHAR(100)
)
BEGIN
	INSERT INTO clientes (apellidos, nombres, dni, claveacceso) VALUES
			(_apellidos, _nombres, _dni, _claveacceso);
END $$


DELIMITER $$
CREATE PROCEDURE spu_add_mascotas
(
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
END $$

-- BUSCAR CLIENTE (Muestra detalle mascota)

DELIMITER $$
CREATE PROCEDURE spu_buscar_mascota(IN _dni CHAR(8))
BEGIN 

	SELECT RA.nombreraza, MA.nombre, MA.color, MA.genero
	FROM clientes CLI
	INNER JOIN mascotas MA ON MA.idcliente = CLI.idcliente
    INNER JOIN razas RA ON RA.idraza = MA.idraza
    WHERE CLI.dni = _dni;

END $$

CALL spu_buscar_mascota(73195465);


DELIMITER $$
CREATE PROCEDURE spu_buscar_cliente(IN _dni CHAR(8))
BEGIN 

	SELECT CLI.nombres, CLI.apellidos,
	 RA.nombreraza, MA.nombre, MA.color, MA.genero
	FROM clientes CLI
	INNER JOIN mascotas MA ON MA.idcliente = CLI.idcliente
    INNER JOIN razas RA ON RA.idraza = MA.idraza
    WHERE CLI.dni = _dni;

END $$

CALL spu_buscar_cliente(12345678);



DELIMITER $$ 
CREATE PROCEDURE spu_login 
( 
IN _dni CHAR(8)
)
BEGIN 
	SELECT *
	FROM clientes
	WHERE dni = _dni;

END $$ 

CALL spu_login(73195465);

DELIMITER $$
CREATE PROCEDURE spu_getMascotas()
BEGIN 

	SELECT CONCAT(CLI.nombres , ' ' , CLI.apellidos) AS cliente ,
	 RA.nombreraza, MA.nombre, MA.color, MA.genero
	FROM clientes CLI
	INNER JOIN mascotas MA ON MA.idcliente = CLI.idcliente
    INNER JOIN razas RA ON RA.idraza = MA.idraza;

END $$

CALL spu_getMascotas()

SELECT * FROM clientes

DELIMITER $$
CREATE PROCEDURE spu_getRazas()
BEGIN 

	SELECT idraza, nombreraza
	FROM razas; 

END $$

CALL spu_getRazas()








