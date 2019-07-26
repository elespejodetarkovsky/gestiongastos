package com.sxtsoft.gestiongastos.Interfaces;

import com.sxtsoft.gestiongastos.model.Caracteristica;
import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.Comercio;
import com.sxtsoft.gestiongastos.model.Gasto;
import com.sxtsoft.gestiongastos.model.TipoGasto;
import com.sxtsoft.gestiongastos.model.Usuario;

import java.util.Date;
import java.util.List;

public interface GastoServices {

    public List<Gasto> getAll(); //busca todos los gastos...probablemente no sea utilizada.

    public Gasto addGasto(Gasto gasto);

    public Gasto gastoById(Long codigo); //devuelvo un gasto por su código

    public List<Gasto> gastosByCommerce(Comercio comercio); //devuelve los gastos realizados por comercio

    public List<Gasto> gastosByType(TipoGasto tipoGasto);

    public List<Gasto> gastosBetweenDates(Date fecha1, Date fecha2);

    public List<Gasto> gastosByArea(long latitud, long longitud); //devolvería los gastos realizados en un area determinada

    public List<Gasto> gastoByUser(Usuario usuario);

    public List<Gasto> gastoByCaracteristica(Caracteristica caracteristica); //ESENCIAL, NO ESENCIAL

    public List<Gasto> gastoByCategoria(Categoria categoria); //SERVICIOS, CORRIENTES

    public List<Gasto> gastoByMonthInAYear(String month, int year);

    public List<Gasto> gastoByMonthBetweenYears(String month, int year1, int year2);

    public boolean borrarTipoGasto(Long codigo); //elimina usuario en funcion de su nombre de usuario

    public TipoGasto update(TipoGasto tipoGasto);

}
