package com.sxtsoft.gestiongastos;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sxtsoft.gestiongastos.Adapters.AdapterRVCategorias;
import com.sxtsoft.gestiongastos.Adapters.AdapterRVTiposGastosSel;
import com.sxtsoft.gestiongastos.Adapters.AdapterRVTiposGatos;
import com.sxtsoft.gestiongastos.Adapters.AdapterRvHistoricosGastos;
import com.sxtsoft.gestiongastos.Interfaces.GastoServices;
import com.sxtsoft.gestiongastos.Interfaces.TipoGastoServices;
import com.sxtsoft.gestiongastos.Interfaces.UsuarioServices;
import com.sxtsoft.gestiongastos.Interfaces.impl.GastoServicesImpl;
import com.sxtsoft.gestiongastos.Interfaces.impl.TipoGastoServicesImpl;
import com.sxtsoft.gestiongastos.Interfaces.impl.UsuarioServicesImpl;
import com.sxtsoft.gestiongastos.database.Utilidades;
import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.Gasto;
import com.sxtsoft.gestiongastos.model.Gender;
import com.sxtsoft.gestiongastos.model.TipoGasto;
import com.sxtsoft.gestiongastos.model.Usuario;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class frmAltaGasto extends AppCompatActivity implements AdapterRVCategorias.OnCategoriasListener,
                            AdapterRVTiposGastosSel.OnTipoGastoListener, AdapterRvHistoricosGastos.OnDelRowGastoListener {

    private static int[] iconos;

    static {

        iconos = new int [] {R.drawable.fijos,
                R.drawable.suministros,
                R.drawable.transporte,
                R.drawable.comida,
                R.drawable.vestimenta,
                R.drawable.otros};
    }

    private TipoGastoServices tipoGastoServicesImpl;
    private GastoServices gastoServicesImpl;
    private UsuarioServices usuarioServicesImpl;

    private RecyclerView rvCategorrias;
    private RecyclerView rvTiposGastosSel;
    private RecyclerView rvHistorialGastos;
    private RecyclerView.LayoutManager layoutManagerCat;
    private RecyclerView.LayoutManager layoutManagerTG;
    private RecyclerView.LayoutManager layoutManagerHistoricG;

    private EditText mImporte;
    private TextView fecha;
    private List<TipoGasto> tiposGastos;
    private List<Gasto> mGastos;

    //private Categoria categoriaSel;
    private AdapterRVCategorias mAdapterRvCategorias;
    private AdapterRVTiposGastosSel mAdapterRvTiposGastosSel;
    private AdapterRvHistoricosGastos mAdapterRvHistoricosGastos;

    private Categoria[] mCategorias;
    private Categoria categoriaSel;
    private SharedPreferences sharedPreferences;
    private long userID;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_alta_gasto);

        tipoGastoServicesImpl = new TipoGastoServicesImpl(this);
        gastoServicesImpl = new GastoServicesImpl(this);

        mCategorias = Categoria.values();
        tiposGastos = new ArrayList<TipoGasto>();
        mGastos = new ArrayList<Gasto>();

        //leo el usuario que se encuentra logeado
        sharedPreferences = getSharedPreferences("MisPrefs", Context.MODE_PRIVATE);
        userID =  Long.parseLong(sharedPreferences.getString("UserID","-1"));

        //crearé un usuario sólo para
        //pasarle el id a la base de datos
        usuario = new Usuario();
        usuario.setCodigo(userID);

        buildRecyclersView();

        //reconozco los objetos
        mImporte = (EditText) findViewById(R.id.txtInImporte);
        fecha = (TextView) findViewById(R.id.txtFecha);


        //coloco la fecha*********************************************************
        fecha.setText(Utilidades.dateToString(new Date()));
        //*************************************************************************


    }

    @Override
    public void OnCategoriaClick(int position) {

        //cargo los tipos de gastos en funcion de la categoria seleccionada
        categoriaSel = mCategorias[position];
        tiposGastos = tipoGastoServicesImpl.getTiposByCategoria(categoriaSel);

        mAdapterRvTiposGastosSel.setTiposGastos(tiposGastos);

        mAdapterRvTiposGastosSel.notifyDataSetChanged(); //actualizo la lista

        Log.d("**", "click categoria" + tiposGastos.get(0).getNombre());

    }

    @Override
    public void OnTipoGastoClick(int position) {
        //obtendré la posición del item de tipo
        //de gasto, que corresponderá a una categoria

        Date date;

        double importe = Double.parseDouble(mImporte.getText().toString());
        TipoGasto tipoGasto = tiposGastos.get(position);

        date = Utilidades.stringToDate(fecha.getText().toString());

        Gasto gasto = new Gasto(importe, usuario, tipoGasto, date, categoriaSel);

        mGastos.add(0, gasto);

        mAdapterRvHistoricosGastos.notifyItemInserted(0);

        Gasto gastoCreado = gastoServicesImpl.create(gasto);

        if (gastoCreado != null){
            Toast.makeText(getBaseContext(),"Gasto agregado con id " + gastoCreado.getCodigo(),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(),"Ha habido un error", Toast.LENGTH_SHORT).show();
        }

        //Log.d("****",this.getClass().getSimpleName());

    }

    private void buildRecyclersView(){

        rvCategorrias = (RecyclerView) findViewById(R.id.rvCategoriaGasto);
        rvTiposGastosSel = (RecyclerView) findViewById(R.id.rvTipoGastoSel);
        rvHistorialGastos = (RecyclerView) findViewById(R.id.rvHistorialGastos);

        rvCategorrias.setHasFixedSize(true);
        rvTiposGastosSel.setHasFixedSize(true);
        rvHistorialGastos.setHasFixedSize(true);

        layoutManagerCat = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerTG = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerHistoricG = new LinearLayoutManager(this);

        rvCategorrias.setLayoutManager(layoutManagerCat);
        rvTiposGastosSel.setLayoutManager(layoutManagerTG);
        rvHistorialGastos.setLayoutManager(layoutManagerHistoricG);

        mAdapterRvCategorias = new AdapterRVCategorias(this, Categoria.values(), iconos, this);
        mAdapterRvTiposGastosSel = new AdapterRVTiposGastosSel(this, tiposGastos, this);
        mAdapterRvHistoricosGastos = new AdapterRvHistoricosGastos(mGastos, this, this);


        rvCategorrias.setAdapter(mAdapterRvCategorias);
        rvTiposGastosSel.setAdapter(mAdapterRvTiposGastosSel);
        rvHistorialGastos.setAdapter(mAdapterRvHistoricosGastos);


    }

    @Override
    public void OnDelRowGasto(int position) {
        Log.d("**", "has hecho click en borrar " + position);
    }
}
