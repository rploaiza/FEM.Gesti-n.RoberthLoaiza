package es.upm.miw.SolitarioCelta;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class MainActivity extends Activity {

    private final String GRID_KEY = "GRID_KEY";
    private final String CRONOMETRO = "CRONOMETRO";
    private final String SCORE = "SCORE";
    private Locale locale;
    JuegoCelta juego;
    Chronometer cronometro;
    TextView score;
    public String estadoGuardado;
    private int numFichas;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        juego = new JuegoCelta();
        mostrarTablero();
        cronometro = ((Chronometer) findViewById(R.id.chronometer));
        cronometro.start();
        cronometro.setFormat(getString(R.string.txt_time) + "(%s)");

        score = (TextView) findViewById(R.id.score);
        score.setText(R.string.txt_token);

    }

    public void onStart() {
        super.onStart();
        final Configuration configs = getBaseContext().getResources().getConfiguration();
        final String language = PreferenceManager.getDefaultSharedPreferences(this).getString("language", "");

        if (!TextUtils.isEmpty(language) && !configs.locale.getLanguage().equals(language)) {
            locale = new Locale(language);
            Locale.setDefault(locale);
            configs.locale = locale;
            getBaseContext().getResources().updateConfiguration(configs, getBaseContext().getResources().getDisplayMetrics());
            startActivity(new Intent(this, MainActivity.class));

        }
    }


    /**
     * Se ejecuta al pulsar una ficha
     * Las coordenadas (i, j) se obtienen a partir del nombre, ya que el botón
     * tiene un identificador en formato pXY, donde X es la fila e Y la columna
     *
     * @param v Vista de la ficha pulsada
     */
    public void fichaPulsada(View v) {
        String resourceName = getResources().getResourceEntryName(v.getId());
        int i = resourceName.charAt(1) - '0';   // fila
        int j = resourceName.charAt(2) - '0';   // columna
        juego.jugar(i, j);
        score.setText(getString(R.string.txt_token).concat(" ") + juego.contPiezas());
        mostrarTablero();
        if (juego.juegoTerminado()) {
            // TODO guardar puntuación
            cronometro.stop();
            new AlertDialogFragment().show(getFragmentManager(), "ALERT_DIALOG");
        }
    }

    /**
     * Visualiza el tablero
     */
    public void mostrarTablero() {
        RadioButton button;
        String strRId;
        String prefijoIdentificador = getPackageName() + ":id/p"; // formato: package:type/entry
        int idBoton;

        for (int i = 0; i < JuegoCelta.TAMANIO; i++)
            for (int j = 0; j < JuegoCelta.TAMANIO; j++) {
                strRId = prefijoIdentificador + Integer.toString(i) + Integer.toString(j);
                idBoton = getResources().getIdentifier(strRId, null, null);
                if (idBoton != 0) { // existe el recurso identificador del botón
                    button = (RadioButton) findViewById(idBoton);
                    button.setChecked(juego.obtenerFicha(i, j) == JuegoCelta.FICHA);
                }
            }
    }

    /**
     * Guarda el estado del tablero (serializado)
     *
     * @param outState Bundle para almacenar el estado del juego
     */
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(GRID_KEY, juego.serializaTablero());
        outState.putLong(CRONOMETRO, cronometro.getBase());
        outState.putString(SCORE, score.getText().toString());
        super.onSaveInstanceState(outState);
    }

    /**
     * Recupera el estado del juego
     *
     * @param savedInstanceState Bundle con el estado del juego almacenado
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if ((savedInstanceState != null) && savedInstanceState.containsKey(CRONOMETRO)) {
            cronometro.setBase(savedInstanceState.getLong(CRONOMETRO));
            score.setText(savedInstanceState.getString(SCORE));
        }
        String grid = savedInstanceState.getString(GRID_KEY);
        juego.deserializaTablero(grid);
        mostrarTablero();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public void getGame() throws IOException {
        BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("partida.txt")));
        this.estadoGuardado = fin.readLine();
        fin.close();
        if (this.numFichas > juego.numeroFichas()) {
            DialogFragment dialogFragment = new GetDialogFragment();
            dialogFragment.show(getFragmentManager(), "getGame");
            this.numFichas = juego.numeroFichas();
        } else {
            juego.deserializaTablero(estadoGuardado);
            this.mostrarTablero();
        }
        Toast.makeText(this, getString(R.string.txt_getSave), Toast.LENGTH_SHORT).show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcAjustes:
                startActivity(new Intent(this, SCeltaPrefs.class));
                return true;
            case R.id.opcAcercaDe:
                startActivity(new Intent(this, AcercaDe.class));
                return true;
            case R.id.opcReiniciarPartida:
                DialogFragment dialogFragment = new Reiniciar();
                dialogFragment.show(getFragmentManager(), "restart");
                return true;
            case R.id.opcGuardarPartida:
                try {
                    FileOutputStream fos = openFileOutput("partida.txt", Context.MODE_PRIVATE);
                    fos.write(juego.serializaTablero().getBytes());
                    fos.close();
                    Toast.makeText(this, getString(R.string.txt_saveGame), Toast.LENGTH_SHORT).show();
                    this.numFichas = juego.numeroFichas();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case R.id.opcRecuperarPartida:
                try {
                    getGame();
                } catch (IOException e) {
                    Toast.makeText(this, getString(R.string.txt_getSave), Toast.LENGTH_SHORT).show();
                }
                return true;
            // TODO!!! resto opciones

            default:
                Toast.makeText(
                        this,
                        getString(R.string.txtSinImplementar),
                        Toast.LENGTH_SHORT
                ).show();
        }
        return true;
    }

}
