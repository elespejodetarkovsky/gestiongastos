package com.sxtsoft.gestiongastos.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxtsoft.gestiongastos.R;
import com.sxtsoft.gestiongastos.database.Utilidades;
import com.sxtsoft.gestiongastos.model.TipoGasto;

import java.util.ArrayList;

public class AdapterRVTiposGatos extends RecyclerView.Adapter<AdapterRVTiposGatos.ViewHolder> {


    private Context context;
    private ArrayList<TipoGasto> tipoGastos;
    private LayoutInflater inflater;


    public AdapterRVTiposGatos(Context context, ArrayList<TipoGasto> tipoGastos) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tipoGastos = tipoGastos;
    }



    /*Este metodo
    creará (inflará) el holder con todos los elementos
    , lo hará inflando todos los elementos, no solo los que cambian.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.row_tipos_gastos, null);

        return new ViewHolder(view);
    }

    //se personaliza el elemento en concreto...lo que cambia
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        String strIcono = tipoGastos.get(position).getIcono();

        viewHolder.imgIconoCategoria.setImageResource(Utilidades.getIdResourcesDrawable(context, strIcono));
        viewHolder.txtCategoria.setText(tipoGastos.get(position).getCategoria().toString());
        viewHolder.txtNombre.setText(tipoGastos.get(position).getNombre());

    }

    @Override
    public int getItemCount() {
        return tipoGastos.size();
    }


    /*
    armo la carcaza de la fila, que será
    modificada (los companentes que luego se inflará
    y será rellenada
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtNombre;
        public TextView txtCategoria;
        public ImageView imgIconoCategoria;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNombre = (TextView) itemView.findViewById(R.id.txtRowNombreTipoDato);
            txtCategoria = (TextView) itemView.findViewById(R.id.txtRowCategoria);
            imgIconoCategoria = (ImageView) itemView.findViewById(R.id.imgRowCategoria);


        }
    }
}
