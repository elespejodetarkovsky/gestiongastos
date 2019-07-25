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
                .append(COL_5).append(" REAL, ");

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

    public Usuario createUsuario(Usuario usuario) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(COL_1, usuario.getNombre());
        contentValues.put(COL_2, usuario.getApellido());
        contentValues.put(COL_3, usuario.getUserName());
        contentValues.put(COL_4, usuario.getGenero().toString());
        contentValues.put(COL_5, usuario.getPassword());

        //nullColumnHack se utiliza cuando pretendemos utilizar registros
        //con valores null. En ese caso, contentValues estará vacío (sin PUT)

        long resultado = db.insert(USUARIOS_TABLE, null, contentValues);

        //insert nos devuelve un long...la Id del registro que acaba de generar
        // o -1 si algo salió mal

        //si algo va mal devuelvo un null
        return resultado == -1 ? null : usuario;
    }
}
