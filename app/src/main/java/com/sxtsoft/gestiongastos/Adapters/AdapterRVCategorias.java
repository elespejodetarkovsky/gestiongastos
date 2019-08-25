package com.sxtsoft.gestiongastos.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    private OnCategoriasListener onCategoriasListener;

    protected View.OnClickListener onClickListener;


    public AdapterRVCategorias(Context context, Categoria[] categorias, int[] iconos, OnCategoriasListener onCategoriasListener) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.categorias = categorias;
        this.iconos = iconos;
        this.onCategoriasListener = onCategoriasListener;
    }


    //es la vista vacia segun el esquema de viewholder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView textView;
        public ImageView imgCategorias;
        OnCategoriasListener onCategoriasListener;


        public ViewHolder(View view, OnCategoriasListener onCategoriasListener) {
            super(view);

            this.onCategoriasListener = onCategoriasListener;

            textView = view.findViewById(R.id.txtRowCategoria);
            imgCategorias = view.findViewById(R.id.imgRowCategoria);

            //estamos aplicando el listener a la vista que pasará al onclick
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCategoriasListener.OnCategoriaClick(getAdapterPosition());
        }
    }


    @NonNull
    @Override //creamos la vista sin personalizar
    public AdapterRVCategorias.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.row_categorias, null);
        view.setOnClickListener(onClickListener); //con esto crearemos el evento click de cada item

        ViewHolder vh = new ViewHolder(view, onCategoriasListener);

        return vh;
    }

    @Override //aquí personalizamos el elemento en concreto
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        int rowIndex;
        viewHolder.imgCategorias.setImageResource(iconos[position]);
        viewHolder.textView.setText(categorias[position].toString());

    }


    @Override
    public int getItemCount() {
        return categorias.length;
    }


    public interface OnCategoriasListener{
        void OnCategoriaClick(int position);
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;

    }
}