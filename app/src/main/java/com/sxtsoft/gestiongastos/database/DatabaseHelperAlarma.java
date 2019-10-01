package com.sxtsoft.gestiongastos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import com.sxtsoft.gestiongastos.model.Alarma;
import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.model.TipoGasto;
import com.sxtsoft.gestiongastos.model.Usuario;

import java.util.ArrayList;
import java.util.Calendar;
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
        contentValues.put(Utilidades.ALARMA_COL_8, alarma.getDias());


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
        sb.append(tablaAlarma + "." + Utilidades.ALARMA_COL_8 + ", ");
        sb.append(tablaTipoGasto + "." + Utilidades.TIPOGASTO_COL_1 + ", ");
        sb.append(tablaUsuario + "." + Utilidades.USUARIOS_COL_1);

        //INNER JOIN
        //algo así como ...y de la tabla pepe...ON (de donde...)

        String sql = "SELECT " + sb.toString() + " FROM " + tablaAlarma + " INNER JOIN " +
                tablaTipoGasto + " ON " + tablaAlarma + "." + Utilidades.ALARMA_COL_4 + "=" +
                tablaTipoGasto + "." + Utilidades.TIPOGASTO_COL_0 +
                " INNER JOIN " + tablaUsuario + " ON " + tablaAlarma + "." + Utilidades.ALARMA_COL_7 + "=" +
                tablaUsuario + "." + Utilidades.USUARIOS_COL_0;


        Log.d("**", sql);

        Cursor cursor = db.rawQuery(sql,null);

        List<Alarma> alarmas = new ArrayList<>();

        if (cursor != null){
            while (cursor.moveToNext()){
                long id = cursor.getLong(0);
                String nombreAlarma = cursor.getString(1);
                double importe = cursor.getDouble(2);
                Categoria categoria = Categoria.valueOf(cursor.getString(3));
                long tipoGastoId = cursor.getLong(4);
                boolean visto = Utilidades.IntegerToBoolean(cursor.getInt(5));
                boolean estado = Utilidades.IntegerToBoolean(cursor.getInt(6));
                long userId = cursor.getLong(7);
                int dias = cursor.getInt(8);
                String nombreTipoGasto = cursor.getString(9);
                String nombreUsuario = cursor.getString(10);


                //creo el usuario para crear el gasto
                Usuario user = new Usuario();
                user.setCodigo(userId);
                user.setNombre(nombreUsuario);

                //creo el tipo de gasto para crear el gasto
                TipoGasto tipoGasto = new TipoGasto();
                tipoGasto.setCodigo(tipoGastoId);
                tipoGasto.setNombre(nombreTipoGasto);

                Alarma alarma = new Alarma(nombreAlarma,dias,importe,categoria,tipoGasto,visto,estado,user);

                alarma.setCodigo(id);

//                //agrego el objeto a la lista
                alarmas.add(alarma);

            }

            return alarmas;

        } else {

            return null;
        }

    }

    public List<Alarma> verificarAlarmas(List<Alarma> alarmas, double sumaImportes){
        /*
        Esta funcion realizará una verificacion de las
        alertas en el mes de las alarmas
        devolverá un array con las alarmas que hayan
        superado el límite impuesto por el usuario
         */

        //buscar gastos del mes
        //ejecutar sobre los mismos las alarmas

        //necesito conocer el día del mes corriente
        Calendar calendar = Calendar.getInstance();

        int diaHoy = calendar.get(Calendar.DAY_OF_MONTH);

        List<Alarma> alarmasSuperadas = new ArrayList<>();


        for (Alarma alarma:alarmas){

            //descartamos los vistos y los inactivos
            if (!alarma.isVisto() || alarma.isEstado()){


                if (diaHoy < alarma.getDias() && alarma.getImporte() > sumaImportes){
                    //aquí se ejecuta la comparación y de
                    //superarla se agrega a la lista

                    alarmasSuperadas.add(alarma);

                }
            }
        }

        return null;
    }
}
