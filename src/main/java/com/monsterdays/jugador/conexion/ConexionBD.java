package com.monsterdays.jugador.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Administra la conexión JDBC con la base de datos MySQL monster_days.
 * Los datos de acceso se leen de variables de entorno para no exponer
 * credenciales en el repositorio.
 */
public final class ConexionBD {

    private static final String URL_POR_DEFECTO =
            "jdbc:mysql://localhost:3306/monster_days?useSSL=false&serverTimezone=America/Bogota";
    private static final String USUARIO_POR_DEFECTO = "root";
    private static final String CLAVE_POR_DEFECTO = "";

    private ConexionBD() {
        // Clase de utilidad: no se instancia.
    }

    /**
     * Abre una nueva conexión. Quien la solicita debe cerrarla (try-with-resources).
     *
     * @return conexión activa con la base de datos
     * @throws SQLException si no es posible conectarse
     */
    public static Connection obtenerConexion() throws SQLException {
        String url = leerVariable("MD_DB_URL", URL_POR_DEFECTO);
        String usuario = leerVariable("MD_DB_USER", USUARIO_POR_DEFECTO);
        String clave = leerVariable("MD_DB_PASSWORD", CLAVE_POR_DEFECTO);
        return DriverManager.getConnection(url, usuario, clave);
    }

    private static String leerVariable(String nombre, String valorPorDefecto) {
        String valor = System.getenv(nombre);
        return (valor == null || valor.isBlank()) ? valorPorDefecto : valor;
    }
}
