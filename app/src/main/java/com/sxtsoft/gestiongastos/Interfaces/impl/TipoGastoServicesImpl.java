package com.sxtsoft.gestiongastos.Interfaces.impl;

import android.content.Context;

import com.sxtsoft.gestiongastos.Interfaces.TipoGastoServices;
import com.sxtsoft.gestiongastos.database.DataBaseHelperTipoDato;
import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.TipoGasto;

import java.util.List;

public class TipoGastoServicesImpl implements TipoGastoServices {

    private DataBaseHelperTipoDato dataBaseHelperTipoDato;

    public TipoGastoServicesImpl(Context context){
        dataBaseHelperTipoDato = new DataBaseHelperTipoDato(context);
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
