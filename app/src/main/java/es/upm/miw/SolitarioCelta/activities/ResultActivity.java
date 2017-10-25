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
import es.upm.miw.SolitarioCelta.logic.ResultadoComparater;
import es.upm.miw.SolitarioCelta.logic.Resultados;
import es.upm.miw.SolitarioCelta.dialogs.DeleteResultDialogFragment;

/**
 * Created by Usuario on 21/10/2017.
 */

public class ResultActivity extends Activity {

    public final static String RESULT = "result.txt";
    private FileOutputStream fileOutputStream;
    private BufferedReader bufferedReader;
    List<String> resultados = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        ListView listView = (ListView) findViewById(R.id.listado);
        try {
            List<Resultados> lista = objList(getResult());
            AdapterResultados adapterResultados = new AdapterResultados(this, R.layout.result, objOrder(lista));
            listView.setAdapter(adapterResultados);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    //Obtener una la lista de objetos - tabulados
    private List<Resultados> objList(List<String> resultados) {
        List<Resultados> lista = new ArrayList<>();
        for (String listResult : resultados) {
            String[] position = listResult.split("\t");
            Resultados resultado = new Resultados(position[0], position[1], Integer.parseInt(position[2]), position[3]);
            lista.add(resultado);
        }
        return lista;
    }

    //Ordenar los objetos para visualizar en el view
    private List<String> objOrder(List<Resultados> resultados) {
        List<String> ordList = new ArrayList<>();
        Collections.sort(resultados, new ResultadoComparater());
        for (Resultados resultado : resultados) {
            String rstOrd = resultado.getJugador() + "        " + resultado.getFecha() + "\n - " + getString(R.string.txt_token)
                    + " (" + resultado.getPiezas() + ")" + "        " + resultado.getCronometro();
            ordList.add(rstOrd);
        }
        return ordList;
    }

    //Read de fichero resultados
    private List<String> getResult() throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(RESULT)));
        String rdLine = bufferedReader.readLine();
        do{
            resultados.add(rdLine);
            rdLine = bufferedReader.readLine();
        }while(rdLine != null);
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
}

