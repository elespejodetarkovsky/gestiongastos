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

    public Usuario read(String userName);

    public boolean delete(String userName); //elimina usuario en funcion de su nombre de usuario

    public Usuario update(Usuario usuario);

    public Usuario insert(Usuario usuario);

}
