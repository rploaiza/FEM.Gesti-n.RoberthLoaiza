package es.upm.miw.SolitarioCelta;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Usuario on 21/10/2017.
 */

public class ViewResultados extends Activity {

    Context context = null;
    List<String> resultados = new ArrayList<String>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        context = getApplicationContext();

        ListView lvlistado = (ListView) findViewById(R.id.listado);

        try {
            resultados = obtenerResultadosFichero();
            List<Resultados> listaObjetosResultado = obtenerListaObjetosResultado(resultados);
            resultados=ordenarResultados(listaObjetosResultado);
            AdapterResultados adaptador = new AdapterResultados(this, R.layout.result, resultados);
            lvlistado.setAdapter(adaptador);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Resultados> obtenerListaObjetosResultado(List<String> resultados) {
        List<Resultados> list = new ArrayList<Resultados>();
        for (String resul : resultados)
        {
            String[] separated = resul.split("\t");
            Resultados r = new Resultados(
                    separated[0],
                    separated[1],
                    Integer.parseInt(separated[2]),
                    separated[3]
            );
            list.add(r);
        }
        return list;
    }

    private List<String> ordenarResultados(List<Resultados> resultados) {
        List<String> listaOrdenada = new ArrayList<String>();
        Collections.sort(resultados);
        for (Resultados r : resultados){
            String resultadoOrd = r.getNombreJugador()+"\t"+ " - Fecha:"+r.getFecha()+"\t"+" - Piezas:"+r.getNumPiezas()+"\t"+" - "+r.getCronometro();
            listaOrdenada.add(resultadoOrd);
        }
        return listaOrdenada;
    }

    private List<String> obtenerResultadosFichero() throws IOException {
        BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("result.txt")));
        String linea = fin.readLine();
        while (linea!=null) {
            resultados.add(linea);
            linea=fin.readLine();
        }
        fin.close();
        return resultados;
    }

}

