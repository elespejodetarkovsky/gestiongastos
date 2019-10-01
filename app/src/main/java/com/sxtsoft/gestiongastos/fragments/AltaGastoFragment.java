package com.sxtsoft.gestiongastos.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sxtsoft.gestiongastos.Adapters.AdapterRVCategorias;
import com.sxtsoft.gestiongastos.Adapters.AdapterRVTiposGastosSel;
import com.sxtsoft.gestiongastos.Adapters.AdapterRvHistoricosGastos;
import com.sxtsoft.gestiongastos.Interfaces.AlarmaServices;
import com.sxtsoft.gestiongastos.Interfaces.GastoServices;
import com.sxtsoft.gestiongastos.Interfaces.TipoGastoServices;
import com.sxtsoft.gestiongastos.Interfaces.UsuarioServices;
import com.sxtsoft.gestiongastos.Interfaces.impl.AlarmaServicesImpl;
import com.sxtsoft.gestiongastos.Interfaces.impl.GastoServicesImpl;
import com.sxtsoft.gestiongastos.Interfaces.impl.TipoGastoServicesImpl;
import com.sxtsoft.gestiongastos.R;
import com.sxtsoft.gestiongastos.database.Utilidades;
import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.Gasto;
import com.sxtsoft.gestiongastos.model.TipoGasto;
import com.sxtsoft.gestiongastos.model.Usuario;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AltaGastoFragment extends Fragment implements AdapterRVTiposGastosSel.OnTipoGastoListener, AdapterRvHistoricosGastos.OnDelRowGastoListener{



    private TipoGastoServices tipoGastoServicesImpl;
    private GastoServices gastoServicesImpl;
    private UsuarioServices usuarioServicesImpl;
    private AlarmaServices alarmaServicesImpl;

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
    private Button btnCargarGastos;
    private TextView infoGastos;
    private TextView infoUserId;

    public AltaGastoFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alta_gasto, null);

        tipoGastoServicesImpl = new TipoGastoServicesImpl(getContext());
        gastoServicesImpl = new GastoServicesImpl(getContext());
        alarmaServicesImpl = new AlarmaServicesImpl(getContext());

        mCategorias = Categoria.values();
        tiposGastos = new ArrayList<TipoGasto>();


        //leo el usuario que se encuentra logeado
        sharedPreferences = getActivity().getSharedPreferences("MisPrefs", Context.MODE_PRIVATE);
        userID =  Long.parseLong(sharedPreferences.getString("UserID","-1"));

        //la siguiente funcion recoje de la base de datos
        //los ultimos n gastos realizados
        mGastos = cargarUltimosGastos(5, userID);

        //crearé un usuario sólo para
        //pasarle el id a la base de datos
        usuario = new Usuario();
        usuario.setCodigo(userID);

        buildRecyclersView(view);

        //reconozco los objetos
        mImporte = (EditText) view.findViewById(R.id.txtInImporte);
        fecha = (TextView) view.findViewById(R.id.txtFecha);
        btnCargarGastos = (Button) view.findViewById(R.id.btnCargaGastos);
        infoGastos = (TextView) view.findViewById(R.id.txtInfoGastos);
        infoUserId = (TextView) view.findViewById(R.id.lblUserId);


        //coloco en la eitqueta el codigo de usuario
        infoUserId.setText("UserId: " + userID);

        //coloco el valor del gasto mensual
        infoGastos.setText(String.valueOf(gastoServicesImpl.sumaGastosMesTotal()));

        //coloco la fecha*********************************************************
        fecha.setText(Utilidades.dateToString(new Date()));
        //*************************************************************************


        btnCargarGastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearGastos(30, usuario);
            }
        });




        return view;

    }

    public void cargaTiposGastos(int position) {
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
        rvHistorialGastos.scrollToPosition(0);

        Gasto gastoCreado = gastoServicesImpl.create(gasto);

        //actualizo el valor del gasto mensual
        infoGastos.setText(String.valueOf(gastoServicesImpl.sumaGastosMesTotal()));

        if (gastoCreado != null){
            Toast.makeText(getActivity(),"Gasto agregado con id " + gastoCreado.getCodigo(),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(),"Ha habido un error", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void OnDelRowGasto(long idGasto, int position) {
        Log.d("**", "has hecho click en borrar " + position + " idGasto: " + idGasto);
    }

    private void buildRecyclersView(View view){

        rvCategorrias = (RecyclerView) view.findViewById(R.id.rvCategoriaGasto);
        rvTiposGastosSel = (RecyclerView) view.findViewById(R.id.rvTipoGastoSel);
        rvHistorialGastos = (RecyclerView) view.findViewById(R.id.rvHistorialGastos);

        rvCategorrias.setHasFixedSize(true);
        rvTiposGastosSel.setHasFixedSize(true);
        rvHistorialGastos.setHasFixedSize(true);

        layoutManagerCat = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManagerTG = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManagerHistoricG = new LinearLayoutManager(getContext());

        rvCategorrias.setLayoutManager(layoutManagerCat);
        rvTiposGastosSel.setLayoutManager(layoutManagerTG);
        rvHistorialGastos.setLayoutManager(layoutManagerHistoricG);

        //mAdapterRvCategorias = new AdapterRVCategorias(getContext(), Categoria.values(), this);
        mAdapterRvCategorias = new AdapterRVCategorias(getContext(), Categoria.values());
        mAdapterRvTiposGastosSel = new AdapterRVTiposGastosSel(getContext(), tiposGastos, this);
        mAdapterRvHistoricosGastos = new AdapterRvHistoricosGastos(mGastos, getContext(), this);


        mAdapterRvCategorias.setOnCategoriaListener(new AdapterRVCategorias.OnCategoriasListener() {
            @Override
            public void OnCategoriaClick(int position) {
                cargaTiposGastos(position);
            }
        });



        rvCategorrias.setAdapter(mAdapterRvCategorias);
        rvTiposGastosSel.setAdapter(mAdapterRvTiposGastosSel);
        rvHistorialGastos.setAdapter(mAdapterRvHistoricosGastos);


    }

    private List<Gasto> cargarUltimosGastos(int limite, long userID){
        /*
        Cargo los ultimos "limite" movimientos
        que se hayan creado
         */

        return gastoServicesImpl.obtenerUltimosGastosUsuario(limite, userID);
    }

    private void crearGastos(int qGastos, Usuario usuario){
        /*
        Esta funcion creará gastos para realzar pruebas
         */

        long fechaMinima = Utilidades.dateToMilisegundos(Utilidades.stringToDate("01-01-2018 23:00"));
        long fechaActual = Utilidades.dateToMilisegundos(new Date());


        for (int i = 0; i < qGastos; i++ ){


            double importe = Math.random()*40; //genero un importe máximo de 40 euros
            DecimalFormat dc = new DecimalFormat("#.##");

            double importeTrunc = Double.parseDouble(dc.format(importe));

            TipoGasto tipoGasto = tipoGastoServicesImpl.randomTipoGasto();

            //genero una fecha (en milisegundos) que sea menor a la de hoy

            long fechaAleatoria = (long) Math.random()*(fechaActual - fechaMinima +1) + fechaMinima;

            Categoria categoria = tipoGasto.getCategoria();


            Gasto gasto = new Gasto(importeTrunc,usuario,tipoGasto, Utilidades.milisegundosToDate(fechaAleatoria),categoria);

            //lo agrego a la base de datos

            gastoServicesImpl.create(gasto);

            //TODO
            //continuar esto...
            //alarmaServicesImpl.verificarAlarmas(alarmaServicesImpl.getAll(),
            //        gastoServicesImpl.totalGastosByDatesCategoriasAndTipoGasto())


        }




    }
}
