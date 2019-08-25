package com.sxtsoft.gestiongastos.database;

import android.util.Log;

public class Utilidades {

        /*
    Creacion de la table de usuarios
     */

    public static final String USUARIOS_TABLE = "USUARIOS";

    public static final String USUARIOS_COL_0 = "USUARIO_ID";
    public static final String USUARIOS_COL_1 = "NOMBRE";
    public static final String USUARIOS_COL_2 = "APELLIDO";
    public static final String USUARIOS_COL_3 = "USERNAME";
    public static final String USUARIOS_COL_4 = "GENERO";
    public static final String USUARIOS_COL_5 = "PASSWORD";
    public static final String USUARIOS_COL_6 = "ID_GRUPO";


    /*
    Campos de la tabla de Tipos de Gastos
     */

    public static final String TIPOGASTOS_TABLE = "TIPOGASTOS";

    public static final String TIPOGASTO_COL_0 = "TIPOGASTO_ID";
    public static final String TIPOGASTO_COL_1 = "NOMBRE";
    public static final String TIPOGASTO_COL_2 = "CATEGORIA";
    public static final String TIPOGASTO_COL_3 = "ICONO";


        /*
    Campos de la tabla de Tipos de Gastos
     */

    public static final String GASTOS_TABLE = "GASTOS";

    public static final String GASTOS_COL_0 = "GASTO_ID";
    public static final String GASTOS_COL_1 = "IMPORTE";
    public static final String GASTOS_COL_2 = "ID_USUARIO";
    public static final String GASTOS_COL_3 = "ID_TIPOGASTO";
    public static final String GASTOS_COL_4 = "FECHA";
    public static final String GASTOS_COL_5 = "CATEGORIA";


    /*******************************************************
     *      CREACION DE TABLAS                             *
     *                                                     *
     ******************************************************/

    /*
    Comienzo con la construcción de las bases de datos
     */
    public static String CreateTablaUsuarios() {

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + USUARIOS_TABLE + " (")
                .append(USUARIOS_COL_0).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(USUARIOS_COL_1).append(" TEXT NOT NULL, ")
                .append(USUARIOS_COL_2).append(" REAL NOT NULL, ")
                .append(USUARIOS_COL_3).append(" REAl NOT NULL UNIQUE, ")
                .append(USUARIOS_COL_4).append(" REAL NOT NULL, ")
                .append(USUARIOS_COL_5).append(" REAL, ")
                .append(USUARIOS_COL_6).append(" INTEGER NOT NULL)");

        return sb.toString();

    }

    public static String CreateTablaTipoGastos() {

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TIPOGASTOS_TABLE + " (")
                .append(TIPOGASTO_COL_0).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(TIPOGASTO_COL_1).append(" TEXT NOT NULL, ")
                .append(TIPOGASTO_COL_2).append(" TEXT NOT NULL, ")
                .append(TIPOGASTO_COL_3).append(" REAl NOT NULL)");

        return sb.toString();
    }

    public static String CreateTablaGastos() {

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + GASTOS_TABLE + " (")
                .append(GASTOS_COL_0).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(GASTOS_COL_1).append(" REAL NOT NULL, ")
                .append(GASTOS_COL_2).append(" REAL NOT NULL, ")
                .append(GASTOS_COL_3).append(" REAL NOT NULL UNIQUE, ")
                .append(GASTOS_COL_4).append(" REAL NOT NULL, ")
                .append(GASTOS_COL_5).append(" REAL, ")
                .append("FOREIGN KEY (" + GASTOS_COL_2 + ") REFERENCES " + USUARIOS_TABLE +
                        " (" + USUARIOS_COL_0 + "),")
                .append("FOREIGN KEY (" + GASTOS_COL_3 + ") REFERENCES " + TIPOGASTOS_TABLE +
                        " (" + TIPOGASTO_COL_0 + "))");

        Log.d("**", sb.toString());

        return sb.toString();

    }

}
