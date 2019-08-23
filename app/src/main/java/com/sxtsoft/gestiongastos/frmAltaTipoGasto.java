package com.sxtsoft.gestiongastos;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.sxtsoft.gestiongastos.Adapters.AdapterRVCategorias;
import com.sxtsoft.gestiongastos.Adapters.AdapterRVTiposGatos;
import com.sxtsoft.gestiongastos.Interfaces.impl.TipoGastoServicesImpl;
import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.TipoGasto;

import java.util.ArrayList;

public class frmAltaTipoGasto extends AppCompatActivity {

    private static int[] iconos;

    static {

        iconos = new int [] {R.drawable.fijos,
                R.drawable.suministros,
                R.drawable.transporte,
                R.drawable.comida,
                R.drawable.vestimenta,
                R.drawable.otros};
    }


    private RecyclerView rvCategorrias;
    private RecyclerView rvTiposDatos;
    private EditText nombreTipoGasto;
    private String nombreTipoDatoSel;
    private Categoria categoriaSel;
    private int iconoSel;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManagerTD;

    private FloatingActionButton btnAddTipoDatos;

    //contendrá las filas de los tipos de datos del usuario
    //que eventualmente se pasarán a la base de datos.
    private ArrayList<TipoGasto> tipoGastos;
    private TipoGasto tipoGasto;
    private TipoGastoServicesImpl tipoGastoServicesImpl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_alta_tipo_gasto);

        final Categoria categorias[] = Categoria.values();

        tipoGastoServicesImpl = new TipoGastoServicesImpl(this);

        nombreTipoGasto = (EditText) findViewById(R.id.txtInNombreTipoGasto);

        //inicio el array con los tipos de datos
        tipoGastos = new ArrayList<>();


        //identifico el recycleview
        rvCategorrias = (RecyclerView) findViewById(R.id.rvCategoriaGasto);
        rvTiposDatos = (RecyclerView) findViewById(R.id.rvTiposDatos);

        //identifico el botón de agregado
        btnAddTipoDatos = (FloatingActionButton) findViewById(R.id.btnAddTipoGasto);


        rvCategorrias.setHasFixedSize(true);
        rvTiposDatos.setHasFixedSize(true);


        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvCategorrias.setLayoutManager(layoutManager);

        layoutManagerTD = new LinearLayoutManager(this);
        rvTiposDatos.setLayoutManager(layoutManagerTD);

        AdapterRVCategorias adapterRVCategorias = new AdapterRVCategorias(this, categorias, iconos);
        final AdapterRVTiposGatos adapterRVTiposGatos = new AdapterRVTiposGatos(this,tipoGastos);

        rvTiposDatos.setAdapter(adapterRVTiposGatos);


        rvCategorrias.setAdapter(adapterRVCategorias);




        adapterRVCategorias.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = rvCategorrias.getChildAdapterPosition(v);

                categoriaSel = categorias[pos];
                nombreTipoDatoSel = nombreTipoGasto.getText().toString();
                iconoSel = iconos[pos];



                Log.d("**", categoriaSel + " " + nombreTipoDatoSel + " " + iconoSel);
            }
        });

        btnAddTipoDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                tipoGasto = new TipoGasto(nombreTipoDatoSel, categoriaSel, iconoSel);
                addTipoDato(tipoGasto, adapterRVTiposGatos);

                //borro la casilla del nombre
                nombreTipoGasto.setText("");
                //nombreTipoGasto.setFocusable(true);

            }
        });



    }

    /*Esta funcion agregará un tipo de datos
    a nuestra lista de tipos personalizados
     */

    private void addTipoDato(TipoGasto tipoGasto, AdapterRVTiposGatos adapterRVTiposGatos){
        tipoGastos.add(0, tipoGasto);
        adapterRVTiposGatos.notifyDataSetChanged();


        //cargaré a la base de datos el objeto creado
        tipoGastoServicesImpl.create(tipoGasto);
    }
}
