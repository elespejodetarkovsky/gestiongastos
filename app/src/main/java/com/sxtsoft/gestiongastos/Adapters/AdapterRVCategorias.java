package com.sxtsoft.gestiongastos.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxtsoft.gestiongastos.R;
import com.sxtsoft.gestiongastos.model.Categoria;

public class AdapterRVCategorias extends RecyclerView.Adapter <AdapterRVCategorias.ViewHolder> {


    private Context context;
    private Categoria[] categorias;
    private int[] iconos;
    private LayoutInflater inflater;
    protected View.OnClickListener onClickListener;


    public AdapterRVCategorias(Context context, Categoria[] categorias, int[] iconos) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.categorias = categorias;
        this.iconos = iconos;
    }

    //es la vista vacia segun el esquema de viewholder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public ImageView imgCategorias;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.txtRowCategoria);
            imgCategorias = view.findViewById(R.id.imgRowCategoria);
        }


    }


    @NonNull
    @Override //creamos la vista sin personalizar
    public AdapterRVCategorias.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.row_categorias, null);
        view.setOnClickListener(onClickListener); //con esto crearemos el evento click de cada item

        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override //aquí personalizamos el elemento en concreto
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        viewHolder.imgCategorias.setImageResource(iconos[position]);
        viewHolder.textView.setText(categorias[position].toString());

    }


    @Override
    public int getItemCount() {
        return categorias.length;
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}