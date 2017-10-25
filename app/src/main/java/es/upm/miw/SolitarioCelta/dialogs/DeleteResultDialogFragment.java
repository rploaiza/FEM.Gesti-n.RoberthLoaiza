package es.upm.miw.SolitarioCelta.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import java.io.IOException;

import es.upm.miw.SolitarioCelta.R;
import es.upm.miw.SolitarioCelta.activities.ResultActivity;
import es.upm.miw.SolitarioCelta.activities.MainActivity;

/**
 * Created by Usuario on 22/10/2017.
 */

public class DeleteResultDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ResultActivity main = (ResultActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(main);
        builder
                .setTitle(R.string.txtDialogoDeleteTitulo)
                .setMessage(R.string.txtDialogoDeletePregunta)
                .setPositiveButton(
                        getString(R.string.txtDialogoDeleteAfirmativo),
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    main.deleteResultados();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                startActivity(new Intent(main, MainActivity.class));
                            }
                        }
                )
                .setNegativeButton(
                        getString(R.string.txtDialogoDeleteNegativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                );

        return builder.create();
    }
}
