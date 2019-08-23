package com.sxtsoft.gestiongastos;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sxtsoft.gestiongastos.Adapters.AdapterRVCategorias;
import com.sxtsoft.gestiongastos.Adapters.AdapterRVTiposGastosSel;
import com.sxtsoft.gestiongastos.Interfaces.impl.TipoGastoServicesImpl;
import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.TipoGasto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class frmAltaGasto extends AppCompatActivity {

    private static int[] iconos;

    static {

        iconos = new int [] {R.drawable.fijos,
                R.drawable.suministros,
                R.drawable.transporte,
                R.drawable.comida,
                R.drawable.vestimenta,
                R.drawable.otros};
    }

    private TipoGastoServicesImpl tipoGastoServicesImpl;
    private RecyclerView rvCategorrias;
    private RecyclerView rvTiposGastosSel;
    private RecyclerView rvHistorialGastos;
    private RecyclerView.LayoutManager layoutManagerCat;
    private RecyclerView.LayoutManager layoutManagerTG;
    private RecyclerView.LayoutManager layoutManagerHistoricG;

    private EditText importe;
    private TextView fecha;
    private List<TipoGasto> tiposGastos;
    //private Categoria categoriaSel;
    private FloatingActionButton addGasto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_alta_gasto);

        final Categoria[] categorias = Categoria.values();
        tiposGastos = new ArrayList<TipoGasto>();


        //reconozco los objetos
        rvCategorrias = (RecyclerView) findViewById(R.id.rvCategoriaGasto);
        rvTiposGastosSel = (RecyclerView) findViewById(R.id.rvTipoGastoSel);
        rvHistorialGastos = (RecyclerView) findViewById(R.id.rvHistorialGastos);
        importe = (EditText) findViewById(R.id.txtInImporte);
        fecha = (TextView) findViewById(R.id.txtFecha);
        addGasto = (FloatingActionButton) findViewById(R.id.btnAddGasto);


        rvCategorrias.setHasFixedSize(true);
        rvTiposGastosSel.setHasFixedSize(true);
        rvHistorialGastos.setHasFixedSize(true);

        layoutManagerCat = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerTG = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerHistoricG = new LinearLayoutManager(this);

        rvCategorrias.setLayoutManager(layoutManagerCat);
        rvTiposGastosSel.setLayoutManager(layoutManagerTG);
        rvHistorialGastos.setLayoutManager(layoutManagerHistoricG);

        final AdapterRVCategorias adapterRVCategorias = new AdapterRVCategorias(this, Categoria.values(), iconos);

        final AdapterRVTiposGastosSel adapterRVTiposGastosSel = new AdapterRVTiposGastosSel(this, tiposGastos);

        rvCategorrias.setAdapter(adapterRVCategorias);
        rvTiposGastosSel.setAdapter(adapterRVTiposGastosSel);

        tipoGastoServicesImpl = new TipoGastoServicesImpl(this);

        //coloco la fecha
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String strDate = dateFormat.format(date);

        fecha.setText(strDate);


        adapterRVCategorias.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = rvCategorrias.getChildAdapterPosition(view);

                tiposGastos = tipoGastoServicesImpl.getTiposByCategoria(categorias[position]);
                Log.d("**", tiposGastos.get(1).getNombre());

                adapterRVTiposGastosSel.setTiposGastos(tiposGastos);
                //rvTiposGastosSel.setAdapter(adapterRVTiposGastosSel);
                adapterRVTiposGastosSel.notifyDataSetChanged();
            }
        });

    }
}
