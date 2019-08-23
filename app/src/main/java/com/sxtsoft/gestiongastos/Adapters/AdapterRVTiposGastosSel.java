package com.sxtsoft.gestiongastos.Adapters;

import android.content.Context;
import android.media.AudioTrack;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sxtsoft.gestiongastos.R;
import com.sxtsoft.gestiongastos.model.TipoGasto;

import java.util.List;

public class AdapterRVTiposGastosSel extends RecyclerView.Adapter<AdapterRVTiposGastosSel.ViewHolder> {


    private List<TipoGasto> tiposGastos;
    private LayoutInflater inflater;

    public AdapterRVTiposGastosSel(Context context, List<TipoGasto> tiposGastos) {

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.tiposGastos = tiposGastos;

    }

    public List<TipoGasto> getTiposGastos() {
        return tiposGastos;
    }

    public void setTiposGastos(List<TipoGasto> tiposGastos) {
        this.tiposGastos = tiposGastos;
    }

    @NonNull
    @Override
    public AdapterRVTiposGastosSel.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.row_tipos_gastos_sel, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRVTiposGastosSel.ViewHolder viewHolder, int position) {
        viewHolder.nombreTipoGasto.setText(tiposGastos.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return tiposGastos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nombreTipoGasto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreTipoGasto = (TextView) itemView.findViewById(R.id.txtTipoGastoSel);

        }
    }
}
