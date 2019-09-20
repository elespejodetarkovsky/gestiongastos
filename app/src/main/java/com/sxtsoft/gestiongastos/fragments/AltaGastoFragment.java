package com.sxtsoft.gestiongastos.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.sxtsoft.gestiongastos.Adapters.AdapterRVCategorias;
import com.sxtsoft.gestiongastos.Adapters.AdapterRVTiposGastosSel;
import com.sxtsoft.gestiongastos.Adapters.AdapterRvHistoricosGastos;
import com.sxtsoft.gestiongastos.Interfaces.GastoServices;
import com.sxtsoft.gestiongastos.Interfaces.TipoGastoServices;
import com.sxtsoft.gestiongastos.Interfaces.UsuarioServices;
import com.sxtsoft.gestiongastos.Interfaces.impl.GastoServicesImpl;
import com.sxtsoft.gestiongastos.Interfaces.impl.TipoGastoServicesImpl;
import com.sxtsoft.gestiongastos.R;
import com.sxtsoft.gestiongastos.database.Utilidades;
import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.Gasto;
import com.sxtsoft.gestiongastos.model.TipoGasto;
import com.sxtsoft.gestiongastos.model.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AltaGastoFragment extends Fragment implements AdapterRVCategorias.OnCategoriasListener,
        AdapterRVTiposGastosSel.OnTipoGastoListener, AdapterRvHistoricosGastos.OnDelRowGastoListener{

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
    public AltaGastoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alta_gasto, null);

        tipoGastoServicesImpl = new TipoGastoServicesImpl(getContext());
        gastoServicesImpl = new GastoServicesImpl(getContext());

        mCategorias = Categoria.values();
        tiposGastos = new ArrayList<TipoGasto>();

        //la siguiente funcion recoje de la base de datos
        //los ultimos n gastos realizados
        mGastos = cargarUltimosGastos(5);

        //leo el usuario que se encuentra logeado
        sharedPreferences = getActivity().getSharedPreferences("MisPrefs", Context.MODE_PRIVATE);
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





        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void OnCategoriaClick(int position) {

    }

    @Override
    public void OnTipoGastoClick(int position) {

    }

    @Override
    public void OnDelRowGasto(long idGasto, int position) {

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
}
