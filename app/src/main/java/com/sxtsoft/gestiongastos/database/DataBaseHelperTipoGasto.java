package com.sxtsoft.gestiongastos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.TipoGasto;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperTipoGasto {

    private SQLiteDatabase db;
    private Context context;

    public DataBaseHelperTipoGasto(Context context){
        db = DatabaseHelper.getInstance(context).getWritableDatabase();
    }


    public Cursor getAll() {

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TIPOGASTOS_TABLE, null);

        return cursor;
    }

    public List<TipoGasto> getTipoGastoByCategoria (Categoria categoria){

        String tabla = Utilidades.TIPOGASTOS_TABLE;

        String[] campos = {Utilidades.TIPOGASTO_COL_0, Utilidades.TIPOGASTO_COL_1, Utilidades.TIPOGASTO_COL_3};

        String[] args = {categoria.toString()};

        Cursor cursor = db.query(tabla,campos,Utilidades.TIPOGASTO_COL_2 + "=?", args, null,null, null);

        List<TipoGasto> tiposGastos = new ArrayList<>();

        if (cursor != null){
            while (cursor.moveToNext()){
                long id = cursor.getLong(0);
                String nombre = cursor.getString(1);
                String icono = cursor.getString(2);

                TipoGasto tipoGasto = new TipoGasto(nombre, categoria);

                tipoGasto.setCodigo(id);
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


        contentValues.put(Utilidades.TIPOGASTO_COL_1, tipoGasto.getNombre());
        contentValues.put(Utilidades.TIPOGASTO_COL_2, tipoGasto.getCategoria().toString());
        contentValues.put(Utilidades.TIPOGASTO_COL_3, tipoGasto.getIcono());


        //nullColumnHack se utiliza cuando pretendemos utilizar registros
        //con valores null. En ese caso, contentValues estará vacío (sin PUT)

        long resultado = db.insert(Utilidades.TIPOGASTOS_TABLE, null, contentValues);

        //insert nos devuelve un long...la Id del registro que acaba de generar
        // o -1 si algo salió mal

        tipoGasto.setCodigo(resultado);

        //si algo va mal devuelvo un null
        return resultado == -1 ? null : tipoGasto;


    }

    public TipoGasto randomTipoGasto(){

        String sql = "SELECT * FROM " + Utilidades.TIPOGASTOS_TABLE + " ORDER BY RANDOM() LIMIT 1";

        Cursor cursor = db.rawQuery(sql,null);

        if (cursor != null){
            while (cursor.moveToNext()){
                long id = cursor.getLong(0);
                String nombre = cursor.getString(1);
                Categoria categoria = Categoria.valueOf(cursor.getString(2));

                TipoGasto tipoGasto = new TipoGasto(nombre, categoria);

                tipoGasto.setCodigo(id);

                return tipoGasto;
            }

        }

        return null;

    }

    public int deleteTipoGasto(long id){
        /*
        borrará en cascada
        un tipo de gasto
         */

        //PRAGMA foreign_keys = ON

        return  db.delete(Utilidades.TIPOGASTOS_TABLE, "=" + id,null);


    }


}
