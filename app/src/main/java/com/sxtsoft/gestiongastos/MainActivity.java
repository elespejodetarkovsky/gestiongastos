package com.sxtsoft.gestiongastos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sxtsoft.gestiongastos.Interfaces.impl.UsuarioServicesImpl;
import com.sxtsoft.gestiongastos.model.Usuario;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private UsuarioServicesImpl usuarioServicesImpl;
    private List<Usuario> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
        Debería tener en cuenta si se ha logeado un usuario
        ir directamente al gasto...intent con el usuario

        de lo contrario continuar la carga
        ...probablemente deberia ser antes del create
        evaluar la base de datos
        1- verificar la existencia de usuarios
        2- verificar que se haya logeado
         */

        usuarioServicesImpl = new UsuarioServicesImpl(this);

        usuarios = usuarioServicesImpl.getAll();

        if (usuarios != null){
            //existe al menos un usuario
            //aun no sabemos si se ha dado de alta
            Log.d("**", "tenemos un usuario: " + usuarios.get(0).getNombre());

            /*
            si existe un usuario verificamos que este esté logeado
            para eso leemos en las shared preference que esté el usuario
             */

            SharedPreferences sharedPreferences = getSharedPreferences("MisPrefs",Context.MODE_PRIVATE);
            String usuario = sharedPreferences.getString("UserName","");

            //en caso de estar en la lista iría a la activity principal
            //de lo contrario tendría que logearse
            if (usuario.equals("")){
                /*
                Inicio la actividad de login
                 */

                Intent logInActivity = new Intent(this, LogIn.class);

                startActivity(logInActivity);

            } else{
                //TO DO
                //ir a la pantalla principal
                Log.d("**", "vamos a la pantalla principal (alta Gasto?)");

                Intent altaGasto = new Intent(this, frmAltaGasto.class);

                startActivity(altaGasto);
            }

        } else {

            //TO DO
            Intent altaUserIntent = new Intent(this, frmAltaUsuario.class);
            startActivity(altaUserIntent);
        }

    }

}
