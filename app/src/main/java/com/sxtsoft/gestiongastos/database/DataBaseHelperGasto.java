package com.sxtsoft.gestiongastos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sxtsoft.gestiongastos.model.Categoria;
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

       ContentValues contentValues = new ContentValues();

       //PASO LA FECHA A STRING
        String fecha = Utilidades.dateToString(gasto.getFecha());
        contentValues.put(Utilidades.GASTOS_COL_1, gasto.getImporte());
        contentValues.put(Utilidades.GASTOS_COL_2, gasto.getUsuario().getCodigo());
        contentValues.put(Utilidades.GASTOS_COL_3, gasto.getTipoGasto().getCodigo());
        contentValues.put(Utilidades.GASTOS_COL_4, fecha);
        contentValues.put(Utilidades.GASTOS_COL_5, gasto.getCategoria().toString());


        //nullColumnHack se utiliza cuando pretendemos utilizar registros
        //con valores null. En ese caso, contentValues estará vacío (sin PUT)

        long resultado = db.insert(Utilidades.GASTOS_TABLE, null, contentValues);

        //insert nos devuelve un long...la Id del registro que acaba de generar
        // o -1 si algo salió mal

        gasto.setCodigo(resultado);

        //si algo va mal devuelvo un null
        return resultado == -1 ? null : gasto;

    }

}
