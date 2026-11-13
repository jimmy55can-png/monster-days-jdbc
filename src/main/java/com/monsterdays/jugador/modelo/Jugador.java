package com.monsterdays.jugador.modelo;

import java.time.LocalDateTime;

/**
 * Representa a un jugador de Monster Days (tabla jugador).
 */
public class Jugador {

    private int idJugador;
    private String nombreUsuario;
    private String correo;
    private String claveHash;
    private int nivelMaximo;
    private LocalDateTime fechaRegistro;

    public Jugador() {
    }

    public Jugador(String nombreUsuario, String correo, String claveHash) {
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.claveHash = claveHash;
        this.nivelMaximo = 1;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClaveHash() {
        return claveHash;
    }

    public void setClaveHash(String claveHash) {
        this.claveHash = claveHash;
    }

    public int getNivelMaximo() {
        return nivelMaximo;
    }

    public void setNivelMaximo(int nivelMaximo) {
        this.nivelMaximo = nivelMaximo;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Jugador{id=" + idJugador
                + ", usuario='" + nombreUsuario + '\''
                + ", correo='" + correo + '\''
                + ", nivelMaximo=" + nivelMaximo
                + ", registro=" + fechaRegistro + '}';
    }
}
