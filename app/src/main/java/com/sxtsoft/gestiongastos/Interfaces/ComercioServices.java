package com.sxtsoft.gestiongastos.Interfaces;

import com.sxtsoft.gestiongastos.model.Comercio;

import java.util.List;

public interface ComercioServices extends CRUDServices<Comercio, Long> {

    public List<Comercio> findByName(String name);

}
