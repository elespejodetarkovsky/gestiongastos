package com.sxtsoft.gestiongastos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.sxtsoft.gestiongastos.Adapters.AdapterRVCategorias;
import com.sxtsoft.gestiongastos.Adapters.AdapterRVTiposGastosSel;
import com.sxtsoft.gestiongastos.Adapters.AdapterRVTiposGatos;
import com.sxtsoft.gestiongastos.Interfaces.TipoGastoServices;
import com.sxtsoft.gestiongastos.Interfaces.impl.TipoGastoServicesImpl;
import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.TipoGasto;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class AltaAlarma extends AppCompatActivity implements AdapterRVCategorias.OnCategoriasListener
                                                , AdapterRVTiposGastosSel.OnTipoGastoListener {

    private TextView cancelar;
    private TextView guardar;
    private EditText importe;
    private EditText nameAlarma;
    private RecyclerView rvDias;
    private RecyclerView rvCategorias;
    private RecyclerView rvTipoGastos;
    private Categoria[] categorias;
    private List<TipoGasto> tipoGastos;

    private AdapterRVCategorias.OnCategoriasListener onCategoriasListener;

    //adapters
    private AdapterRVCategorias adapterRVCategorias;
    private AdapterRVTiposGastosSel adapterRVTiposGastosSel;


    //private RecyclerView.LayoutManager layoutManager;
    //private RecyclerView.LayoutManager layoutManagerTD;

    private TipoGastoServices tipoGastoServicesImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_alarma);

        //cargo la lista de categorias
        //en el array
        categorias = Categoria.values();
        tipoGastos = new ArrayList<>();

        tipoGastoServicesImpl = new TipoGastoServicesImpl(this);

        buildVistas();

        buildRecyclersView();

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void buildVistas(){
        cancelar = (TextView) findViewById(R.id.lblCancelAltaAlarma);
        guardar = (TextView) findViewById(R.id.lblGuardarAlarma);
        importe = (EditText) findViewById(R.id.txtImporteAlarma);
        nameAlarma = (EditText) findViewById(R.id.txtNameAlarma);
        rvDias = (RecyclerView) findViewById(R.id.rvCicloMes);
        rvCategorias = (RecyclerView) findViewById(R.id.rvCategoriaAlarma);
        rvTipoGastos = (RecyclerView) findViewById(R.id.rvTipoGastosAlarma);
    }

    private void buildRecyclersView(){

        rvDias.setHasFixedSize(true);
        rvCategorias.setHasFixedSize(true);
        rvTipoGastos.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManagerDias = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManagerCategoria = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManagerTipoGasto = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rvDias.setLayoutManager(layoutManagerDias);
        rvCategorias.setLayoutManager(layoutManagerCategoria);
        rvTipoGastos.setLayoutManager(layoutManagerTipoGasto);

        //se crean los adaptadores
        adapterRVCategorias = new AdapterRVCategorias(this, categorias, this);
        adapterRVTiposGastosSel = new AdapterRVTiposGastosSel(this, tipoGastos, this);

        rvCategorias.setAdapter(adapterRVCategorias);
        rvTipoGastos.setAdapter(adapterRVTiposGastosSel);

    }

    @Override
    public void OnCategoriaClick(int position) {

        int index = 0;

        Categoria categoriaSel = categorias[position];
        tipoGastos = tipoGastoServicesImpl.getTiposByCategoria(categoriaSel);

        //notifico el cambio al adaptador
        adapterRVTiposGastosSel.setTiposGastos(tipoGastos);

        //actualizo la lista
        adapterRVTiposGastosSel.notifyDataSetChanged();

    }

    @Override
    public void OnTipoGastoClick(int position) {

    }
}
