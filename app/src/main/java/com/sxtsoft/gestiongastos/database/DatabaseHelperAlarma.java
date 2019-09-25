package com.sxtsoft.gestiongastos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelperAlarma {

    private SQLiteDatabase db;

    public DatabaseHelperAlarma(Context context){
        db = DatabaseHelper.getInstance(context).getWritableDatabase();
    }
    
}
