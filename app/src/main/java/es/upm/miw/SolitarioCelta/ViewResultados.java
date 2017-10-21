package es.upm.miw.SolitarioCelta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

        ListView lvListado = (ListView) findViewById(R.id.listado);

        try {
            resultados = obtenerResultados();
            List<Resultados> listaObjetosResultado = obtenerListaObjetosResultado(resultados);
            resultados = ordenarResultados(listaObjetosResultado);
            AdapterResultados adaptador = new AdapterResultados(this, R.layout.result, resultados);
            lvListado.setAdapter(adaptador);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Resultados> obtenerListaObjetosResultado(List<String> resultados) {
        List<Resultados> list = new ArrayList<Resultados>();
        for (String resul : resultados) {
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
        for (Resultados r : resultados) {
            String resultadoOrd = r.getNombreJugador() + "\t" + " " + r.getFecha() + "\t" + " - Piezas:" + r.getNumPiezas() + "\t" + " - " + r.getCronometro();
            listaOrdenada.add(resultadoOrd);
        }
        return listaOrdenada;
    }

    private List<String> obtenerResultados() throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(openFileInput("result.txt")));
        String line = read.readLine();
        while (line != null) {
            resultados.add(line);
            line = read.readLine();
        }
        read.close();
        return resultados;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.result_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteResultado:
                FileOutputStream fos = null;
                try {
                    fos = openFileOutput("result.txt", Context.MODE_PRIVATE);
                    fos.write("".getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(this, "Resultados eliminados", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ViewResultados.class));
                return true;
        }
        return true;
    }
}

