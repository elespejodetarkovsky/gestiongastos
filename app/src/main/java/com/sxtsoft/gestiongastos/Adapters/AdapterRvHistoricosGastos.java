package com.sxtsoft.gestiongastos.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxtsoft.gestiongastos.R;
import com.sxtsoft.gestiongastos.model.Gasto;
import com.sxtsoft.gestiongastos.model.TipoGasto;
import com.sxtsoft.gestiongastos.model.Usuario;

import java.util.List;

public class AdapterRvHistoricosGastos extends RecyclerView.Adapter<AdapterRvHistoricosGastos.ViewHolder> {

    private List<Gasto> gastos;
    private LayoutInflater inflater;
    private OnDelRowGastoListener onDelRowGastoListener;

    public AdapterRvHistoricosGastos(List<Gasto> gastos, Context context, OnDelRowGastoListener onDelRowGastoListener) {

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.gastos = gastos;
        this.onDelRowGastoListener = onDelRowGastoListener;
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
        viewHolder.icono.setImageResource(gastos.get(position).getTipoGasto().getIcono());
        viewHolder.tipoGasto.setText(gastos.get(position).getTipoGasto().getNombre());
        viewHolder.fecha.setText(gastos.get(position).getFecha().toString());

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
        public FloatingActionButton borrarGasto;
        OnDelRowGastoListener onDelRowGastoListener;

        public ViewHolder(@NonNull View itemView, OnDelRowGastoListener onDelRowGastoListener) {
            super(itemView);

            this.onDelRowGastoListener = onDelRowGastoListener;

            importe = (TextView) itemView.findViewById(R.id.txtOutImporte);
            tipoGasto = (TextView) itemView.findViewById(R.id.txtOutTipoGasto);
            fecha = (TextView) itemView.findViewById(R.id.txtOutFecha);
            icono = (ImageView) itemView.findViewById(R.id.imgOutCategoria);
            usuario = (TextView) itemView.findViewById(R.id.txtOutUsuario);
            borrarGasto = (FloatingActionButton) itemView.findViewById(R.id.btnDelGastoHistorico);

            borrarGasto.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDelRowGastoListener.OnDelRowGasto(getAdapterPosition());
        }
    }

    public void setGastos(List<Gasto> gastos){
        this.gastos = gastos;
    }

    public interface OnDelRowGastoListener{
        void OnDelRowGasto(int position);
    }
}
