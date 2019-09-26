package com.sxtsoft.gestiongastos.model;

import java.util.Date;

public class Alarma {

    private Long codigo;
    private String nombre;
    private double importe;
    private Categoria categoria;
    private TipoGasto tipoGasto;
    private boolean visto; //en caso de que el usario lo "vea" no se repetirá hasta el otro ciclo
    private boolean estado; //indicará si está activada o desactivada
    private Usuario usuario;


    public Alarma() {
    }

    public Alarma(String nombre, double importe, Categoria categoria, TipoGasto tipoGasto, boolean visto, boolean estado, Usuario usuario) {
        this.nombre = nombre;
        this.importe = importe;
        this.categoria = categoria;
        this.tipoGasto = tipoGasto;
        this.visto = visto;
        this.estado = estado;
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public TipoGasto getTipoGasto() {
        return tipoGasto;
    }

    public void setTipoGasto(TipoGasto tipoGasto) {
        this.tipoGasto = tipoGasto;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Alarma{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", importe=" + importe +
                ", categoria=" + categoria +
                ", tipoGasto=" + tipoGasto +
                ", visto=" + visto +
                ", estado=" + estado +
                '}';
    }
}
