package com.sxtsoft.gestiongastos.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxtsoft.gestiongastos.R;
import com.sxtsoft.gestiongastos.database.Utilidades;
import com.sxtsoft.gestiongastos.model.Categoria;

public class AdapterRvCategorias extends RecyclerView.Adapter <AdapterRvCategorias.ViewHolder> {


    private Context context;
    private Categoria[] categorias;
    private LayoutInflater inflater;
    OnCategoriasListener onCategoriasListener;


    public AdapterRvCategorias(Context context, Categoria[] categorias) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.categorias = categorias;
        this.context = context;

        Log.d("**","CONSTRUCTOR ADAPTERCATEGORIA");
    }

    public void setOnCategoriaListener(OnCategoriasListener onCategoriaListener){
        this.onCategoriasListener = onCategoriaListener;
    }


    //es la vista vacia segun el esquema de viewholder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private TextView textView;
        private ImageView imgCategorias;


        public ViewHolder(View view) {
            super(view);

            Log.d("**","CONSTRUCTOR VIEWHOLDER");

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
    public AdapterRvCategorias.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.row_categorias, null);

        Log.d("**","ESTAMOS EN ONCREATEVIEWHOLDER");

        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override //aquí personalizamos el elemento en concreto
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Log.d("**","ESTAMOS EN ONBINDVIEWHOLDER");

        
        viewHolder.textView.setText(categorias[position].toString());
        viewHolder.imgCategorias.setImageResource(Utilidades.getIdResourcesDrawable(context,
                categorias[position].toString().toLowerCase()));

    }


    @Override
    public int getItemCount() {
        return categorias.length;
    }


    public interface OnCategoriasListener{
        void OnCategoriaClick(int position);
    }

}