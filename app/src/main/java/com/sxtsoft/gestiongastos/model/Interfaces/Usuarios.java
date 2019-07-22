package com.sxtsoft.gestiongastos.model.Interfaces;

import com.sxtsoft.gestiongastos.model.Gender;
import com.sxtsoft.gestiongastos.model.Usuario;

import java.util.List;

public interface Usuarios {

    /*
    Esta interface deberá dar de alta, eliminar y
    listar usuarios
     */

    public List<Usuario> getAll();
    public Usuario altaUsuario(String nombre, String apellido, String userName,
                               Gender genero, String password);

    public boolean delUsuario(String userName); //elimina usuario en funcion de su nombre de usuario

}
