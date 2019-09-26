package com.sxtsoft.gestiongastos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sxtsoft.gestiongastos.Adapters.AdapterRVCategorias;
import com.sxtsoft.gestiongastos.Adapters.AdapterRVTiposGastosSel;
import com.sxtsoft.gestiongastos.Interfaces.AlarmaServices;
import com.sxtsoft.gestiongastos.Interfaces.TipoGastoServices;
import com.sxtsoft.gestiongastos.Interfaces.impl.AlarmaServicesImpl;
import com.sxtsoft.gestiongastos.Interfaces.impl.TipoGastoServicesImpl;
import com.sxtsoft.gestiongastos.model.Alarma;
import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.TipoGasto;
import com.sxtsoft.gestiongastos.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class AltaAlarma extends AppCompatActivity implements AdapterRVCategorias.OnCategoriasListener
                                                , AdapterRVTiposGastosSel.OnTipoGastoListener {

    private TextView cancelar;
    private TextView guardar;
    private EditText importe;
    private EditText nameAlarma;
    private RecyclerView rvCategorias;
    private RecyclerView rvTipoGastos;
    private Categoria[] categorias;
    private List<TipoGasto> tipoGastos;
    private AlarmaServices alarmaServicesImpl;
    private Categoria categoriaSel;
    private String UserId;
    private TipoGasto tipoGasto;
    private SeekBar seekBar;
    private TextView cicloDias;

    private AdapterRVCategorias.OnCategoriasListener onCategoriasListener;

    //adapters
    private AdapterRVCategorias adapterRVCategorias;
    private AdapterRVTiposGastosSel adapterRVTiposGastosSel;



    private TipoGastoServices tipoGastoServicesImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_alarma);

        SharedPreferences sharedPreferences = getSharedPreferences("MisPrefs", Context.MODE_PRIVATE);
        UserId = sharedPreferences.getString("UserID","");

        //cargo la lista de categorias
        //en el array
        categorias = Categoria.values();
        tipoGastos = new ArrayList<>();

        tipoGastoServicesImpl = new TipoGastoServicesImpl(this);
        alarmaServicesImpl = new AlarmaServicesImpl(this);

        buildVistas();

        buildRecyclersView();

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAlarma();
                finish();
            }
        });



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress +=1;
                cicloDias.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void buildVistas(){
        cancelar = (TextView) findViewById(R.id.lblCancelAltaAlarma);
        guardar = (TextView) findViewById(R.id.lblGuardarAlarma);
        importe = (EditText) findViewById(R.id.txtImporteAlarma);
        nameAlarma = (EditText) findViewById(R.id.txtNameAlarma);
        rvCategorias = (RecyclerView) findViewById(R.id.rvCategoriaAlarma);
        rvTipoGastos = (RecyclerView) findViewById(R.id.rvTipoGastosAlarma);
        seekBar = (SeekBar) findViewById(R.id.seekCicloDias);
        cicloDias = (TextView) findViewById(R.id.txtClicloDias);
    }

    private void buildRecyclersView(){

        rvCategorias.setHasFixedSize(true);
        rvTipoGastos.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManagerCategoria = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManagerTipoGasto = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

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


        categoriaSel = categorias[position];
        tipoGastos = tipoGastoServicesImpl.getTiposByCategoria(categoriaSel);

        //notifico el cambio al adaptador
        adapterRVTiposGastosSel.setTiposGastos(tipoGastos);

        //actualizo la lista
        adapterRVTiposGastosSel.notifyDataSetChanged();

    }

    @Override
    public void OnTipoGastoClick(int position) {


        tipoGasto = tipoGastos.get(position);

    }

    private void saveAlarma(){

        //creo usuario temporal
        Usuario usuario = new Usuario();

        try {
            usuario.setCodigo(Long.parseLong(UserId));
        } catch (Exception e){
            Log.d("**", e.getMessage());
            usuario.setCodigo((long)0);
        }

        Alarma alarma = new Alarma(nameAlarma.getText().toString(),
                Double.parseDouble(importe.getText().toString()), categoriaSel,tipoGasto,false,true,usuario);

        alarmaServicesImpl.create(alarma);

    }
}
