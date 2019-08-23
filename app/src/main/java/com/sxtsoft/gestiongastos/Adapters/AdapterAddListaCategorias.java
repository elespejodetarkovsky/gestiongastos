package com.sxtsoft.gestiongastos.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxtsoft.gestiongastos.R;
import com.sxtsoft.gestiongastos.model.Categoria;

import java.util.Map;

public class AdapterAddListaCategorias extends BaseAdapter {

    private LayoutInflater inflater = null;
    private Context contexto;
    //private Categoria categoria;
    private Categoria[] categorias;
    private int[] iconos;

    public AdapterAddListaCategorias(Context contexto, Categoria[] categorias, int[] iconos){
        this.contexto = contexto;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
        this.categorias = categorias;
        this.iconos = iconos;
    }

    @Override
    public int getCount() {
        return iconos.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final View vista = inflater.inflate(R.layout.row_categorias, null);


        TextView categoria = (TextView) vista.findViewById(R.id.txtRowCategoria);
        ImageView imagenCategoria = (ImageView) vista.findViewById(R.id.imgRowCategoria);


        categoria.setText(categorias[position].toString());
        imagenCategoria.setImageResource(iconos[position]);

        Log.d("**", categorias.toString() + " " + iconos[position]);

//        if (position % 2 == 0){
//            vista.setBackgroundColor(Color.rgb(226,165,43));
//        }
//        else {
//            vista.setBackgroundColor(Color.WHITE);
//        }
        return vista;
    }

}
