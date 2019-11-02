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
import java.util.Calendar;
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

    public double SumaGastosMes(){

        /*
        Este método devuelve la suma de gastos
        en lo que va del mes
         */

        Calendar calendar = Calendar.getInstance();

        String mes = String.valueOf(calendar.get(Calendar.MONTH));
        String year = String.valueOf(calendar.get(Calendar.YEAR));

        //armo fecha inicial de primero del mes corriente
        String fechaInicio = "01/" + mes + "/" + year + " 00:00";

        long fechaInicioMili = Utilidades.dateToMilisegundos(Utilidades.stringToDate(fechaInicio));
        long fechaFinMili = Utilidades.dateToMilisegundos(new Date());


        //comienzo la consulta
        double suma = 0;

        String tabla = Utilidades.GASTOS_TABLE;

        String[] campos = {Utilidades.GASTOS_COL_1};

        String[] args = {String.valueOf(fechaInicioMili), String.valueOf(fechaFinMili)};

        Cursor cursor = db.rawQuery("SELECT SUM(" + Utilidades.GASTOS_COL_1 + ") FROM " + tabla + " WHERE "
                + Utilidades.GASTOS_COL_4 + " BETWEEN " + fechaInicioMili + " AND " + fechaFinMili, null);


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

        long lngFecha1 = Utilidades.dateToMilisegundos(fechaStart);
        long lngFecha2 = Utilidades.dateToMilisegundos(fechaEnd);

        String sql = "SELECT SUM(" + Utilidades.GASTOS_COL_1 +")," +
                Utilidades.GASTOS_COL_5 + " FROM " + tabla +
                " WHERE " + Utilidades.GASTOS_COL_4 + " BETWEEN " +
                lngFecha1 + " AND " + lngFecha2 + " GROUP BY " + Utilidades.GASTOS_COL_5;

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

    public double totalGastosByDatesCategoriasAndTipoGasto(Date fechaStart, Date fechaEnd, Categoria categoria, long tipoGastoId){

        String tabla = Utilidades.GASTOS_TABLE;

        long lngFecha1 = Utilidades.dateToMilisegundos(fechaStart);
        long lngFecha2 = Utilidades.dateToMilisegundos(fechaEnd);

        String sql = "SELECT SUM(" + Utilidades.GASTOS_COL_1 +")," +
                " FROM " + tabla + " WHERE " + Utilidades.GASTOS_COL_3 + "=" + tipoGastoId +
                " AND " + Utilidades.GASTOS_COL_5 + "=" + categoria.toString() + " BETWEEN " +
                lngFecha1 + " AND " + lngFecha2;

        //Cursor cursor = db.rawQuery(sql,null);

        Log.d("**", sql);

        Cursor cursor = db.rawQuery(sql,null);

        double importeTotal = 0;

        if (cursor != null) {
            while (cursor.moveToNext()) {

                importeTotal = cursor.getDouble(0);

            }

        }

        return importeTotal;

    }


    public List<Gasto> obtenerUltimosGastos(int limite){
        /*
        obtendr'a los ultimos n registros
        ingresados
         */

        String tabla = Utilidades.GASTOS_TABLE;
        String tablaTipos = Utilidades.TIPOGASTOS_TABLE;


        StringBuilder sb = new StringBuilder();
            sb.append(tabla + "." + Utilidades.GASTOS_COL_0 + ", ");
            sb.append(tabla + "." + Utilidades.GASTOS_COL_1 + ", ");
            sb.append(tabla + "." + Utilidades.GASTOS_COL_2 + ", ");
            sb.append(tabla + "." + Utilidades.GASTOS_COL_3 + ", ");
            sb.append(tabla + "." + Utilidades.GASTOS_COL_4 + ", ");
            sb.append(tabla + "." + Utilidades.GASTOS_COL_5 + ", ");
            sb.append(Utilidades.TIPOGASTOS_TABLE + "." + Utilidades.TIPOGASTO_COL_1 + ", ");
            sb.append(Utilidades.TIPOGASTOS_TABLE + "." + Utilidades.TIPOGASTO_COL_3);


        String sql = "SELECT " + sb.toString() + " FROM " + tabla + " INNER JOIN " +
                tablaTipos + " ON " + tabla + "." + Utilidades.GASTOS_COL_3 + "=" +
                tablaTipos + "." + Utilidades.TIPOGASTO_COL_0
                + " ORDER BY " + Utilidades.GASTOS_COL_0 + " DESC LIMIT " + limite;


        Log.d("**", sql);

        Cursor cursor = db.rawQuery(sql,null);

        List<Gasto> gastos = new ArrayList<>();

        if (cursor != null){
            while (cursor.moveToNext()){
                long id = cursor.getLong(0);
                double importe = cursor.getDouble(1);
                long userId = cursor.getLong(2);
                long tipoGastoId = cursor.getLong(3);
                String fecha = cursor.getString(4);
                Categoria categoria = Categoria.valueOf(cursor.getString(5));
                String nombreTipo = cursor.getString(6);
                int icono = cursor.getInt(7);

                //creo el usuario para crear el gasto
                Usuario user = new Usuario();
                user.setCodigo(userId);

                //creo el tipo de gasto para crear el gasto
                TipoGasto tipoGasto = new TipoGasto(nombreTipo, categoria);
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

    public List<Gasto> obtenerUltimosGastosUsuario(int limite, long idUsuario){
        /*
        obtendr'a los ultimos n registros
        ingresados
         */

        String tablaGastos = Utilidades.GASTOS_TABLE;
        String tablaTipos = Utilidades.TIPOGASTOS_TABLE;
        String tablaUsuarios = Utilidades.USUARIOS_TABLE;


        StringBuilder sb = new StringBuilder();
        sb.append(tablaGastos + "." + Utilidades.GASTOS_COL_0 + ", ");
        sb.append(tablaGastos + "." + Utilidades.GASTOS_COL_1 + ", ");
        sb.append(tablaGastos + "." + Utilidades.GASTOS_COL_2 + ", ");
        sb.append(tablaGastos + "." + Utilidades.GASTOS_COL_3 + ", ");
        sb.append(tablaGastos + "." + Utilidades.GASTOS_COL_4 + ", ");
        sb.append(tablaGastos + "." + Utilidades.GASTOS_COL_5 + ", ");
        sb.append(tablaTipos + "." + Utilidades.TIPOGASTO_COL_1 + ", ");
        //sb.append(tablaTipos + "." + Utilidades.TIPOGASTO_COL_3 + ", ");
        sb.append(tablaUsuarios + "." + Utilidades.USUARIOS_COL_3);


        String sql = "SELECT " + sb.toString() + " FROM " + tablaGastos + " INNER JOIN " +
                tablaTipos + " ON " + tablaGastos + "." + Utilidades.GASTOS_COL_3 + "=" +
                tablaTipos + "." + Utilidades.TIPOGASTO_COL_0 + " INNER JOIN " + tablaUsuarios + " ON " +
                tablaGastos + "." + Utilidades.GASTOS_COL_2 + "=" + tablaUsuarios + "." + Utilidades.USUARIOS_COL_0
                + " ORDER BY " + Utilidades.GASTOS_COL_0 + " DESC LIMIT " + limite;


        Log.d("**", sql);

        Cursor cursor = db.rawQuery(sql,null);

        List<Gasto> gastos = new ArrayList<>();

        if (cursor != null){
            while (cursor.moveToNext()){
                long id = cursor.getLong(0);
                double importe = cursor.getDouble(1);
                long userId = cursor.getLong(2);
                long tipoGastoId = cursor.getLong(3);
                long fecha = cursor.getLong(4);
                Categoria categoria = Categoria.valueOf(cursor.getString(5));
                String nombreTipo = cursor.getString(6);
                //int icono = cursor.getInt(7);
                String userName = cursor.getString(7);

                //creo el usuario para crear el gasto
                Usuario user = new Usuario();
                user.setCodigo(userId);
                user.setUserName(userName);

                //creo el tipo de gasto para crear el gasto
                TipoGasto tipoGasto = new TipoGasto(nombreTipo, categoria);
                tipoGasto.setCodigo(tipoGastoId);

                Gasto gasto = new Gasto(importe,user,tipoGasto,Utilidades.milisegundosToDate(fecha),categoria);

                gasto.setCodigo(id);

                //agrego el objeto a la lista
                gastos.add(gasto);
            }

            return gastos;

        } else {

            return null;
        }

    }

    public Map<String, Double> totalGastosBetweenDatesAndTiposGastos(Date fechaInicio, Date fechaFin, Categoria categoria){
        /*
        crea un map con los tipos de gastos (su nombre),
        la suma de sus importes
        en funcion de las fechas
        y en un futuro por usuario
         */

        //TODO
        //faltaría agregar gestion por usuario


        String tabla = Utilidades.GASTOS_TABLE;
        String tablaGastos = Utilidades.TIPOGASTOS_TABLE;


        long lngFecha1 = Utilidades.dateToMilisegundos(fechaInicio);
        long lngFecha2 = Utilidades.dateToMilisegundos(fechaFin);


        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("SELECT SUM(" + tabla + "." + Utilidades.GASTOS_COL_1 +")," + tablaGastos + "." + Utilidades.TIPOGASTO_COL_1);
        stringBuilder.append(" FROM " + tabla + " INNER JOIN " + tablaGastos + " ON " + tabla + "." + Utilidades.GASTOS_COL_3 + "=");
        stringBuilder.append(tablaGastos + "." + Utilidades.TIPOGASTO_COL_0);
        stringBuilder.append(" WHERE " + tabla + "." + Utilidades.GASTOS_COL_5 + "='" + categoria.toString() + "'");
        stringBuilder.append( " AND " + Utilidades.GASTOS_COL_4 +  " BETWEEN " +
                lngFecha1 + " AND " + lngFecha2);
        stringBuilder.append(" GROUP BY " + tablaGastos + "." + Utilidades.TIPOGASTO_COL_1);


        String sql = stringBuilder.toString();

        //Cursor cursor = db.rawQuery(sql,null);

        Log.d("**", "DataBaseHelperGasto.totalGastosBetweenDatesAndTiposGastos : \n" + sql);

       Cursor cursor = db.rawQuery(sql,null);

        double importeTotal = 0;

        Map<String, Double> gastos = new HashMap<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {

                //importeTotal = cursor.getDouble(0);
                gastos.put(cursor.getString(1), cursor.getDouble(0));


            }

        }

        return gastos;

    }
}
