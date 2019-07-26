package com.sxtsoft.gestiongastos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sxtsoft.gestiongastos.model.Usuario;

public class DatabaseHelperUsuario extends DatabaseHelper{

    private static final String USUARIOS_TABLE = "USUARIOS";

    private static final String COL_0 = "CODIGO";
    private static final String COL_1 = "NOMBRE";
    private static final String COL_2 = "APELLIDO";
    private static final String COL_3 = "USERNAME";
    private static final String COL_4 = "GENERO";
    private static final String COL_5 = "PASSWORD";
    private static final String COL_6 = "ID_GRUPO";


    public DatabaseHelperUsuario(Context context) {
        super(context);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        /*  CREATE TABLE LECTURAS(
//
//           0     CODIGO      INTEGER PRIMARY KEY AUTOINCREMENT,
//           1     FECHA_HORA  TEXT NOT NULL,
//           2     PESO        REAL NOT NULL,
//           3     DIASTOLICA  REAL NOT NULL,
//           4     SISTOLICA   REAL NOT NULL,
//           5     LONGITUD    REAL,
//           6     LATITUD     REAL) */
//        //se utiliza una sola vez en la aplicacion...si ve que está
//        //la db.
//
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + USUARIOS_TABLE + " (")
                .append(COL_0).append(" INTEGER AUTOINCREMENT, ")
                .append(COL_1).append(" TEXT NOT NULL, ")
                .append(COL_2).append(" REAL NOT NULL, ")
                .append(COL_3).append(" PRIMARY KEY REAl NOT NULL, ")
                .append(COL_4).append(" REAL NOT NULL, ")
                .append(COL_5).append(" REAL, ")
                .append(COL_6).append("INTEGER NOT NULL");

        String strDDL = sb.toString();

        db.execSQL(strDDL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //en caso de cambio de versión se eliminaría
        //la tabla.
        db.execSQL("DROP TABLE IF EXISTS " + USUARIOS_TABLE);
        onCreate(db);
    }

    @Override
    public Cursor getAll() {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + USUARIOS_TABLE, null);

        return cursor;
    }

    @Override
    public boolean delete(int codigo) {
        return false;
    }

    @Override
    public Object update(Object object) {
        return null;
    }

    @Override
    public Object insert(Object object) {
        return null;
    }

    public Usuario readUsuario(String userName) {
        //Devolverá una lectura en
        //funcion del código suministrado
        SQLiteDatabase db = getWritableDatabase();

        //creo una array de string para luego pasarlo
        //como argumento al select

        String[] campos = new String[]{COL_1, COL_2, COL_3, COL_4, COL_5};

        String[] args = new String[]{String.valueOf(id)};

        Cursor cursor = db.query(USUARIOS_TABLE, campos, "CODIGO=?", args, null, null, null);
//        Cursor cursor = db.rawQuery("SELECT * FROM " + LECTURAS_TABLE +
//                " WHERE CODIGO = ? ", args);

        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToNext();

            //Integer codigo = cursor.getInt(0);
            //el getxxx indica la forma en que lo quieres
            //por ejemplo el entero 1...el getString será "1"

            String strFecha = cursor.getString(0);
            double peso = cursor.getDouble(1);
            double diastolica = cursor.getDouble(2);
            double sistolica = cursor.getDouble(3);
            double longitud = cursor.getDouble(4);
            double latitud = cursor.getDouble(5);


            Lectura lectura = new Lectura(this.stringToDate(strFecha), peso, diastolica, sistolica, longitud, latitud);
            return lectura;

        }

        return null;
    }

    @Override
    public Object Crear(Object object) {
        return null;
    }
}
