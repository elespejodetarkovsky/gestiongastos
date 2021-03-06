package com.sxtsoft.gestiongastos.Adapters;

import android.content.Context;
import android.media.AudioTrack;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sxtsoft.gestiongastos.R;
import com.sxtsoft.gestiongastos.model.TipoGasto;

import java.text.ParseException;
import java.util.List;

public class AdapterRVTiposGastosSel extends RecyclerView.Adapter<AdapterRVTiposGastosSel.ViewHolder> {


    private List<TipoGasto> tiposGastos;
    private LayoutInflater inflater;
    private OnTipoGastoListener onTipoGastoListener;

    public AdapterRVTiposGastosSel(Context context, List<TipoGasto> tiposGastos, OnTipoGastoListener onTipoGastoListener) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tiposGastos = tiposGastos;
        this.onTipoGastoListener = onTipoGastoListener;

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

        return new ViewHolder(view, onTipoGastoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRVTiposGastosSel.ViewHolder viewHolder, int position) {
        viewHolder.nombreTipoGasto.setText(tiposGastos.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return tiposGastos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView nombreTipoGasto;
        OnTipoGastoListener onTipoGastoListener;


        public ViewHolder(@NonNull View itemView, OnTipoGastoListener onTipoGastoListener) {
            super(itemView);

            this.onTipoGastoListener = onTipoGastoListener;

            nombreTipoGasto = (TextView) itemView.findViewById(R.id.txtTipoGastoSel);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            //el try es porque manejo una fecha???
            //preguntar

            Log.d("**", "Estoy en el click");
                onTipoGastoListener.OnTipoGastoClick(getAdapterPosition());

        }
    }

    public interface OnTipoGastoListener{
        void OnTipoGastoClick(int position);
    }

}
