package com.sxtsoft.gestiongastos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sxtsoft.gestiongastos.R;
import com.sxtsoft.gestiongastos.model.Alarma;

import java.util.List;

public class AdapterRvAlarmas extends RecyclerView.Adapter<AdapterRvAlarmas.ViewHolder> {

    private Context context;
    private List<Alarma> alarmas;
    private OnAlarmasListener onAlarmasListener;
    private LayoutInflater inflater;

    public AdapterRvAlarmas(Context context, List<Alarma> alarmas, OnAlarmasListener onAlarmasListener){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.alarmas = alarmas;
        this.context = context;
        this.onAlarmasListener = onAlarmasListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_lista_alarmas, null);


        return new ViewHolder(view, onAlarmasListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        /*
        coloco los valores a modificar
        del viewHolder
         */

        holder.tipoGasto.setText(alarmas.get(position).getTipoGasto().getNombre());
        holder.importe.setText(String.valueOf(alarmas.get(position).getImporte()));
        holder.cicloDias.setText(String.valueOf(alarmas.get(position).getDias()));
        holder.categoria.setText(alarmas.get(position).getCategoria().toString());

    }

    @Override
    public int getItemCount() {
        return alarmas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnAlarmasListener onAlarmasListener;
        private TextView cicloDias;
        private TextView importe;
        private TextView categoria;
        private TextView tipoGasto;
        private FloatingActionButton editarAlarma;


        public ViewHolder(@NonNull View view, OnAlarmasListener onAlarmasListener) {
            super(view);

            this.onAlarmasListener = onAlarmasListener;

            cicloDias = (TextView) view.findViewById(R.id.txtImporteRowAlarma);
            importe = (TextView) view.findViewById(R.id.txtCicloRowAlarma);
            categoria = (TextView) view.findViewById(R.id.txtCategoriaRowAlarma);
            tipoGasto = (TextView) view.findViewById(R.id.txtTipoGastoRowAlarma);
            editarAlarma = (FloatingActionButton) view.findViewById(R.id.btnEditRowAlarma);

            editarAlarma.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onAlarmasListener.OnAlarmasClick(getAdapterPosition(), alarmas.get(getAdapterPosition()).getCodigo());
        }
    }

    public interface OnAlarmasListener{
        void OnAlarmasClick(int position, long idAlarma);
    }
}
