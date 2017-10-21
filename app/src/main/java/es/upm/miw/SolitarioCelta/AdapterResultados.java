package es.upm.miw.SolitarioCelta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Usuario on 21/10/2017.
 */

public class AdapterResultados extends ArrayAdapter {

    private Context contexto;
    private List<String> resultados;
    private int resultRecursoID;

    public AdapterResultados(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.setContexto(context);
        this.setResultRecursoID(resource);
        this.setResultados((List<String>)objects);
    }

    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }

    public List<String> getResultados() {
        return resultados;
    }

    public void setResultados(List<String> resultados) {
        this.resultados = resultados;
    }

    public int getResultRecursoID() {
        return resultRecursoID;
    }

    public void setResultRecursoID(int resultRecursoID) {
        this.resultRecursoID = resultRecursoID;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout view;

        if (null != convertView) {
            view = (LinearLayout) convertView;
        } else {
            LayoutInflater linf = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (LinearLayout) linf.inflate(this.getResultRecursoID(), parent, false);
        }
        
        TextView resultado = (TextView) view.findViewById(R.id.resultado);
        resultado.setText(this.getResultados().get(position));

        return view;
    }
}
