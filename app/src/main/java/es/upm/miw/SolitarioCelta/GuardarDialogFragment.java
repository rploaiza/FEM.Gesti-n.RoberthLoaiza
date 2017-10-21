package es.upm.miw.SolitarioCelta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

/**
 * Created by Usuario on 21/10/2017.
 */

public class GuardarDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity main = (MainActivity) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(View.inflate(main, R.layout.save_result, null))
                .setTitle(R.string.txtDialogoFinalTitulo)
                .setPositiveButton(
                        R.string.resultSave,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                String nombreDialog = ((EditText) GuardarDialogFragment.this
                                        .getDialog().findViewById(R.id.userName))
                                        .getText().toString();
                                try {
                                    if (nombreDialog.length() > 0) {
                                        main.saveResultVariable(nombreDialog);
                                    } else {
                                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(main);
                                        String nombrePrtef = sharedPref.getString("nombreJugador", "Jugador");
                                        main.saveResultVariable(nombrePrtef);
                                    }
                                    new AlertDialogFragment().show(getFragmentManager(), "ALERT DIALOG");
                                } catch (Exception e) {
                                }
                            }
                        }
                )
                .setNegativeButton(
                        R.string.resultCancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                GuardarDialogFragment.this.getDialog().cancel();
                            }
                        }
                );

        return builder.create();
    }
}