package com.monsterdays.jugador.dao;

import com.monsterdays.jugador.conexion.ConexionBD;
import com.monsterdays.jugador.modelo.Jugador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Acceso a datos del módulo Jugador: inserción, consulta, actualización y eliminación.
 * Todas las sentencias usan PreparedStatement para evitar inyección SQL.
 */
public class JugadorDao {

    private static final String SQL_INSERTAR =
            "INSERT INTO jugador (nombre_usuario, correo, clave_hash, nivel_maximo) VALUES (?, ?, ?, ?)";
    private static final String SQL_CONSULTAR_TODOS =
            "SELECT id_jugador, nombre_usuario, correo, clave_hash, nivel_maximo, fecha_registro "
                    + "FROM jugador ORDER BY id_jugador";
    private static final String SQL_CONSULTAR_POR_ID =
            "SELECT id_jugador, nombre_usuario, correo, clave_hash, nivel_maximo, fecha_registro "
                    + "FROM jugador WHERE id_jugador = ?";
    private static final String SQL_ACTUALIZAR =
            "UPDATE jugador SET nombre_usuario = ?, correo = ?, nivel_maximo = ? WHERE id_jugador = ?";
    private static final String SQL_ELIMINAR =
            "DELETE FROM jugador WHERE id_jugador = ?";

    /**
     * Inserta un jugador y asigna al objeto el id generado por la base de datos.
     *
     * @return true si se insertó el registro
     */
    public boolean insertarJugador(Jugador jugador) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia =
                     conexion.prepareStatement(SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setString(1, jugador.getNombreUsuario());
            sentencia.setString(2, jugador.getCorreo());
            sentencia.setString(3, jugador.getClaveHash());
            sentencia.setInt(4, jugador.getNivelMaximo());

            boolean insertado = sentencia.executeUpdate() > 0;
            if (insertado) {
                try (ResultSet claves = sentencia.getGeneratedKeys()) {
                    if (claves.next()) {
                        jugador.setIdJugador(claves.getInt(1));
                    }
                }
            }
            return insertado;
        }
    }

    /**
     * Consulta todos los jugadores registrados.
     */
    public List<Jugador> listarJugadores() throws SQLException {
        List<Jugador> jugadores = new ArrayList<>();
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_CONSULTAR_TODOS);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                jugadores.add(construirJugador(resultado));
            }
        }
        return jugadores;
    }

    /**
     * Consulta un jugador por su identificador.
     *
     * @return el jugador, o vacío si no existe
     */
    public Optional<Jugador> buscarPorId(int idJugador) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_CONSULTAR_POR_ID)) {

            sentencia.setInt(1, idJugador);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(construirJugador(resultado));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Actualiza nombre de usuario, correo y nivel máximo de un jugador existente.
     *
     * @return true si se actualizó al menos un registro
     */
    public boolean actualizarJugador(Jugador jugador) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ACTUALIZAR)) {

            sentencia.setString(1, jugador.getNombreUsuario());
            sentencia.setString(2, jugador.getCorreo());
            sentencia.setInt(3, jugador.getNivelMaximo());
            sentencia.setInt(4, jugador.getIdJugador());
            return sentencia.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un jugador por su identificador.
     *
     * @return true si se eliminó el registro
     */
    public boolean eliminarJugador(int idJugador) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ELIMINAR)) {

            sentencia.setInt(1, idJugador);
            return sentencia.executeUpdate() > 0;
        }
    }

    private Jugador construirJugador(ResultSet resultado) throws SQLException {
        Jugador jugador = new Jugador();
        jugador.setIdJugador(resultado.getInt("id_jugador"));
        jugador.setNombreUsuario(resultado.getString("nombre_usuario"));
        jugador.setCorreo(resultado.getString("correo"));
        jugador.setClaveHash(resultado.getString("clave_hash"));
        jugador.setNivelMaximo(resultado.getInt("nivel_maximo"));
        Timestamp fecha = resultado.getTimestamp("fecha_registro");
        if (fecha != null) {
            jugador.setFechaRegistro(fecha.toLocalDateTime());
        }
        return jugador;
    }
}
