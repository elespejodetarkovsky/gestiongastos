package com.sxtsoft.gestiongastos.Interfaces.impl;

import android.content.Context;

import com.sxtsoft.gestiongastos.Interfaces.TipoGastoServices;
import com.sxtsoft.gestiongastos.database.DataBaseHelperTipoGasto;
import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.TipoGasto;

import java.util.List;

public class TipoGastoServicesImpl implements TipoGastoServices {

    private DataBaseHelperTipoGasto dataBaseHelperTipoDato;

    public TipoGastoServicesImpl(Context context){
        dataBaseHelperTipoDato = new DataBaseHelperTipoGasto(context);
    }

    @Override
    public List<TipoGasto> getAll() {
        return null;
    }

    @Override
    public List<TipoGasto> getTiposByCategoria(Categoria categoria) {
        return dataBaseHelperTipoDato.getTipoGastoByCategoria(categoria);
    }

    @Override
    public List<TipoGasto> setListaTipoGastos(List<TipoGasto> tipoGastos) {
        return null;
    }

    @Override
    public TipoGasto randomTipoGasto() {
        return dataBaseHelperTipoDato.randomTipoGasto();
    }


    @Override
    public TipoGasto create(TipoGasto tipoGasto) {
        return dataBaseHelperTipoDato.create(tipoGasto);
    }

    @Override
    public TipoGasto read(Long codigo) {
        return null;
    }

    @Override
    public boolean delete(Long codigo) {
        return false;
    }

    @Override
    public TipoGasto update(TipoGasto Object) {
        return null;
    }
}
