package es.upm.miw.SolitarioCelta.logic;

import java.util.Comparator;

/**
 * Created by Usuario on 25/10/2017.
 */

public class ResultadoComparater implements Comparator<Resultados> {
    @Override
    public int compare(Resultados resultado1, Resultados resultado2) {
        return (resultado1.getPiezas() > resultado2.getPiezas()) ? 1 : (resultado1.getPiezas() < resultado2.getPiezas()) ? -1 : 0;
    }
}
