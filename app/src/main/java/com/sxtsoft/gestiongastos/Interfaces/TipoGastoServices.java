package com.sxtsoft.gestiongastos.Interfaces;

import com.sxtsoft.gestiongastos.model.TipoGasto;

import java.util.List;

public interface TipoGastoServices {

        /*
    Esta interface deberá dar de alta, eliminar y
    listar usuarios
     */

    public List<TipoGasto> getAll();

    public TipoGasto agregarTipo(TipoGasto tipoGasto);

    public TipoGasto readTipo(Long codigo);

    public boolean borrarTipoGasto(Long codigo); //elimina usuario en funcion de su nombre de usuario

    public TipoGasto update(TipoGasto tipoGasto);


}
