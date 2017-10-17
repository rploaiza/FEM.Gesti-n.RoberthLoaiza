package es.upm.miw.SolitarioCelta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;

/**
 * Created by Usuario on 16/10/2017.
 */

public class Reiniciar extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity main = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(main);
        builder
                .setTitle(R.string.txtDialogoReinicioDeTitulo)
                .setMessage(R.string.txtDialogoReinicioDeMensaje)
                .setPositiveButton(
                        getString(R.string.txtDialogoReinicioDePositivo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                main.juego.reiniciar();
                                main.mostrarTablero();
                                main.cronometro.setBase(SystemClock.elapsedRealtime());
                            }
                        }
                )
                .setNegativeButton(
                        getString(R.string.txtDialogoReinicioDeNegativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }
                );

        return builder.create();
    }
}