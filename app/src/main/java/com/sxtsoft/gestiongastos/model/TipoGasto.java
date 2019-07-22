package com.sxtsoft.gestiongastos.model;

import java.util.Date;

public class TipoGasto {

    private Long codigo;
    private String nombre;
    private Categoria categoria;
    private String icono;

    public TipoGasto () {

    }

    public TipoGasto (String nombre, Categoria categoria, String icono) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.icono = icono;
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


    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }


    @Override
    public String toString() {
        return "TipoGasto [codigo=" + codigo + ", nombre=" + nombre + ", categoria=" + categoria + "icono=" + icono + "]";
    }





}