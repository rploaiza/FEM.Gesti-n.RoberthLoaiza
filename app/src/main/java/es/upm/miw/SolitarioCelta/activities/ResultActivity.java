package es.upm.miw.SolitarioCelta.activities;

import android.app.Activity;
import android.content.Context;
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

import es.upm.miw.SolitarioCelta.logic.AdapterResultados;
import es.upm.miw.SolitarioCelta.R;
import es.upm.miw.SolitarioCelta.logic.Resultados;
import es.upm.miw.SolitarioCelta.dialogs.DeleteResultDialogFragment;

/**
 * Created by Usuario on 21/10/2017.
 */

public class ResultActivity extends Activity {

    public final static String RESULT = "result.txt";
    private FileOutputStream fileOutputStream;
    List<String> resultados = new ArrayList<String>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        ListView listView = (ListView) findViewById(R.id.listado);
        try {
            resultados = obtenerResultados();
            List<Resultados> listaObjetosResultado = obtenerListaObjetosResultado(resultados);
            resultados = ordenarResultados(listaObjetosResultado);
            AdapterResultados adaptador = new AdapterResultados(this, R.layout.result, resultados);
            listView.setAdapter(adaptador);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Obtener una la lista de objetos - tabulados
    private List<Resultados> obtenerListaObjetosResultado(List<String> resultados) {
        List<Resultados> lista = new ArrayList<Resultados>();
        for (String resul : resultados) {
            String[] separated = resul.split("\t");
            Resultados r = new Resultados(separated[0], separated[1], Integer.parseInt(separated[2]), separated[3]);
            lista.add(r);
        }
        return lista;
    }

    //Ordenar los objetos para visualizar en el view
    private List<String> ordenarResultados(List<Resultados> resultados) {
        List<String> listaOrdenada = new ArrayList<String>();
        Collections.sort(resultados);
        for (Resultados result : resultados) {
            String resultadoOrd = result.getNombreJugador() + " " + result.getFecha() + " - " + getString(R.string.txt_token) + " (" + result.getNumPiezas() + ")\t" + " - " + result.getCronometro();
            listaOrdenada.add(resultadoOrd);
        }
        return listaOrdenada;
    }

    //Read de fichero resultados
    private List<String> obtenerResultados() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(RESULT)));
        String line = bufferedReader.readLine();
        while (line != null) {
            resultados.add(line);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return resultados;
    }

    //Eliminar datos del fichero
    public void deleteResultados() throws IOException {
        fileOutputStream = openFileOutput(RESULT, Context.MODE_PRIVATE);
        fileOutputStream.write("".getBytes());
        fileOutputStream.close();
        Toast.makeText(this, R.string.txtdelete, Toast.LENGTH_SHORT).show();

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.result_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteResultado:
                new DeleteResultDialogFragment().show(getFragmentManager(), "DELETE_DIALOG");
                return true;
        }
        return true;
    }
}

