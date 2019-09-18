package com.sxtsoft.gestiongastos.Interfaces;

import com.sxtsoft.gestiongastos.model.Gasto;
import com.sxtsoft.gestiongastos.model.Grupo;

import java.util.List;

public interface GrupoServices extends CRUDServices<Grupo, Long> {

    public List<Grupo> getAll();

}
