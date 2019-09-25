package com.sxtsoft.gestiongastos.Interfaces.impl;

import com.sxtsoft.gestiongastos.Interfaces.AlarmaServices;
import com.sxtsoft.gestiongastos.model.Alarma;

public class AlarmaServicesImpl implements AlarmaServices {
    @Override
    public boolean desactivarAlarma(long id) {
        return false;
    }

    @Override
    public Alarma create(Alarma alarma) {
        return null;
    }

    @Override
    public Alarma read(Long codigo) {
        return null;
    }

    @Override
    public boolean delete(Long codigo) {
        return false;
    }

    @Override
    public Alarma update(Alarma Object) {
        return null;
    }
}
