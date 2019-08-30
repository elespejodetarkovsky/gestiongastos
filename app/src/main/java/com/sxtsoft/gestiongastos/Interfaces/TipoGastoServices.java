package com.sxtsoft.gestiongastos.Interfaces;


import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.TipoGasto;

import java.util.List;

public interface TipoGastoServices extends CRUDServices<TipoGasto> {

        /*
    Esta interface deberá dar de alta, eliminar y
    listar usuarios
     */

    public List<TipoGasto> getAll();

    public List<TipoGasto> getTiposByCategoria(Categoria categoria);

    public List<TipoGasto> setListaTipoGastos(List<TipoGasto> tipoGastos);

}
