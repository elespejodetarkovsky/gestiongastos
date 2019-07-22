package com.sxtsoft.gestiongastos.model;

public class Usuario {

    private Long codigo;
    private String nombre;
    private String apellido;
    private String userName; //será su identificador único
    private Gender genero;
    private String password;

    public Usuario() {

    }

    public Usuario (String nombre, String apellido, String userName,
                    Gender genero, String password){

        this.nombre = nombre;
        this.apellido = apellido;
        this.userName = userName;
        this.genero = genero;
        this.password = password;

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Gender getGenero() {
        return genero;
    }

    public void setGenero(Gender genero) {
        this.genero = genero;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario [codigo=" + codigo + ", nombre=" + nombre + ", apellido=" + apellido + ", userName=" + userName + "]";
    }


}
