package com.sxtsoft.gestiongastos.Interfaces.impl;

import android.content.Context;

import com.sxtsoft.gestiongastos.Interfaces.GastoServices;
import com.sxtsoft.gestiongastos.database.DataBaseHelperGasto;
import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.Comercio;
import com.sxtsoft.gestiongastos.model.Gasto;
import com.sxtsoft.gestiongastos.model.Grupo;
import com.sxtsoft.gestiongastos.model.TipoGasto;
import com.sxtsoft.gestiongastos.model.Usuario;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class GastoServicesImpl implements GastoServices {

    private DataBaseHelperGasto dataBaseHelperGasto;

    public GastoServicesImpl(Context context){
        dataBaseHelperGasto = new DataBaseHelperGasto(context);
    }

    @Override
    public List<Gasto> getAll() {
        return null;
    }

    @Override
    public Gasto gastoById(Long codigo) {
        return null;
    }

    @Override
    public List<Gasto> gastosByCommerce(Comercio comercio) {
        return null;
    }

    @Override
    public List<Gasto> gastosByType(TipoGasto tipoGasto) {
        return null;
    }

    @Override
    public List<Gasto> gastosBetweenDates(Date fecha1, Date fecha2) {
        return null;
    }

    @Override
    public Map<String, Double> totalGastosBetweenDatesAndCategorias(Date fecha1, Date fecha2) {
        return dataBaseHelperGasto.totalGastosByDatesAndCategorias(fecha1, fecha2);
    }

    @Override
    public List<Gasto> gastosByArea(long latitud, long longitud) {
        return null;
    }

    @Override
    public List<Gasto> gastoByUser(Usuario usuario) {
        return null;
    }

    @Override
    public List<Gasto> gastoByGroup(Grupo grupo) {
        return null;
    }


    @Override
    public List<Gasto> gastosByCategoria(Categoria categoria) {
        return dataBaseHelperGasto.gastosByCategoria(categoria);
    }

    @Override
    public double SumaGastosByCategoria(Categoria categoria) {
        return dataBaseHelperGasto.SumaGastosByCategoria(categoria);
    }

    @Override
    public List<Gasto> gastoByMonthInAYear(String month, int year) {
        return null;
    }

    @Override
    public List<Gasto> gastoByMonthBetweenYears(String month, int year1, int year2) {
        return null;
    }

    @Override
    public List<Gasto> obtenerUltimosGastos(int limite) {
        return dataBaseHelperGasto.obtenerUltimosGastos(limite);
    }

    @Override
    public Gasto create(Gasto object) {
        return dataBaseHelperGasto.create(object);
    }

    @Override
    public Gasto read(Long codigo) {
        return null;
    }

    @Override
    public boolean delete(Long codigo) {
        return dataBaseHelperGasto.deleteGasto(codigo);
    }

    @Override
    public Gasto update(Gasto Object) {
        return null;
    }
}