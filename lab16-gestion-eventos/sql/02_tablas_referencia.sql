-- SOLO REFERENCIA (necesario si usas perfil prod con ddl-auto=validate y no corriste dev antes)
USE bd_evento;
CREATE TABLE IF NOT EXISTS evento (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(120) NOT NULL, lugar VARCHAR(150) NOT NULL,
  fecha DATE NOT NULL, capacidad INT NOT NULL,
  estado ENUM('ACTIVO','CANCELADO','FINALIZADO') NOT NULL);
USE bd_asistente;
CREATE TABLE IF NOT EXISTS asistente (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(80) NOT NULL, apellido VARCHAR(80) NOT NULL,
  email VARCHAR(120) NOT NULL, telefono VARCHAR(15),
  CONSTRAINT uk_asistente_email UNIQUE (email));
USE bd_inscripcion;
CREATE TABLE IF NOT EXISTS inscripcion (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  id_evento BIGINT NOT NULL, id_asistente BIGINT NOT NULL,
  fecha DATE NOT NULL, estado ENUM('CONFIRMADA','CANCELADA') NOT NULL);
