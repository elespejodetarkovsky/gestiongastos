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

public class frmAltaTipoGasto extends AppCompatActivity implements AdapterRVCategorias.OnCategoriasListener {

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
    private Categoria[] categorias;
    private int iconoSel;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManagerTD;

    private FloatingActionButton btnAddTipoGastos;

    //contendrá las filas de los tipos de datos del usuario
    //que eventualmente se pasarán a la base de datos.
    private ArrayList<TipoGasto> tipoGastos;
    private TipoGasto tipoGasto;
    private TipoGastoServicesImpl tipoGastoServicesImpl;
    private AdapterRVCategorias adapterRVCategorias;
    private AdapterRVTiposGatos mAdapterRVTiposGatos;

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


    @Override
    public void OnCategoriaClick(int position) {
        //position me dará la posicion de la categoria en este caso

        int index = 0;

        categoriaSel = categorias[position];
        nombreTipoDatoSel = nombreTipoGasto.getText().toString();
        iconoSel = iconos[position];

        this.tipoGastos.add(index, new TipoGasto(nombreTipoDatoSel, categoriaSel,iconoSel));

        //notifico el cambio al adaptador
        this.mAdapterRVTiposGatos.notifyItemInserted(index);

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
        adapterRVCategorias = new AdapterRVCategorias(this, categorias, iconos, this);
        mAdapterRVTiposGatos = new AdapterRVTiposGatos(this,tipoGastos);

        rvTiposDatos.setAdapter(mAdapterRVTiposGatos);

        rvCategorrias.setAdapter(adapterRVCategorias);

    }

}
