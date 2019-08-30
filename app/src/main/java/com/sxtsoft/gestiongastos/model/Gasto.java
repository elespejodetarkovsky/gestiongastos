package com.sxtsoft.gestiongastos.model;

import java.util.Date;

public class Gasto {

    private Long codigo;
    private double importe;
    private Usuario usuario;
    private TipoGasto tipoGasto;
    private Date fecha;
    private String detalle;
    private long longitud;
    private long latitud;


    public Gasto() {

    }

    public Gasto(double importe, Usuario usuario, TipoGasto tipoGasto, Date fecha) {
        this.importe = importe;
        this.usuario = usuario;
        this.tipoGasto = tipoGasto;
        this.fecha = fecha;
    }


    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoGasto getTipoGasto() {
        return tipoGasto;
    }

    public void setTipoGasto(TipoGasto tipoGasto) {
        this.tipoGasto = tipoGasto;
    }

    public long getLongitud() {
        return longitud;
    }

    public void setLongitud(long longitud) {
        this.longitud = longitud;
    }

    public long getLatitud() {
        return latitud;
    }

    public void setLatitud(long latitud) {
        this.latitud = latitud;
    }


    @Override
    public String toString() {
        return "Gasto{" +
                "codigo=" + codigo +
                ", importe=" + importe +
                ", usuario=" + usuario +
                ", tipoGasto=" + tipoGasto +
                ", fecha=" + fecha +
                ", detalle='" + detalle + '\'' +
                ", longitud=" + longitud +
                ", latitud=" + latitud +
                "'}'";
    }
}