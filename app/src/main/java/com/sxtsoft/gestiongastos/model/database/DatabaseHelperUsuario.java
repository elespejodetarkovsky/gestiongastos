package com.sxtsoft.gestiongastos.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.print.PrinterId;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelperUsuario extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GESTIONGASTOS.DB";

    private static final String LECTURAS_TABLE = "USUARIOS";

    private static final String COL_0 = "CODIGO";
    private static final String COL_1 = "FECHA_HORA";
    private static final String COL_2 = "PESO";
    private static final String COL_3 = "DIASTOLICA";
    private static final String COL_4 = "SISTOLICA";
    private static final String COL_5 = "LONGITUD";
    private static final String COL_6 = "LATITUD";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");

    public DatabaseHelperUsuario(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /*  CREATE TABLE LECTURAS(

           0     CODIGO      INTEGER PRIMARY KEY AUTOINCREMENT,
           1     FECHA_HORA  TEXT NOT NULL,
           2     PESO        REAL NOT NULL,
           3     DIASTOLICA  REAL NOT NULL,
           4     SISTOLICA   REAL NOT NULL,
           5     LONGITUD    REAL,
           6     LATITUD     REAL) */
        //se utiliza una sola vez en la aplicacion...si ve que está
        //la db.

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + LECTURAS_TABLE + " (")
                .append(COL_0).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(COL_1).append(" TEXT NOT NULL, ")
                .append(COL_2).append(" REAL NOT NULL, ")
                .append(COL_3).append(" REAl NOT NULL, ")
                .append(COL_4).append(" REAL NOT NULL, ")
                .append(COL_5).append(" REAL, ")
                .append(COL_6).append(" REAL)");

        String strDDL = sb.toString();

        db.execSQL(strDDL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LECTURAS_TABLE);
        onCreate(db);
    }


    public Lectura createLectura(Lectura lectura) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(COL_1, dateToString(lectura.getFechaHora()));
        contentValues.put(COL_2, lectura.getPeso());
        contentValues.put(COL_3, lectura.getDiastolica());
        contentValues.put(COL_4, lectura.getSistolica());
        contentValues.put(COL_5, lectura.getLongitud());
        contentValues.put(COL_6, lectura.getLatitud());

        //nullColumnHack se utiliza cuando pretendemos utilizar registros
        //con valores null. En ese caso, contentValues estará vacío (sin PUT)

        long resultado = db.insert(LECTURAS_TABLE, null, contentValues);

        //insert nos devuelve un long...la Id del registro que acaba de generar
        // o -1 si algo salió mal

        lectura.setCodigo((int) resultado);

        //si algo va mal devuelvo un null
        return resultado == -1 ? null : lectura;
    }

    public List<Lectura> getAll() {

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + LECTURAS_TABLE, null);

        List<Lectura> lecturas = new ArrayList<Lectura>();

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                Integer codigo = cursor.getInt(0);
                String strFecha = cursor.getString(1);
                double peso = cursor.getDouble(2);
                double diastolica = cursor.getDouble(3);
                double sistolica = cursor.getDouble(4);
                double longitud = cursor.getDouble(5);
                double latitud = cursor.getDouble(6);


                Lectura lectura = new Lectura(this.stringToDate(strFecha), peso, diastolica, sistolica, longitud, latitud);

                //el código de la base de datos...para que no haya discrepancias.

                lectura.setCodigo(codigo);
                lecturas.add(lectura);

            }

        }

        Log.d("*****", lecturas.toString());

        return lecturas;
    }

    public Lectura readLectura(int id) {
        //Devolverá una lectura en
        //funcion del código suministrado
        SQLiteDatabase db = getWritableDatabase();

        //creo una array de string para luego pasarlo
        //como argumento al select

        String[] campos = new String[]{COL_1, COL_2, COL_3, COL_4, COL_5, COL_6};

        String[] args = new String[]{String.valueOf(id)};

        Cursor cursor = db.query(LECTURAS_TABLE, campos, "CODIGO=?", args, null, null, null);
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


    public boolean delete(int codigo) {
        SQLiteDatabase db = getWritableDatabase();

        String[] args = new String[]{String.valueOf(codigo)};

        long resultado = db.delete(LECTURAS_TABLE, "CODIGO=?",args );

        return resultado == -1 ? false : true;

    }

    public Lectura update(Lectura lectura){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();


        contentValues.put(COL_1, this.dateToString(lectura.getFechaHora()));
        contentValues.put(COL_2, lectura.getPeso());
        contentValues.put(COL_3, lectura.getDiastolica());
        contentValues.put(COL_4, lectura.getSistolica());
        contentValues.put(COL_5, lectura.getLongitud());
        contentValues.put(COL_6, lectura.getLatitud());

        String[] args = new String[]{String.valueOf(lectura.getCodigo())};

        db.update(LECTURAS_TABLE, contentValues, "CODIGO=?", args);

        return lectura;
    }

    public List<Lectura> getBetweenDates(Date fecha1, Date fecha2) {

        //hacerlo en una consulta de base de datos

        List<Lectura> lecturas = new ArrayList<>();

        for(Lectura lectura: getAll()){     //this.getAll()

            Date fechaHora = lectura.getFechaHora();
            if (fechaHora.after(fecha1) && fechaHora.before(fecha2)){
                lecturas.add(lectura);
            }
        }

        return lecturas;
    }


    public void transferencia(){

        //BEGIN TRANSACTION
        //leemos salddo
        //si saldo cubre importe a transferir...


        //descontamos importe a cuenta origen

        //SI PETA NO SE HACE...
        //incrementamos importe a cuenta destino

        //END TRANSACTION
    }
//*****************************************************
//              METODOS PRIVADOS                    ***
//*****************************************************

    private String dateToString(Date fecha){
        //este método convertirá
        //una fecha (Date) en un string

        return sdf.format(fecha);

    }

    private Date stringToDate(String strDate){
        //este método convertirá
        //un String en una fecha

        //convierto el String to Date
        Date fecha;

        try {
            fecha = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            fecha = new Date();
        }

        return fecha;

    }



}