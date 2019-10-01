package com.sxtsoft.gestiongastos.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxtsoft.gestiongastos.R;
import com.sxtsoft.gestiongastos.database.Utilidades;
import com.sxtsoft.gestiongastos.model.Gasto;

import java.util.List;

public class AdapterRvHistoricosGastos extends RecyclerView.Adapter<AdapterRvHistoricosGastos.ViewHolder> {

    private List<Gasto> gastos;
    private LayoutInflater inflater;
    private OnDelRowGastoListener onDelRowGastoListener;
    private Context context;

    public AdapterRvHistoricosGastos(List<Gasto> gastos, Context context, OnDelRowGastoListener onDelRowGastoListener) {

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.gastos = gastos;
        this.onDelRowGastoListener = onDelRowGastoListener;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterRvHistoricosGastos.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.row_historicos_gastos, null);
        return new ViewHolder(view, onDelRowGastoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRvHistoricosGastos.ViewHolder viewHolder, int position) {

        viewHolder.importe.setText(String.valueOf(gastos.get(position).getImporte()));
        viewHolder.usuario.setText(gastos.get(position).getUsuario().getUserName());

        viewHolder.icono.setImageResource(Utilidades.getIdResourcesDrawable(context,gastos.get(position).getTipoGasto().getIcono() ));

        viewHolder.tipoGasto.setText(gastos.get(position).getTipoGasto().getNombre());
        viewHolder.fecha.setText(gastos.get(position).getFecha().toString());

        Log.d("**",gastos.get(position).getUsuario().getUserName());
    }

    @Override
    public int getItemCount() {
        return gastos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView importe;
        public TextView tipoGasto;
        public TextView fecha;
        public ImageView icono;
        public TextView usuario;
        OnDelRowGastoListener onDelRowGastoListener;

        public ViewHolder(@NonNull View itemView, OnDelRowGastoListener onDelRowGastoListener) {
            super(itemView);

            this.onDelRowGastoListener = onDelRowGastoListener;

            importe = (TextView) itemView.findViewById(R.id.txtOutImporte);
            tipoGasto = (TextView) itemView.findViewById(R.id.txtOutTipoGasto);
            fecha = (TextView) itemView.findViewById(R.id.txtOutFecha);
            icono = (ImageView) itemView.findViewById(R.id.imgOutCategoria);
            usuario = (TextView) itemView.findViewById(R.id.txtOutUsuario);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDelRowGastoListener.OnDelRowGasto(gastos.get(getAdapterPosition()).getCodigo(), getAdapterPosition());
        }
    }

    public void setGastos(List<Gasto> gastos){
        this.gastos = gastos;
    }

    public interface OnDelRowGastoListener{
        void OnDelRowGasto(long idGasto, int position);
    }
}
