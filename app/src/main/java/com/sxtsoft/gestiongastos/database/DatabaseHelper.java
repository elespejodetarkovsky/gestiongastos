package com.sxtsoft.gestiongastos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DatabaseHelper extends SQLiteOpenHelper {


    /*
    Creacion de la table de usuarios
     */

    public static final String USUARIOS_TABLE = "USUARIOS";

    public static final String USUARIOS_COL_0 = "CODIGO";
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

    public static final String TIPOGASTO_COL_0 = "CODIGO";
    public static final String TIPOGASTO_COL_1 = "NOMBRE";
    public static final String TIPOGASTO_COL_2 = "CATEGORIA";
    public static final String TIPOGASTO_COL_3 = "ICONO";



    private static DatabaseHelper sInstance;

    private static final String DATABASE_NAME = "GESTIONGASTOS.DB";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       /*
       Crearé una sola base de datos sólo para probar
       la de usuarios. Luego estas (el resto de tables)
       serán leídas de un archivo
        */


        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + USUARIOS_TABLE + " (")
                .append(USUARIOS_COL_0).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(USUARIOS_COL_1).append(" TEXT NOT NULL, ")
                .append(USUARIOS_COL_2).append(" REAL NOT NULL, ")
                .append(USUARIOS_COL_3).append(" REAl NOT NULL UNIQUE, ")
                .append(USUARIOS_COL_4).append(" REAL NOT NULL, ")
                .append(USUARIOS_COL_5).append(" REAL, ")
                .append(USUARIOS_COL_6).append(" INTEGER NOT NULL)");

        String strDDL = sb.toString();

        sb.setLength(0); //vacio el StringBuffer
        db.execSQL(strDDL);

        Log.d("**", "USUARIOS: " + strDDL);

        /*
        creo la tabla de tipos de Gastos
         */


        sb.append("CREATE TABLE " + TIPOGASTOS_TABLE + " (")
                .append(TIPOGASTO_COL_0).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(TIPOGASTO_COL_1).append(" TEXT NOT NULL, ")
                .append(TIPOGASTO_COL_2).append(" TEXT NOT NULL, ")
                .append(TIPOGASTO_COL_3).append(" REAl NOT NULL)");


        strDDL = sb.toString();

        sb.setLength(0); //vacio el StringBuffer
        db.execSQL(strDDL);

        Log.d("**", "TIPOGASTOS: " + strDDL);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //en caso de cambio de versión se eliminaría
        //la tabla.
        db.execSQL("DROP TABLE IF EXISTS " + USUARIOS_TABLE);
        onCreate(db);

    }


//    public abstract Cursor getAll();
//
//    public abstract Object Crear(Object object);
//
//    public abstract boolean delete(int codigo);
//
//    public abstract Object update (Object object);
//
//    public abstract Object insert (Object object);


//*****************************************************
//              METODOS PRIVADOS                    ***
//*****************************************************

    private String dateToString(Date fecha){
        //este método convertirá
        //una fecha (Date) en un string

        return sdf.format(fecha);

    }

    private Date stringToDate(String strDate){
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

}