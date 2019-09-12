package com.sxtsoft.gestiongastos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.Gasto;
import com.sxtsoft.gestiongastos.model.TipoGasto;
import com.sxtsoft.gestiongastos.model.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBaseHelperGasto {


    private SQLiteDatabase db;

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
        long fecha = Utilidades.dateToMilisegundos(gasto.getFecha());
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

    public List<Gasto> gastosByCategoria(Categoria categoria){

        String tabla = Utilidades.GASTOS_TABLE;

        String[] campos = {Utilidades.GASTOS_COL_0,
                Utilidades.GASTOS_COL_1,
                Utilidades.GASTOS_COL_2,
                Utilidades.GASTOS_COL_3,
                Utilidades.GASTOS_COL_4};

        String[] args = {categoria.toString()};

        Cursor cursor = db.query(tabla,campos,Utilidades.GASTOS_COL_5 + "=?", args, null,null, null);

        List<Gasto> gastos = new ArrayList<>();

        if (cursor != null){
            while (cursor.moveToNext()){
                long id = cursor.getLong(0);
                double importe = cursor.getDouble(1);
                long userId = cursor.getLong(2);
                long tipoGastoId = cursor.getLong(3);
                String fecha = cursor.getString(4);

                //creo el usuario para crear el gasto
                Usuario user = new Usuario();
                user.setCodigo(userId);

                //creo el tipo de gasto para crear el gasto
                TipoGasto tipoGasto = new TipoGasto();
                tipoGasto.setCodigo(tipoGastoId);

                Gasto gasto = new Gasto(importe,user,tipoGasto,Utilidades.stringToDate(fecha),categoria);

                gasto.setCodigo(id);

                //agrego el objeto a la lista
                gastos.add(gasto);
            }

            return gastos;

        } else {

            return null;
        }
    }

    public double SumaGastosByCategoria(Categoria categoria){
        double suma = 0;

        String tabla = Utilidades.GASTOS_TABLE;

        String[] campos = {Utilidades.GASTOS_COL_1};

        String[] args = {categoria.toString()};

        Cursor cursor = db.rawQuery("SELECT SUM(" + Utilidades.GASTOS_COL_1 + ") FROM " + tabla + " WHERE CATEGORIA=?", args);


        if (cursor != null){
            while (cursor.moveToNext()){
                suma = cursor.getDouble(0);
            }

            return suma;

        }

        return 0;
    }

    public boolean deleteGasto(long codigo){

        String tabla = Utilidades.GASTOS_TABLE;

        int delete = db.delete(tabla,Utilidades.GASTOS_COL_0 + "=" + codigo,null);

        if (delete != -1){
            //se ha borrado correctamente
            return true;
        }

        return false;
    }

    public Map<String, Double> totalGastosByDatesAndCategorias(Date fechaStart, Date fechaEnd){

        String tabla = Utilidades.GASTOS_TABLE;

        String[] campos = {Utilidades.GASTOS_COL_1,
                            Utilidades.GASTOS_COL_5}; //me interesará obtener la suma de los gastos

        long lngFecha1 = Utilidades.dateToMilisegundos(fechaStart);
        long lngFecha2 = Utilidades.dateToMilisegundos(fechaEnd);

        String sql = "SELECT SUM(" + Utilidades.GASTOS_COL_1 +")," +
                Utilidades.GASTOS_COL_5 + " FROM " + tabla +
                " WHERE " + Utilidades.GASTOS_COL_4 + " BETWEEN '" +
                lngFecha1 + "' AND '" + lngFecha2 + "' GROUP BY " + Utilidades.GASTOS_COL_5;

        //Cursor cursor = db.rawQuery(sql,null);

        Log.d("**", sql);

        Cursor cursor = db.rawQuery(sql,null);

        Map<String, Double> gastos = new HashMap<>();

        if (cursor != null){
            while (cursor.moveToNext()){
                double importeTotal = cursor.getDouble(0);
                String categoria = cursor.getString(1);

                gastos.put(categoria, importeTotal);
            }

            return gastos;

        } else {

            return null;
        }
    }
}
