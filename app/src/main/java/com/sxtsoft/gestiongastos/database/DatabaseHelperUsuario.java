package com.sxtsoft.gestiongastos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sxtsoft.gestiongastos.model.Gender;
import com.sxtsoft.gestiongastos.model.Usuario;

public class DatabaseHelperUsuario {

    private Context context;
    //private SQLiteDatabase sqLiteDatabase;

    public DatabaseHelperUsuario(Context context) {
        this.context = context;
    }



    public Cursor getAll() {
        SQLiteDatabase db = DatabaseHelper.getInstance(this.context).getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.USUARIOS_TABLE, null);

        return cursor;
    }

    public boolean delete(int codigo) {
        return false;
    }


    public Object update(Object object) {
        return null;
    }


    public Usuario create(Usuario usuario) {

        SQLiteDatabase db = DatabaseHelper.getInstance(this.context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(Utilidades.USUARIOS_COL_1, usuario.getNombre());
        contentValues.put(Utilidades.USUARIOS_COL_2, usuario.getApellido());
        contentValues.put(Utilidades.USUARIOS_COL_3, usuario.getUserName());
        contentValues.put(Utilidades.USUARIOS_COL_4, usuario.getGenero().toString());
        contentValues.put(Utilidades.USUARIOS_COL_5, usuario.getPassword());
        contentValues.put(Utilidades.USUARIOS_COL_6, usuario.getGrupo().getCodigo());

        //nullColumnHack se utiliza cuando pretendemos utilizar registros
        //con valores null. En ese caso, contentValues estará vacío (sin PUT)

        long resultado = db.insert(Utilidades.USUARIOS_TABLE, null, contentValues);

        //insert nos devuelve un long...la Id del registro que acaba de generar
        // o -1 si algo salió mal

        usuario.setCodigo(resultado);

        //si algo va mal devuelvo un null
        return resultado == -1 ? null : usuario;


    }

    public long validarUser(String nombreUsuario, String clave){

        SQLiteDatabase db = DatabaseHelper.getInstance(this.context).getWritableDatabase();

        String tabla = Utilidades.USUARIOS_TABLE;

        String[] campos = {Utilidades.USUARIOS_COL_0};

        String[] args = {nombreUsuario, clave};

        Cursor cursor = db.query(tabla,campos,Utilidades.USUARIOS_COL_3 + "=? AND " + Utilidades.USUARIOS_COL_5 + "=?", args, null,null, null);


        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToNext();

            long usuarioID = cursor.getLong(0);

            return usuarioID;

        }

        return -1;
    }

    public Usuario readUserById(long userID){
        //Devolverá una lectura en
        //funcion del código suministrado

        SQLiteDatabase db = DatabaseHelper.getInstance(this.context).getWritableDatabase();

        //creo una array de string para luego pasarlo
        //como argumento al select

        String[] campos = new String[]{Utilidades.USUARIOS_COL_1,
                Utilidades.USUARIOS_COL_2,
                Utilidades.USUARIOS_COL_3,
                Utilidades.USUARIOS_COL_5};

        String[] args = new String[]{String.valueOf(userID)};

        Cursor cursor = db.query(Utilidades.USUARIOS_TABLE, campos, Utilidades.USUARIOS_COL_0 + "=?", args, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToNext();

            String nombre = cursor.getString(0);
            String apellido = cursor.getString(1);
            String userName = cursor.getString(2);
            String clave = cursor.getString(3);

            Usuario usuario = new Usuario(nombre,apellido,userName,clave);
            usuario.setCodigo(userID);

            return usuario;

        }

        return null;
    }


//    public Usuario readUsuario(String userName) {
//        //Devolverá una lectura en
//        //funcion del código suministrado
//        SQLiteDatabase db = getWritableDatabase();
//
//        //creo una array de string para luego pasarlo
//        //como argumento al select
//
//        String[] campos = new String[]{COL_1, COL_2, COL_3, COL_4, COL_5};
//
//        //String[] args = new String[]{String.valueOf(id)};
//
//        //Cursor cursor = db.query(USUARIOS_TABLE, campos, "CODIGO=?", args, null, null, null);
////        Cursor cursor = db.rawQuery("SELECT * FROM " + LECTURAS_TABLE +
////                " WHERE CODIGO = ? ", args);
////
////        if (cursor != null && cursor.getCount() > 0) {
////
////            cursor.moveToNext();
////
////            //Integer codigo = cursor.getInt(0);
////            //el getxxx indica la forma en que lo quieres
////            //por ejemplo el entero 1...el getString será "1"
////
////            String strFecha = cursor.getString(0);
////            double peso = cursor.getDouble(1);
////            double diastolica = cursor.getDouble(2);
////            double sistolica = cursor.getDouble(3);
////            double longitud = cursor.getDouble(4);
////            double latitud = cursor.getDouble(5);
//
//
//            //Lectura lectura = new Lectura(this.stringToDate(strFecha), peso, diastolica, sistolica, longitud, latitud);
//            //return lectura;
//            //return null;
//
//        //}
//
//        return null;
//    }

    public Object Crear(Object object) {
        return null;
    }
}
