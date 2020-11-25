CREATE TABLE IF NOT EXISTS unquitravel.USUARIO
 (
  id long not null AUTO_INCREMENT,
  nombreUsuario varchar(255) not null unique,
  email varchar(255) not null unique,
  contrasenia varchar(255) not null,
  nombre varchar(255),
  apellido varchar(255),
  edad int,
  primary key(id)
)

ENGINE = InnoDB;