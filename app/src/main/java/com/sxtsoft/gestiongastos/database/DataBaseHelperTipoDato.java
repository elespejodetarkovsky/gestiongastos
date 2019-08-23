package com.sxtsoft.gestiongastos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.TipoGasto;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperTipoDato {

    private SQLiteDatabase db;
    private Context context;

    public DataBaseHelperTipoDato(Context context){
        db = DatabaseHelper.getInstance(context).getWritableDatabase();
    }


    public Cursor getAll() {

        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TIPOGASTOS_TABLE, null);

        return cursor;
    }

    public List<TipoGasto> getTipoGastoByCategoria (Categoria categoria){

        String tabla = DatabaseHelper.TIPOGASTOS_TABLE;

        String[] campos = {DatabaseHelper.TIPOGASTO_COL_1, DatabaseHelper.TIPOGASTO_COL_3};

        String[] args = {categoria.toString()};

        Cursor cursor = db.query(tabla,campos,DatabaseHelper.TIPOGASTO_COL_2 + "=?", args, null,null, null);

        List<TipoGasto> tiposGastos = new ArrayList<>();

        if (cursor != null){
            while (cursor.moveToNext()){
                String nombre = cursor.getString(0);
                int icono = cursor.getInt(1);

                TipoGasto tipoGasto = new TipoGasto(nombre, categoria, icono);

                //agrego el objeto a la lista
                tiposGastos.add(tipoGasto);
            }

            return tiposGastos;

        } else {

            return null;
        }

    }


    public TipoGasto create(TipoGasto tipoGasto) {

        ContentValues contentValues = new ContentValues();


        contentValues.put(DatabaseHelper.TIPOGASTO_COL_1, tipoGasto.getNombre());
        contentValues.put(DatabaseHelper.TIPOGASTO_COL_2, tipoGasto.getCategoria().toString());
        contentValues.put(DatabaseHelper.TIPOGASTO_COL_3, tipoGasto.getIcono());


        //nullColumnHack se utiliza cuando pretendemos utilizar registros
        //con valores null. En ese caso, contentValues estará vacío (sin PUT)

        long resultado = db.insert(DatabaseHelper.TIPOGASTOS_TABLE, null, contentValues);

        //insert nos devuelve un long...la Id del registro que acaba de generar
        // o -1 si algo salió mal

        tipoGasto.setCodigo(resultado);

        //si algo va mal devuelvo un null
        return resultado == -1 ? null : tipoGasto;


    }


}
