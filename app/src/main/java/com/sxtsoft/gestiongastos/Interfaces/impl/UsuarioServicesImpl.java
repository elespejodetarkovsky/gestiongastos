package com.sxtsoft.gestiongastos.Interfaces.impl;

import android.content.Context;
import android.database.Cursor;

import com.sxtsoft.gestiongastos.Interfaces.UsuarioServices;
import com.sxtsoft.gestiongastos.database.DatabaseHelper;
import com.sxtsoft.gestiongastos.database.DatabaseHelperUsuario;
import com.sxtsoft.gestiongastos.model.Gender;
import com.sxtsoft.gestiongastos.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioServicesImpl implements UsuarioServices {

    private DatabaseHelperUsuario databaseHelperUsuario;

    public UsuarioServicesImpl(Context context){
        databaseHelperUsuario = new DatabaseHelperUsuario(context);
    }

    @Override
    public List<Usuario> getAll() {

        Cursor cursor = databaseHelperUsuario.getAll();

        List<Usuario> usuarios = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                Long id = cursor.getLong(0);
                String nombre = cursor.getString(1);
                String apellido = cursor.getString(2);
                String userName = cursor.getString(3);
                Gender genero = Gender.valueOf(cursor.getString(4));
                String pass = cursor.getString(5);

                Usuario usuario = new Usuario(nombre,apellido,userName,genero,pass,null);
                usuario.setCodigo(id);


                //el código de la base de datos...para que no haya discrepancias.

                usuarios.add(usuario);

            }

            return usuarios;

        }
        return null;
    }

    @Override
    public long logIn(String userName, String clave) {
        return databaseHelperUsuario.validarUser(userName, clave);
    }

    @Override
    public Usuario UsuarioById(long userID) {
        return databaseHelperUsuario.readUserById(userID);
    }


    @Override
    public Usuario create(Usuario usuario) {

        return databaseHelperUsuario.create(usuario);

    }

    @Override
    public Usuario read(Long codigo) {
        return null;
    }

    @Override
    public boolean delete(Long codigo) {
        return false;
    }

    @Override
    public Usuario update(Usuario usuario) {
        return null;
    }


}
