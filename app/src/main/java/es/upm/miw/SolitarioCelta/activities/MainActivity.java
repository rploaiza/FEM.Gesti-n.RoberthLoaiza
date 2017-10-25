package es.upm.miw.SolitarioCelta.activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.upm.miw.SolitarioCelta.dialogs.GetGameDialogFragment;
import es.upm.miw.SolitarioCelta.dialogs.SaveGameDialogFragment;
import es.upm.miw.SolitarioCelta.logic.JuegoCelta;
import es.upm.miw.SolitarioCelta.R;
import es.upm.miw.SolitarioCelta.dialogs.ResetGameDialogFragment;
import es.upm.miw.SolitarioCelta.logic.Resultados;

import static es.upm.miw.SolitarioCelta.activities.ResultActivity.RESULT;


public class MainActivity extends Activity {

    private final String GRID_KEY = "GRID_KEY";
    private final String CRONOMETRO_KEY = "CRONOMETRO_KEY";
    private final String SCORE_KEY = "SCORE_KEY";
    private final static String GAME = "partida.txt";
    private int numFichas;
    private Locale locale;
    private FileOutputStream fileOutputStream;
    private BufferedReader bufferedReader;
    public JuegoCelta juego;
    public Chronometer cronometro;
    public TextView score;
    public String estadoGuardado;

    private RadioButton radioButton;

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

        final String colores = PreferenceManager.getDefaultSharedPreferences(this).getString("colores", "ec");

        if (colores.equals("ec"))
            coloresEC();
        else
            coloresES();
    }

    public void coloresEC() {
        int[] flag1 = new int[]{R.id.p02, R.id.p03, R.id.p04,
                R.id.p12, R.id.p13, R.id.p14};
        int[] flag2 = new int[]{
                R.id.p20, R.id.p21, R.id.p22, R.id.p23, R.id.p24, R.id.p25, R.id.p26,
                R.id.p30, R.id.p31, R.id.p32, R.id.p33, R.id.p34, R.id.p34, R.id.p35, R.id.p36,
                R.id.p40, R.id.p41, R.id.p42, R.id.p43, R.id.p44, R.id.p45, R.id.p46};
        int[] flag3 = new int[]{
                R.id.p52, R.id.p53, R.id.p54,
                R.id.p62, R.id.p63, R.id.p64};

        for (int i = 0; i < flag1.length; i++) {
            radioButton = (RadioButton) findViewById(flag1[i]);
            radioButton.setButtonTintList(ColorStateList.valueOf(Color.YELLOW));
        }
        for (int i = 0; i < flag2.length; i++) {
            radioButton = (RadioButton) findViewById(flag2[i]);
            radioButton.setButtonTintList(ColorStateList.valueOf(Color.BLUE));
        }
        for (int i = 0; i < flag3.length; i++) {
            radioButton = (RadioButton) findViewById(flag3[i]);
            radioButton.setButtonTintList(ColorStateList.valueOf(Color.RED));
        }
    }

    public void coloresES() {
        int[] flag1 = new int[]{R.id.p02, R.id.p03, R.id.p04,
                R.id.p12, R.id.p13, R.id.p14};
        int[] flag2 = new int[]{
                R.id.p20, R.id.p21, R.id.p22, R.id.p23, R.id.p24, R.id.p25, R.id.p26,
                R.id.p30, R.id.p31, R.id.p32, R.id.p33, R.id.p34, R.id.p34, R.id.p35, R.id.p36,
                R.id.p40, R.id.p41, R.id.p42, R.id.p43, R.id.p44, R.id.p45, R.id.p46};
        int[] flag3 = new int[]{
                R.id.p52, R.id.p53, R.id.p54,
                R.id.p62, R.id.p63, R.id.p64};

        for (int i = 0; i < flag1.length; i++) {
            radioButton = (RadioButton) findViewById(flag1[i]);
            radioButton.setButtonTintList(ColorStateList.valueOf(Color.YELLOW));
        }
        for (int i = 0; i < flag2.length; i++) {
            radioButton = (RadioButton) findViewById(flag2[i]);
            radioButton.setButtonTintList(ColorStateList.valueOf(Color.RED));
        }
        for (int i = 0; i < flag3.length; i++) {
            radioButton = (RadioButton) findViewById(flag3[i]);
            radioButton.setButtonTintList(ColorStateList.valueOf(Color.YELLOW));
        }
    }

    /**
     * Se ejecuta al pulsar una ficha
     * Las coordenadas (i, j) se obtienen a partir del nombre, ya que el botón
     * tiene un identificador en formato pXY, donde X es la fila e Y la columna
     *
     * @param v Vista de la ficha pulsada
     */
    public void fichaPulsada(View v) throws IOException {
        String resourceName = getResources().getResourceEntryName(v.getId());
        int i = resourceName.charAt(1) - '0';   // fila
        int j = resourceName.charAt(2) - '0';   // columna
        juego.jugar(i, j);
        score.setText(getString(R.string.txt_token).concat(" ") + juego.contPiezas());
        mostrarTablero();
        if (juego.juegoTerminado()) {
            // TODO guardar puntuación
            cronometro.stop();
            new SaveGameDialogFragment().show(getFragmentManager(), "ALERT_DIALOG");
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
        outState.putLong(CRONOMETRO_KEY, cronometro.getBase());
        outState.putString(SCORE_KEY, score.getText().toString());
        super.onSaveInstanceState(outState);
    }

    /**
     * Recupera el estado del juego
     *
     * @param savedInstanceState Bundle con el estado del juego almacenado
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if ((savedInstanceState != null) && savedInstanceState.containsKey(CRONOMETRO_KEY)) {
            cronometro.setBase(savedInstanceState.getLong(CRONOMETRO_KEY));
            score.setText(savedInstanceState.getString(SCORE_KEY));
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
        bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(GAME)));
        this.estadoGuardado = bufferedReader.readLine();
        bufferedReader.close();
        if (this.numFichas > juego.numeroFichas()) {
            Toast.makeText(this, getString(R.string.txt_getSave), Toast.LENGTH_SHORT).show();
            DialogFragment dialogFragment = new GetGameDialogFragment();
            dialogFragment.show(getFragmentManager(), "SAVE_GAME");
            this.numFichas = juego.numeroFichas();
        }
    }

    public void saveResultFormat(String nombre) throws IOException {

        SimpleDateFormat date = new SimpleDateFormat();
        String fecha = date.format(new Date());

        Resultados resultado = new Resultados(nombre, fecha, juego.contPiezas(), (String) cronometro.getText());
        saveResultado(resultado);
        cronometro.start();
    }

    public void saveResultado(Resultados resultado) throws IOException {
        fileOutputStream = openFileOutput(RESULT, Context.MODE_APPEND);
        fileOutputStream.write(resultado.getJugador().getBytes());
        fileOutputStream.write("\t".getBytes());
        fileOutputStream.write(resultado.getFecha().getBytes());
        fileOutputStream.write("\t".getBytes());
        fileOutputStream.write(String.valueOf(resultado.getPiezas()).getBytes());
        fileOutputStream.write("\t".getBytes());
        fileOutputStream.write(String.valueOf(resultado.getCronometro()).getBytes());
        fileOutputStream.write("\n".getBytes());
        fileOutputStream.close();
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
                DialogFragment dialogFragment = new ResetGameDialogFragment();
                dialogFragment.show(getFragmentManager(), "RESET_GAME");
                return true;
            case R.id.opcGuardarPartida:
                try {
                    fileOutputStream = openFileOutput(GAME, Context.MODE_APPEND);
                    fileOutputStream.write(juego.serializaTablero().getBytes());
                    fileOutputStream.close();
                    Toast.makeText(this, getString(R.string.txt_saveGame), Toast.LENGTH_SHORT).show();
                    this.numFichas = juego.numeroFichas();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case R.id.opcRecuperarPartida:
                try {
                    getGame();
                } catch (IOException e) {

                }
                return true;
            case R.id.opcMejoresResultados:
                startActivity(new Intent(this, ResultActivity.class));
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