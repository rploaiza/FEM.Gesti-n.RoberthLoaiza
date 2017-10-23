package es.upm.miw.SolitarioCelta.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;

import es.upm.miw.SolitarioCelta.R;
import es.upm.miw.SolitarioCelta.activities.MainActivity;

public class AlertDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final MainActivity main = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(main);
        builder.setTitle(R.string.txtDialogoFinalTitulo);
        builder.setMessage(R.string.txtDialogoFinalPregunta);
        builder.setPositiveButton(
                getString(R.string.txtDialogoFinalAfirmativo),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        main.juego.reiniciar();
                        main.mostrarTablero();
                        main.cronometro.setBase(SystemClock.elapsedRealtime());
                        main.score.setText(R.string.txt_token);
                    }
                }
        );
        builder.setNegativeButton(
                getString(R.string.txtDialogoFinalNegativo),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        main.finish();
                    }
                }
        );

        return builder.create();
	}
}
