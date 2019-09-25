package com.sxtsoft.gestiongastos.database;

import android.content.Context;
import android.util.Log;

import java.security.PublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilidades {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private static final SimpleDateFormat sdfISO8601 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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
    public static final String GASTOS_COL_2 = "USUARIO_ID";
    public static final String GASTOS_COL_3 = "TIPOGASTO_ID";
    public static final String GASTOS_COL_4 = "FECHA";
    public static final String GASTOS_COL_5 = "CATEGORIA";

     /*
    Campos de la tabla Alarma
     */

    public static final String ALARMA_TABLE = "ALARMA";

    public static final String ALARMA_COL_0 = "ALARMA_ID";
    public static final String ALARMA_COL_1 = "NOMBRE";
    public static final String ALARMA_COL_2 = "IMPORTE";
    public static final String ALARMA_COL_3 = "CATEGORIA_ID";
    public static final String ALARMA_COL_4 = "TIPOGASTO_ID";
    public static final String ALARMA_COL_5 = "VISTO";
    public static final String ALARMA_COL_6 = "ESTADO";
    public static final String ALARMA_COL_7 = "USUARIO_ID";


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
                .append(TIPOGASTO_COL_3).append(" TEXT NOT NULL)");

        return sb.toString();
    }

    public static String CreateTablaGastos() {

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + GASTOS_TABLE + " (")
                .append(GASTOS_COL_0).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(GASTOS_COL_1).append(" REAL NOT NULL, ")
                .append(GASTOS_COL_2).append(" INTEGER NOT NULL, ")
                .append(GASTOS_COL_3).append(" INTEGER NOT NULL, ")
                .append(GASTOS_COL_4).append(" REAL NOT NULL, ")
                .append(GASTOS_COL_5).append(" TEXT NOT NULL, ")
                .append("FOREIGN KEY (" + GASTOS_COL_2 + ") REFERENCES " + USUARIOS_TABLE +
                        " (" + USUARIOS_COL_0 + "),")
                .append("FOREIGN KEY (" + GASTOS_COL_3 + ") REFERENCES " + TIPOGASTOS_TABLE +
                        " (" + TIPOGASTO_COL_0 + "))");

        Log.d("**", sb.toString());

        return sb.toString();

    }

    public static String createTableAlarmas(){

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + ALARMA_TABLE + " (")
                .append(ALARMA_COL_0).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ALARMA_COL_1).append(" TEXT NOT NULL, ")
                .append(ALARMA_COL_2).append(" REAL NOT NULL, ")
                .append(GASTOS_COL_3).append(" INTEGER NOT NULL, ")
                .append(GASTOS_COL_4).append(" REAL NOT NULL, ")
                .append(GASTOS_COL_5).append(" TEXT NOT NULL, ")
                .append("FOREIGN KEY (" + GASTOS_COL_2 + ") REFERENCES " + USUARIOS_TABLE +
                        " (" + USUARIOS_COL_0 + "),")
                .append("FOREIGN KEY (" + GASTOS_COL_3 + ") REFERENCES " + TIPOGASTOS_TABLE +
                        " (" + TIPOGASTO_COL_0 + "))");

        return sb.toString();

    }

    public static String dateToString(Date fecha){
        //este método convertirá
        //una fecha (Date) en un string

        return sdf.format(fecha);

    }

    public static Date stringToDate(String strDate){
        //este método convertirá
        //un String en una fecha

        //convierto el String to Date
        Date fecha;

        try {
            fecha = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            fecha = new Date();
        }

        return fecha;

    }


    public static long dateToMilisegundos(Date date){
        /*
        Esta fundion convierte una fecha en milisegundos
        para pasarla a la base de datos
         */

        return date.getTime();
    }

    public static Date milisegundosToDate(long milisegundos){

        Date date = new Date(milisegundos);

        return date;

    }

    public static String milisegundosToDateString(long milisegundos){
        /*
        Este metodo convertira una fecha dada en milisegundos
        en un strin
         */

        Date fecha = new Date(milisegundos);

        return sdf.format(fecha);

    }

    public static int getIdResourcesDrawable(Context context, String nameResources){

        return context.getResources().getIdentifier(nameResources,
                "drawable",context.getPackageName());

    }

}
