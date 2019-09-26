package com.sxtsoft.gestiongastos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sxtsoft.gestiongastos.Interfaces.impl.UsuarioServicesImpl;
import com.sxtsoft.gestiongastos.fragments.AltaGastoFragment;


public class LogIn extends AppCompatActivity {

    private EditText nombreUsuario;
    private EditText clave;
    private Button logIn;
    private UsuarioServicesImpl usuarioServicesImpl;
    private TextView errorLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        usuarioServicesImpl = new UsuarioServicesImpl(this);

        buildObjects();


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long usuarioID = usuarioServicesImpl.logIn(nombreUsuario.getText().toString(), clave.getText().toString());

                Log.d("**", String.valueOf(usuarioID));

                //si existe el usuario me devolverá su id
                //entonces lo logeo y lo agrego a las sharedpreference

                if (usuarioID != -1) {
                    SharedPreferences sharedPreferences = getSharedPreferences("MisPrefs", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("UserName", nombreUsuario.getText().toString());
                    editor.putString("UserID", String.valueOf(usuarioID));
                    editor.commit();

                    //loadFragment(new AltaGastoFragment());
                    //vuelvo a cargar el home con sus contenedores
                    //y forzar nuevamente el logIn
                    Intent homeIntent = new Intent(view.getContext(), HomeActivity.class);
                    startActivity(homeIntent);
                    finish();

                    Log.d("**","Usuario logeado");
                } else {
                    errorLogIn.setText("error en login...");
                    Log.d("**", "el usuario no existe");
                }

            }
        });

    }

    private void buildObjects(){
        nombreUsuario = (EditText) findViewById(R.id.txtUserNameLogIn);
        clave = (EditText) findViewById(R.id.txtClaveLogIn);
        logIn =(Button) findViewById(R.id.btbLogIn);
        errorLogIn = (TextView) findViewById(R.id.txtErrorLogIn);

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
}

