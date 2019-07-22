package com.sxtsoft.gestiongastos.model;

import java.util.List;

public class Grupo {

    private Long codigo;
    private String nombre;
    private List<Usuario> usuarios;


    public Grupo() {

    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public String toString() {
        return "Grupo [codigo=" + codigo + ", nombre=" + nombre + ", usuarios=" + usuarios + "]";
    }



}