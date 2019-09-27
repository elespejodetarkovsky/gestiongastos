package com.sxtsoft.gestiongastos.Interfaces;

import android.widget.ListView;

import com.sxtsoft.gestiongastos.model.Alarma;

import java.util.List;

public interface AlarmaServices extends  CRUDServices<Alarma, Long> {


    public boolean desactivarAlarma(long id); //pasará la alarma a estado inactivo

    public List<Alarma> getAll();

}
