package com.sxtsoft.gestiongastos.Interfaces;

import com.sxtsoft.gestiongastos.model.Usuario;

import java.util.List;

public interface UsuarioServices {

    /*
    Esta interface deberá dar de alta, eliminar y
    listar usuarios
     */

    public List<Usuario> getAll();
    public Usuario Crear(Usuario usuario);

    public boolean borrar(String userName); //elimina usuario en funcion de su nombre de usuario

}
