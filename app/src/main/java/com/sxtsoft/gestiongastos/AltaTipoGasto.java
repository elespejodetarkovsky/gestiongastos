package com.sxtsoft.gestiongastos;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;

import com.sxtsoft.gestiongastos.Adapters.AdapterRvCategorias;
import com.sxtsoft.gestiongastos.Adapters.AdapterRvTiposGastos;
import com.sxtsoft.gestiongastos.Interfaces.TipoGastoServices;
import com.sxtsoft.gestiongastos.Interfaces.impl.TipoGastoServicesImpl;
import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.TipoGasto;

import java.util.ArrayList;

public class AltaTipoGasto extends AppCompatActivity {

    private RecyclerView rvCategorrias;
    private RecyclerView rvTiposDatos;
    private EditText nombreTipoGasto;
    private String nombreTipoDatoSel;
    private Categoria categoriaSel;
    private Categoria[] categorias;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManagerTD;

    private FloatingActionButton btnAddTipoGastos;

    //contendrá las filas de los tipos de datos del usuario
    //que eventualmente se pasarán a la base de datos.
    private ArrayList<TipoGasto> tipoGastos;
    private TipoGasto tipoGasto;
    private TipoGastoServices tipoGastoServicesImpl;
    private AdapterRvCategorias adapterRVCategorias;
    private AdapterRvTiposGastos mAdapterRvTiposGastos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_alta_tipo_gasto);

        categorias = Categoria.values();

        tipoGastos = new ArrayList<>(); //inicio el array con los tipos de datos

        tipoGastoServicesImpl = new TipoGastoServicesImpl(this);

        nombreTipoGasto = (EditText) findViewById(R.id.txtInNombreTipoGasto);
        btnAddTipoGastos = (FloatingActionButton) findViewById(R.id.btnAddTipoGasto); //boton agregar, por ahora

        buildRecyclersView();


    }


    public void cargaTiposGastos(int position) {
        //position me dará la posicion de la categoria en este caso

        int index = 0;

        categoriaSel = categorias[position];
        nombreTipoDatoSel = nombreTipoGasto.getText().toString();

        this.tipoGastos.add(index, new TipoGasto(nombreTipoDatoSel, categoriaSel));

        //notifico el cambio al adaptador
        this.mAdapterRvTiposGastos.notifyItemInserted(index);

        //cargaré a la base de datos el objeto creado
        tipoGastoServicesImpl.create(tipoGasto);

    }


    private void buildRecyclersView(){

        //identifico el recycleview
        rvCategorrias = (RecyclerView) findViewById(R.id.rvCategoriaGasto);
        rvTiposDatos = (RecyclerView) findViewById(R.id.rvTiposDatos);

        rvCategorrias.setHasFixedSize(true);
        rvTiposDatos.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvCategorrias.setLayoutManager(layoutManager);

        layoutManagerTD = new LinearLayoutManager(this);
        rvTiposDatos.setLayoutManager(layoutManagerTD);

        //se crean los adaptadores
        adapterRVCategorias = new AdapterRvCategorias(this, categorias);

        adapterRVCategorias.setOnCategoriaListener(new AdapterRvCategorias.OnCategoriasListener() {
            @Override
            public void OnCategoriaClick(int position) {
                cargaTiposGastos(position);
            }
        });


        mAdapterRvTiposGastos = new AdapterRvTiposGastos(this,tipoGastos);

        rvTiposDatos.setAdapter(mAdapterRvTiposGastos);

        rvCategorrias.setAdapter(adapterRVCategorias);

    }

}
