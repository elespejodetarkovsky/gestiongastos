package com.sxtsoft.gestiongastos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sxtsoft.gestiongastos.model.Gasto;

public class DataBaseHelperGasto {


    private SQLiteDatabase db;
    private Context context;


    public DataBaseHelperGasto(Context context){
        db = DatabaseHelper.getInstance(context).getWritableDatabase();
    }


    public Cursor getAll() {

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.GASTOS_TABLE, null);

        return cursor;
    }


    public Gasto create(Gasto gasto) {

//       ContentValues contentValues = new ContentValues();


//        contentValues.put(Utilidades.GASTOS_COL_1, gasto.getImporte());
//        contentValues.put(Utilidades.GASTOS_COL_2, gasto.);
//        contentValues.put(Utilidades.TIPOGASTO_COL_1, tipoGasto.getNombre());
//        contentValues.put(Utilidades.TIPOGASTO_COL_2, tipoGasto.getCategoria().toString());
//        contentValues.put(Utilidades.TIPOGASTO_COL_3, tipoGasto.getIcono());


        //nullColumnHack se utiliza cuando pretendemos utilizar registros
        //con valores null. En ese caso, contentValues estará vacío (sin PUT)
//
//        long resultado = db.insert(Utilidades.TIPOGASTOS_TABLE, null, contentValues);

        //insert nos devuelve un long...la Id del registro que acaba de generar
        // o -1 si algo salió mal

//        tipoGasto.setCodigo(resultado);

        //si algo va mal devuelvo un null
        return null;


    }

}
