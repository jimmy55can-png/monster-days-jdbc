-- Tabla usada por el módulo Jugador (ajústala si tu monster_days.sql define otras columnas)
CREATE DATABASE IF NOT EXISTS monster_days CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE monster_days;

CREATE TABLE IF NOT EXISTS jugador (
    id_jugador      INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario  VARCHAR(30)  NOT NULL UNIQUE,
    correo          VARCHAR(100) NOT NULL UNIQUE,
    clave_hash      VARCHAR(255) NOT NULL,
    nivel_maximo    TINYINT      NOT NULL DEFAULT 1 CHECK (nivel_maximo BETWEEN 1 AND 4),
    fecha_registro  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
