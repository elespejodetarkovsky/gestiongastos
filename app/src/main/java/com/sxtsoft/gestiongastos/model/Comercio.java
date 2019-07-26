package com.sxtsoft.gestiongastos.model;

public class Comercio {

    private Long codigo;
    private String nombre;
    private long latitud;
    private long longitud;
    private String icono;

    public Comercio(Long codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Comercio(Long codigo, String nombre, long latitud, long longitud, String icono) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
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

    public long getLatitud() {
        return latitud;
    }

    public void setLatitud(long latitud) {
        this.latitud = latitud;
    }

    public long getLongitud() {
        return longitud;
    }

    public void setLongitud(long longitud) {
        this.longitud = longitud;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    @Override
    public String toString() {
        return "Comercio{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", icono='" + icono + '\'' +
                '}';
    }
}
