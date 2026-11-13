package com.monsterdays.jugador.principal;

import com.monsterdays.jugador.dao.JugadorDao;
import com.monsterdays.jugador.modelo.Jugador;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Menú de consola para probar el CRUD del módulo Jugador de Monster Days.
 */
public class Main {

    private static final JugadorDao JUGADOR_DAO = new JugadorDao();

    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero(lector, "Opción: ");
            try {
                ejecutarOpcion(opcion, lector);
            } catch (SQLException error) {
                System.out.println("Error de base de datos: " + error.getMessage());
            }
        } while (opcion != 0);
        System.out.println("Hasta pronto.");
    }

    private static void mostrarMenu() {
        System.out.println("\n=== MONSTER DAYS - MÓDULO JUGADOR ===");
        System.out.println("1. Registrar jugador");
        System.out.println("2. Listar jugadores");
        System.out.println("3. Buscar jugador por id");
        System.out.println("4. Actualizar jugador");
        System.out.println("5. Eliminar jugador");
        System.out.println("0. Salir");
    }

    private static void ejecutarOpcion(int opcion, Scanner lector) throws SQLException {
        switch (opcion) {
            case 1 -> registrarJugador(lector);
            case 2 -> listarJugadores();
            case 3 -> buscarJugador(lector);
            case 4 -> actualizarJugador(lector);
            case 5 -> eliminarJugador(lector);
            case 0 -> { }
            default -> System.out.println("Opción no válida.");
        }
    }

    private static void registrarJugador(Scanner lector) throws SQLException {
        String nombreUsuario = leerTexto(lector, "Nombre de usuario: ");
        String correo = leerTexto(lector, "Correo: ");
        String claveHash = leerTexto(lector, "Clave (hash): ");
        Jugador jugador = new Jugador(nombreUsuario, correo, claveHash);
        if (JUGADOR_DAO.insertarJugador(jugador)) {
            System.out.println("Jugador registrado con id " + jugador.getIdJugador());
        }
    }

    private static void listarJugadores() throws SQLException {
        List<Jugador> jugadores = JUGADOR_DAO.listarJugadores();
        if (jugadores.isEmpty()) {
            System.out.println("No hay jugadores registrados.");
            return;
        }
        jugadores.forEach(System.out::println);
    }

    private static void buscarJugador(Scanner lector) throws SQLException {
        int idJugador = leerEntero(lector, "Id del jugador: ");
        Optional<Jugador> jugador = JUGADOR_DAO.buscarPorId(idJugador);
        System.out.println(jugador.map(Jugador::toString).orElse("Jugador no encontrado."));
    }

    private static void actualizarJugador(Scanner lector) throws SQLException {
        int idJugador = leerEntero(lector, "Id del jugador a actualizar: ");
        Optional<Jugador> existente = JUGADOR_DAO.buscarPorId(idJugador);
        if (existente.isEmpty()) {
            System.out.println("Jugador no encontrado.");
            return;
        }
        Jugador jugador = existente.get();
        jugador.setNombreUsuario(leerTexto(lector, "Nuevo nombre de usuario: "));
        jugador.setCorreo(leerTexto(lector, "Nuevo correo: "));
        jugador.setNivelMaximo(leerEntero(lector, "Nuevo nivel máximo (1-4): "));
        System.out.println(JUGADOR_DAO.actualizarJugador(jugador)
                ? "Jugador actualizado." : "No se actualizó ningún registro.");
    }

    private static void eliminarJugador(Scanner lector) throws SQLException {
        int idJugador = leerEntero(lector, "Id del jugador a eliminar: ");
        System.out.println(JUGADOR_DAO.eliminarJugador(idJugador)
                ? "Jugador eliminado." : "Jugador no encontrado.");
    }

    private static String leerTexto(Scanner lector, String mensaje) {
        System.out.print(mensaje);
        return lector.nextLine().trim();
    }

    private static int leerEntero(Scanner lector, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(lector.nextLine().trim());
            } catch (NumberFormatException error) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }
}
