package es.upm.miw.SolitarioCelta;

/**
 * Created by Usuario on 21/10/2017.
 */

public class Resultados implements Comparable<Resultados>{

    private String nombreJugador;
    private String fecha;
    private int numPiezas;

    private String cronometro;

    public Resultados(String nombreJugador, String fecha, int numPiezas, String cronometro) {
        this.setNombreJugador(nombreJugador);
        this.setFecha(fecha);
        this.setNumPiezas(numPiezas);
        this.setCronometro(cronometro);
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNumPiezas() {
        return numPiezas;
    }

    public void setNumPiezas(int numPiezas) {
        this.numPiezas = numPiezas;
    }

    public String getCronometro() {
        return cronometro;
    }

    public void setCronometro(String cronometro) {
        this.cronometro = cronometro;
    }
    @Override
    public int compareTo(Resultados resultado) {
        if(numPiezas<resultado.numPiezas) {
            return -1;
        }
        if(numPiezas>resultado.numPiezas){
            return 1;
        }
        return 0;
    }
}

