package com.sxtsoft.gestiongastos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.UtteranceProgressListener;

import com.sxtsoft.gestiongastos.model.Alarma;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperAlarma {

    private SQLiteDatabase db;

    public DatabaseHelperAlarma(Context context){
        db = DatabaseHelper.getInstance(context).getWritableDatabase();
    }


    public Alarma crearAlarma(Alarma alarma){

        ContentValues contentValues = new ContentValues();

        contentValues.put(Utilidades.ALARMA_COL_1, alarma.getNombre());
        contentValues.put(Utilidades.ALARMA_COL_2, alarma.getImporte());
        contentValues.put(Utilidades.ALARMA_COL_3, alarma.getCategoria().toString());
        contentValues.put(Utilidades.ALARMA_COL_4, alarma.getTipoGasto().getCodigo());
        contentValues.put(Utilidades.ALARMA_COL_5, alarma.isVisto());
        contentValues.put(Utilidades.ALARMA_COL_6, alarma.isEstado());
        contentValues.put(Utilidades.ALARMA_COL_7, alarma.getUsuario().getCodigo());


        //nullColumnHack se utiliza cuando pretendemos utilizar registros
        //con valores null. En ese caso, contentValues estará vacío (sin PUT)

        long resultado = db.insert(Utilidades.ALARMA_TABLE, null, contentValues);

        //insert nos devuelve un long...la Id del registro que acaba de generar
        // o -1 si algo salió mal

        alarma.setCodigo(resultado);

        //si algo va mal devuelvo un null
        return resultado == -1 ? null : alarma;
    }

    public List<Alarma> getAll(){

        String tablaAlarma = Utilidades.ALARMA_TABLE;
        String tablaTipoGasto = Utilidades.TIPOGASTOS_TABLE;
        String tablaUsuario = Utilidades.USUARIOS_TABLE;

        StringBuilder sb = new StringBuilder();
        sb.append(tablaAlarma + "." + Utilidades.ALARMA_COL_0 + ", ");
        sb.append(tablaAlarma + "." + Utilidades.ALARMA_COL_1 + ", ");
        sb.append(tablaAlarma + "." + Utilidades.ALARMA_COL_2 + ", ");
        sb.append(tablaAlarma + "." + Utilidades.ALARMA_COL_3 + ", ");
        sb.append(tablaAlarma + "." + Utilidades.ALARMA_COL_4 + ", ");
        sb.append(tablaAlarma + "." + Utilidades.ALARMA_COL_5 + ", ");
        sb.append(tablaAlarma + "." + Utilidades.ALARMA_COL_6 + ", ");
        sb.append(tablaAlarma + "." + Utilidades.ALARMA_COL_7 + ", ");
        sb.append(tablaTipoGasto + "." + Utilidades.TIPOGASTO_COL_1 + ", ");

//        sb.append(tabla + "." + Utilidades.GASTOS_COL_1 + ", ");
//        sb.append(tabla + "." + Utilidades.GASTOS_COL_2 + ", ");
//        sb.append(tabla + "." + Utilidades.GASTOS_COL_3 + ", ");
//        sb.append(tabla + "." + Utilidades.GASTOS_COL_4 + ", ");
//        sb.append(tabla + "." + Utilidades.GASTOS_COL_5 + ", ");
        sb.append(Utilidades.TIPOGASTOS_TABLE + "." + Utilidades.TIPOGASTO_COL_1 + ", ");
        sb.append(Utilidades.TIPOGASTOS_TABLE + "." + Utilidades.TIPOGASTO_COL_3);

//        String[] args = {categoria.toString()};

//        Cursor cursor = db.query(tabla,campos,Utilidades.GASTOS_COL_5 + "=?", args, null,null, null);
//
//        List<Gasto> gastos = new ArrayList<>();

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
}
