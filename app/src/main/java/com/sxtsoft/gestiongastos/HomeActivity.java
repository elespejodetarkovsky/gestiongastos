package com.sxtsoft.gestiongastos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.sxtsoft.gestiongastos.Interfaces.GastoServices;
import com.sxtsoft.gestiongastos.Interfaces.TipoGastoServices;
import com.sxtsoft.gestiongastos.Interfaces.UsuarioServices;
import com.sxtsoft.gestiongastos.Interfaces.impl.GastoServicesImpl;
import com.sxtsoft.gestiongastos.Interfaces.impl.TipoGastoServicesImpl;
import com.sxtsoft.gestiongastos.Interfaces.impl.UsuarioServicesImpl;
import com.sxtsoft.gestiongastos.database.Utilidades;
import com.sxtsoft.gestiongastos.fragments.AltaGastoFragment;
import com.sxtsoft.gestiongastos.fragments.GraficaFragment;
import com.sxtsoft.gestiongastos.fragments.LogInFragment;
import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.Gasto;
import com.sxtsoft.gestiongastos.model.TipoGasto;
import com.sxtsoft.gestiongastos.model.Usuario;

import java.security.cert.CertificateRevokedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private UsuarioServices usuarioServicesImpl;
    private List<Usuario> usuarios;
    private TipoGastoServices tipoGastoServicesImpl;
    private GastoServices gastoServicesImpl;
    private SharedPreferences sharedPreferences; //para cargar las preferencias de la app documentarlas!!
    private ArrayList<TipoGasto> tipoGastos;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        /*
        Debería tener en cuenta si se ha logeado un usuario
        ir directamente al gasto...intent con el usuario

        de lo contrario continuar la carga
        ...probablemente deberia ser antes del create
        evaluar la base de datos
        1- verificar la existencia de usuarios
        2- verificar que se haya logeado
         */

            sharedPreferences = getSharedPreferences("MisPrefs", Context.MODE_PRIVATE);

            usuarioServicesImpl = new UsuarioServicesImpl(this);
            tipoGastoServicesImpl = new TipoGastoServicesImpl(this);
            gastoServicesImpl = new GastoServicesImpl(this);


            usuarios = usuarioServicesImpl.getAll();

            /*
                Realizo una carga de tipos de gastos
                y Gastos para realizar las pruebas
            */

            String paramsTipoGasto = sharedPreferences.getString("TiposGastos", "");


            if (paramsTipoGasto.equals("")){

                listaTipoGastosTest();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("TiposGastos", "ok");
                editor.commit();
            }


            if (usuarios != null){
                /*
                hay un usuario pero no sabemos
                si se ha dado de alta
                 */

                String usuario = sharedPreferences.getString("UserName","");


                //en caso de estar en la lista iría a la activity principal
                //de lo contrario tendría que logearse
                if (usuario.equals("")){


                 /*
                Inicio la actividad de login
                 */

                 Intent logInIntent = new Intent(this, LogIn.class);
                 startActivity(logInIntent);


                } else{
                    //ir a la pantalla principal

                    loadFragment(new AltaGastoFragment());

                }

            } else {

                //TO DO
                Intent altaUserIntent = new Intent(this, frmAltaUsuario.class);
                startActivity(altaUserIntent);
            }

        }

        private void listaTipoGastosTest(){

            tipoGastos = new ArrayList<>();

            tipoGastos.add(new TipoGasto("GAS", Categoria.SUMINISTROS));
            tipoGastos.add(new TipoGasto("ELECTRICIDAD", Categoria.SUMINISTROS));
            tipoGastos.add(new TipoGasto("AGUA", Categoria.SUMINISTROS));
            tipoGastos.add(new TipoGasto("NETFLIX", Categoria.SUMINISTROS));
            tipoGastos.add(new TipoGasto("INTERNET", Categoria.SUMINISTROS));
            tipoGastos.add(new TipoGasto("HIPOTECA", Categoria.FIJOS));
            tipoGastos.add(new TipoGasto("ALQUILER", Categoria.FIJOS));
            tipoGastos.add(new TipoGasto("LIDL", Categoria.COMIDA));
            tipoGastos.add(new TipoGasto("CAPRABO", Categoria.COMIDA));
            tipoGastos.add(new TipoGasto("MERCADO", Categoria.COMIDA));
            tipoGastos.add(new TipoGasto("CALZADO", Categoria.VESTIMENTA));
            tipoGastos.add(new TipoGasto("DEPORTIVA", Categoria.VESTIMENTA));
            tipoGastos.add(new TipoGasto("RENFE", Categoria.TRANSPORTES));
            tipoGastos.add(new TipoGasto("METRO", Categoria.TRANSPORTES));
            tipoGastos.add(new TipoGasto("GYM", Categoria.OTROS));

            for(TipoGasto tipoGasto:tipoGastos){
                tipoGastoServicesImpl.create(tipoGasto);
            }

        }



    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;

        switch (menuItem.getItemId()){

            case R.id.bottom_nav_add:
                fragment = new AltaGastoFragment();

                break;

            case R.id.bottom_nav_graficas:
                fragment = new GraficaFragment();
                break;

        }

        return loadFragment(fragment);
    }

}

