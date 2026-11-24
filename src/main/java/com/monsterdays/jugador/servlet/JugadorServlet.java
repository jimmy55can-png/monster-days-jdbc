package com.monsterdays.jugador.servlet;

import com.monsterdays.jugador.dao.JugadorDao;
import com.monsterdays.jugador.modelo.Jugador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HexFormat;
import java.util.Optional;

/**
 * Controlador web del módulo Jugador. Reutiliza JugadorDao (evidencia AA2-EV01).
 *
 * GET  /jugadores                 -> lista los jugadores (listar.jsp)
 * GET  /jugadores?accion=nuevo    -> formulario vacío (formulario.jsp)
 * GET  /jugadores?accion=editar&id=N -> formulario con los datos del jugador
 * POST /jugadores (accion=guardar)   -> inserta o actualiza el jugador
 * POST /jugadores (accion=eliminar)  -> elimina el jugador
 */
@WebServlet("/jugadores")
public class JugadorServlet extends HttpServlet {

    private static final String PAGINA_LISTA = "/listar.jsp";
    private static final String PAGINA_FORMULARIO = "/formulario.jsp";

    private final JugadorDao jugadorDao = new JugadorDao();

    @Override
    protected void doGet(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws ServletException, IOException {
        String accion = solicitud.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        try {
            switch (accion) {
                case "nuevo":
                    solicitud.getRequestDispatcher(PAGINA_FORMULARIO).forward(solicitud, respuesta);
                    break;
                case "editar":
                    mostrarFormularioEdicion(solicitud, respuesta);
                    break;
                default:
                    solicitud.setAttribute("jugadores", jugadorDao.listarJugadores());
                    solicitud.getRequestDispatcher(PAGINA_LISTA).forward(solicitud, respuesta);
            }
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error al procesar la solicitud GET", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws ServletException, IOException {
        solicitud.setCharacterEncoding("UTF-8");
        String accion = solicitud.getParameter("accion");

        try {
            if ("eliminar".equals(accion)) {
                int idJugador = Integer.parseInt(solicitud.getParameter("idJugador"));
                jugadorDao.eliminarJugador(idJugador);
                respuesta.sendRedirect(solicitud.getContextPath() + "/jugadores?mensaje=eliminado");
            } else {
                guardarJugador(solicitud, respuesta);
            }
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error al procesar la solicitud POST", e);
        }
    }

    private void mostrarFormularioEdicion(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws SQLException, ServletException, IOException {
        int idJugador = Integer.parseInt(solicitud.getParameter("id"));
        Optional<Jugador> jugador = jugadorDao.buscarPorId(idJugador);
        if (jugador.isEmpty()) {
            respuesta.sendRedirect(solicitud.getContextPath() + "/jugadores");
            return;
        }
        solicitud.setAttribute("jugador", jugador.get());
        solicitud.getRequestDispatcher(PAGINA_FORMULARIO).forward(solicitud, respuesta);
    }

    private void guardarJugador(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws SQLException, ServletException, IOException {
        String idTexto = leerTexto(solicitud, "idJugador");
        boolean esNuevo = idTexto.isEmpty();
        String clave = leerTexto(solicitud, "clave");

        Jugador jugador = new Jugador();
        jugador.setNombreUsuario(leerTexto(solicitud, "nombreUsuario"));
        jugador.setCorreo(leerTexto(solicitud, "correo"));
        jugador.setNivelMaximo(Integer.parseInt(leerTexto(solicitud, "nivelMaximo")));
        if (!esNuevo) {
            jugador.setIdJugador(Integer.parseInt(idTexto));
        }

        if (jugador.getNombreUsuario().isEmpty() || jugador.getCorreo().isEmpty()
                || (esNuevo && clave.isEmpty())) {
            volverAlFormulario(solicitud, respuesta, jugador, "Todos los campos son obligatorios.");
            return;
        }

        try {
            if (esNuevo) {
                jugador.setClaveHash(calcularHash(clave));
                jugadorDao.insertarJugador(jugador);
            } else {
                jugadorDao.actualizarJugador(jugador);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            volverAlFormulario(solicitud, respuesta, jugador,
                    "El nombre de usuario o el correo ya están registrados.");
            return;
        }
        respuesta.sendRedirect(solicitud.getContextPath() + "/jugadores?mensaje=guardado");
    }

    private void volverAlFormulario(HttpServletRequest solicitud, HttpServletResponse respuesta,
                                    Jugador jugador, String error) throws ServletException, IOException {
        solicitud.setAttribute("error", error);
        solicitud.setAttribute("jugador", jugador);
        solicitud.getRequestDispatcher(PAGINA_FORMULARIO).forward(solicitud, respuesta);
    }

    private String leerTexto(HttpServletRequest solicitud, String nombre) {
        String valor = solicitud.getParameter(nombre);
        return valor == null ? "" : valor.trim();
    }

    /** Guarda la clave como hash SHA-256 (nunca en texto plano). */
    private String calcularHash(String clave) throws ServletException {
        try {
            MessageDigest resumen = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(resumen.digest(clave.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new ServletException("Algoritmo de hash no disponible", e);
        }
    }
}
