package com.sxtsoft.gestiongastos.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sxtsoft.gestiongastos.Interfaces.impl.UsuarioServicesImpl;
import com.sxtsoft.gestiongastos.R;
import com.sxtsoft.gestiongastos.frmAltaGasto;

public class LogInFragment extends Fragment {

    private EditText nombreUsuario;
    private EditText clave;
    private Button logIn;
    private UsuarioServicesImpl usuarioServicesImpl;
    private TextView errorLogIn;

    public LogInFragment() {
    }


    private void buildObjects(View view){
        nombreUsuario = (EditText) view.findViewById(R.id.txtUserNameLogIn);
        clave = (EditText) view.findViewById(R.id.txtClaveLogIn);
        logIn =(Button) view.findViewById(R.id.btbLogIn);
        errorLogIn = (TextView) view.findViewById(R.id.txtErrorLogIn);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_log_in,null);

//        buildObjects(view);
//
//        usuarioServicesImpl = new UsuarioServicesImpl(getContext());
//
//
//        logIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                long usuarioID = usuarioServicesImpl.logIn(nombreUsuario.getText().toString(), clave.getText().toString());
//
//                Log.d("**", String.valueOf(usuarioID));
//
//                //si existe el usuario me devolverá su id
//                //entonces lo logeo y lo agrego a las sharedpreference
//
//                if (usuarioID != -1) {
//                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MisPrefs", Context.MODE_PRIVATE);
//
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("UserName", nombreUsuario.getText().toString());
//                    editor.putString("UserID", String.valueOf(usuarioID));
//                    editor.commit();
//
//                    Intent altaGasto = new Intent(view.getContext(), frmAltaGasto.class);
//                    startActivity(altaGasto);
//
//                    Log.d("**","Usuario logeado");
//                } else {
//                    errorLogIn.setText("error en login...");
//                    Log.d("**", "el usuario no existe");
//                }
//
//            }
//        });

        return view;

    }

}
