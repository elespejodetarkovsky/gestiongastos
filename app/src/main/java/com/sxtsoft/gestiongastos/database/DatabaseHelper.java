package com.sxtsoft.gestiongastos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DatabaseHelper extends SQLiteOpenHelper {

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
        Se crea base de datos Usuarios
        */

        db.execSQL(Utilidades.CreateTablaUsuarios());

        /*
        creo la tabla de tipos de Gastos
         */

        db.execSQL(Utilidades.CreateTablaTipoGastos());

        /*
        CREO LA TABLA GASTOS
         */

        db.execSQL(Utilidades.CreateTablaGastos());


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //en caso de cambio de versión se eliminaría
        //la tabla.
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.USUARIOS_TABLE);
        onCreate(db);

    }



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