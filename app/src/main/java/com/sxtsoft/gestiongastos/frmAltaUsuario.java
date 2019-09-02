package com.sxtsoft.gestiongastos;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.RadioButton;

import com.sxtsoft.gestiongastos.Interfaces.impl.UsuarioServicesImpl;
import com.sxtsoft.gestiongastos.database.DatabaseHelperUsuario;
import com.sxtsoft.gestiongastos.model.Gender;
import com.sxtsoft.gestiongastos.model.Grupo;
import com.sxtsoft.gestiongastos.model.Usuario;

public class frmAltaUsuario extends AppCompatActivity {

    private EditText userName;
    private EditText nombre;
    private EditText apellido;
    private EditText passWord;
    private Gender genero;
    private RadioButton male;
    private RadioButton female;
    private Grupo grupo;
    private Usuario usuario;
    private FloatingActionButton altaUsuario;
    private Button btnCrear;
    //private DatabaseHelperUsuario databaseHelperUsuario;
    private UsuarioServicesImpl usuarioServicesImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_alta_usuario);

        usuarioServicesImpl = new UsuarioServicesImpl(this);

        buildObjects();

        //evaluaré cual es el genero seleccionado
        genero = Gender.MASCULINO;


        //creo un grupo para pasarlo al objeto
        grupo = new Grupo("users");
        grupo.setCodigo((long)1);

        altaUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                crearUsuario(view);
            }
        });
        //Coloco evento en los check de genero
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genero = Gender.MASCULINO;
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genero = Gender.FEMENINO;
            }
        });

    }

    private void crearUsuario(View view){

        //creo el usuario para fijarlo en la db
        usuario = new Usuario(nombre.getText().toString(), apellido.getText().toString(),
                userName.getText().toString(), genero, passWord.getText().toString(), grupo);

        //le paso el usuario al objeto que lo agregará
        //a la base de datos
        usuarioServicesImpl.create(usuario);
        //databaseHelperUsuario = new DatabaseHelperUsuario(view.getContext());

        //usuario = databaseHelperUsuario.create(usuario);

        Log.d("**", "Id usuario: " + usuario.getCodigo());

    }

    private void buildObjects(){

        userName = (EditText) findViewById(R.id.txtUserName);
        nombre = (EditText) findViewById(R.id.txtName);
        apellido = (EditText) findViewById(R.id.txtApellido);
        passWord = (EditText) findViewById(R.id.txtPassword);
        btnCrear = (Button) findViewById(R.id.btnCrearUsuario);
        altaUsuario = (FloatingActionButton) findViewById(R.id.btnCreateUser);
        male = (RadioButton) findViewById(R.id.rdoMasculino);
        female = (RadioButton) findViewById(R.id.rdoFemenino);

    }
}
