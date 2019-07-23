package com.sxtsoft.gestiongastos.Interfaces.impl;

import android.content.Context;
import android.database.Cursor;

import com.sxtsoft.gestiongastos.Interfaces.UsuarioServices;
import com.sxtsoft.gestiongastos.database.DatabaseHelper;
import com.sxtsoft.gestiongastos.model.Gender;
import com.sxtsoft.gestiongastos.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioServicesImpl implements UsuarioServices {

    private DatabaseHelper databaseHelper;

    public UsuarioServicesImpl(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    @Override
    public List<Usuario> getAll() {

        Cursor cursor = databaseHelper.getAll();

        List<Usuario> usuarios = new ArrayList<Usuario>();

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                //Integer codigo = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String apellido = cursor.getString(2);
                String userName = cursor.getString(3);
                Gender genero = Gender.valueOf(cursor.getString(4));
                String passWord = cursor.getString(5);

                Usuario usuario = new Usuario(nombre,apellido,userName,(Gender) genero,passWord);

                //el código de la base de datos...para que no haya discrepancias.

                usuarios.add(usuario);

            }

        }
        return usuarios;
    }

    @Override
    public Usuario Crear(Usuario usuario) {
        return null;
    }

    @Override
    public boolean borrar(String userName) {
        return false;
    }
}
