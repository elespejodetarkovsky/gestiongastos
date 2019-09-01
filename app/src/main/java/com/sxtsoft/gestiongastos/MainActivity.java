package com.sxtsoft.gestiongastos;

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

        usuarios = usuarioServicesImpl.getAll();

        if (usuarios != null){
            //existe al menos un usuario
            //aun no sabemos si se ha dado de alta
            Log.d("**", "tenemos un usuario" + usuarios.get(0).getNombre());


        }

        Log.d("**","estoy en oncreate");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("**", "estoy en onstart");
    }
}
