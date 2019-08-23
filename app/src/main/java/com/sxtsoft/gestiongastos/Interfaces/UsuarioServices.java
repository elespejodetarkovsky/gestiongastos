package com.sxtsoft.gestiongastos.Interfaces;

import com.sxtsoft.gestiongastos.model.Usuario;

import java.util.List;

public interface UsuarioServices extends CRUDServices<Usuario>{

    /*
    Esta interface deberá dar de alta, eliminar y
    listar usuarios
     */

    public List<Usuario> getAll();

}
