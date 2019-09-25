package com.sxtsoft.gestiongastos.Interfaces;

import com.sxtsoft.gestiongastos.model.Alarma;

public interface AlarmaServices extends  CRUDServices<Alarma, Long> {


    public boolean desactivarAlarma(long id); //pasará la alarma a estado inactivo

}
