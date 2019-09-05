package com.sxtsoft.gestiongastos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.Gasto;
import com.sxtsoft.gestiongastos.model.TipoGasto;
import com.sxtsoft.gestiongastos.model.Usuario;

import java.util.ArrayList;
import java.util.List;

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

    public int delete(long codigo){
        return db.delete(Utilidades.GASTOS_TABLE, Utilidades.GASTOS_COL_0 + "=" + codigo,null);
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
}
