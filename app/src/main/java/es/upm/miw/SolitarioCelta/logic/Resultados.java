package es.upm.miw.SolitarioCelta.logic;

import java.io.Serializable;

/**
 * Created by Usuario on 21/10/2017.
 */

public class Resultados implements Serializable {

    private String jugador;
    private String fecha;
    private int piezas;
    private String cronometro;

    public Resultados(String jugador, String fecha, int piezas, String cronometro) {
        this.setJugador(jugador);
        this.setFecha(fecha);
        this.setPiezas(piezas);
        this.setCronometro(cronometro);
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String nombreJugador) {
        this.jugador = nombreJugador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getPiezas() {
        return piezas;
    }

    public void setPiezas(int piezas) {
        this.piezas = piezas;
    }

    public String getCronometro() {
        return cronometro;
    }

    public void setCronometro(String cronometro) {
        this.cronometro = cronometro;
    }

}

