package com.sxtsoft.gestiongastos.Interfaces;

public interface CRUDServices<T> {
    /*
    Interface que obligará a la aplicacion
    de operaciones CRUD
     */


    public T create(T object);

    public T read(Long codigo);

    public boolean delete(Long codigo); //elimina usuario en funcion de su nombre de usuario

    public T update(T Object);

}
