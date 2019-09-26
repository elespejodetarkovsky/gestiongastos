package com.sxtsoft.gestiongastos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.UtteranceProgressListener;

import com.sxtsoft.gestiongastos.model.Alarma;

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

}
