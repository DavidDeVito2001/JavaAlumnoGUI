-- =====================================================================
--  TP Alumno GUI - Base de datos
--  Motor: MySQL 8.0
--  Crea la base "universidad_caba" y la tabla "alumnos" que usa el
--  AlumnoDAOSQL (columnas DNI, NOMBRE, APELLIDO, FEC_NAC, PROMEDIO,
--  CANT_MAT_APROB, FEC_ING, ESTADO).
-- =====================================================================

CREATE DATABASE IF NOT EXISTS universidad_caba;
USE universidad_caba;

CREATE TABLE IF NOT EXISTS alumnos (
    DNI            INT          PRIMARY KEY,
    NOMBRE         VARCHAR(50)  NOT NULL,
    APELLIDO       VARCHAR(50)  NOT NULL,
    FEC_NAC        DATE,
    PROMEDIO       DOUBLE,
    CANT_MAT_APROB SMALLINT,
    FEC_ING        DATE,
    ESTADO         CHAR(1)      NOT NULL DEFAULT 'A'
);

-- Datos de ejemplo (todos cumplen las validaciones: 18+ al ingresar,
-- promedio 0-10, etc.). Carlos esta dado de baja ('B') para probar
-- el "Ver Eliminados".
INSERT IGNORE INTO alumnos
    (DNI, NOMBRE, APELLIDO, FEC_NAC, PROMEDIO, CANT_MAT_APROB, FEC_ING, ESTADO) VALUES
    (30111222, 'Juan',   'Perez', '2000-05-02', 7.50, 12, '2019-03-01', 'A'),
    (31222333, 'Maria',  'Gomez', '1999-11-20', 8.75, 20, '2018-08-15', 'A'),
    (32333444, 'Carlos', 'Lopez', '2001-02-10', 6.00,  5, '2020-03-10', 'B');
