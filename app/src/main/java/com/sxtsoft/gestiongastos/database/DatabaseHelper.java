package com.sxtsoft.gestiongastos.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sxtsoft.gestiongastos.model.TipoGasto;
import com.sxtsoft.gestiongastos.model.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GESTIONGASTOS.DB";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public abstract void onCreate(SQLiteDatabase db);

    @Override
    public abstract void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    public abstract Cursor getAll();

    public abstract Object Crear(Object object);

    public abstract boolean delete(int codigo);

    public abstract Object update (Object object);

    public abstract Object insert (Object object);


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