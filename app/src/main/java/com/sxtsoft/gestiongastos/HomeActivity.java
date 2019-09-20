package com.sxtsoft.gestiongastos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.sxtsoft.gestiongastos.Interfaces.TipoGastoServices;
import com.sxtsoft.gestiongastos.Interfaces.UsuarioServices;
import com.sxtsoft.gestiongastos.Interfaces.impl.TipoGastoServicesImpl;
import com.sxtsoft.gestiongastos.Interfaces.impl.UsuarioServicesImpl;
import com.sxtsoft.gestiongastos.fragments.LogInFragment;
import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.TipoGasto;
import com.sxtsoft.gestiongastos.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private UsuarioServices usuarioServicesImpl;
    private List<Usuario> usuarios;
    private TipoGastoServices tipoGastoServicesImpl;
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

            String paramsTipoGasto = sharedPreferences.getString("TiposGastos", "");

            if (paramsTipoGasto.equals("")){
                /*
                se realiza la carga por default en
                caso de que no se hallan realizado
                 */

                tipoGastos = new ArrayList<>();
                listaTipoGastosTest();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("TiposGastos", "ok");
                editor.commit();
            }

            usuarios = usuarioServicesImpl.getAll();

            if (usuarios != null){
                /*
                hay un usuario pero no sabemos
                se se ha dado de alta
                 */

                String usuario = sharedPreferences.getString("UserName","");

                //en caso de estar en la lista iría a la activity principal
                //de lo contrario tendría que logearse
                if (usuario.equals("")){
                /*
                Inicio la actividad de login
                 */

                    loadFragment(new LogInFragment());

                } else{
                    //ir a la pantalla principal

//                    Intent altaGasto = new Intent(this, frmAltaGasto.class);
//
//                    startActivity(altaGasto);

                }

            } else {

                //TO DO
                Intent altaUserIntent = new Intent(this, frmAltaUsuario.class);
                startActivity(altaUserIntent);
            }

        }

        private void listaTipoGastosTest(){

            tipoGastos.add(new TipoGasto("GAS", Categoria.SUMINISTROS, R.drawable.suministros));
            tipoGastos.add(new TipoGasto("ELECTRICIDAD", Categoria.SUMINISTROS, R.drawable.suministros));
            tipoGastos.add(new TipoGasto("AGUA", Categoria.SUMINISTROS, R.drawable.suministros));
            tipoGastos.add(new TipoGasto("NETFLIX", Categoria.SUMINISTROS, R.drawable.suministros));
            tipoGastos.add(new TipoGasto("INTERNET", Categoria.SUMINISTROS, R.drawable.suministros));
            tipoGastos.add(new TipoGasto("HIPOTECA", Categoria.FIJOS, R.drawable.fijos));
            tipoGastos.add(new TipoGasto("ALQUILER", Categoria.FIJOS, R.drawable.fijos));
            tipoGastos.add(new TipoGasto("LIDL", Categoria.COMIDA, R.drawable.fijos));
            tipoGastos.add(new TipoGasto("CAPRABO", Categoria.COMIDA, R.drawable.fijos));
            tipoGastos.add(new TipoGasto("MERCADO", Categoria.COMIDA, R.drawable.fijos));
            tipoGastos.add(new TipoGasto("CALZADO", Categoria.VESTIMENTA, R.drawable.fijos));
            tipoGastos.add(new TipoGasto("DEPORTIVA", Categoria.VESTIMENTA, R.drawable.fijos));
            tipoGastos.add(new TipoGasto("RENFE", Categoria.TRANSPORTES, R.drawable.fijos));
            tipoGastos.add(new TipoGasto("METRO", Categoria.TRANSPORTES, R.drawable.fijos));
            tipoGastos.add(new TipoGasto("GYM", Categoria.OTROS, R.drawable.fijos));

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
        return false;
    }
}

