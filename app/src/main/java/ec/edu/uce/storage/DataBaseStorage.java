package ec.edu.uce.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ec.edu.uce.model.Reserva;
import ec.edu.uce.model.Usuario;
import ec.edu.uce.model.Vehiculo;

/**
 * <> by dacop on 03/02/2018.
 */

public class DataBaseStorage extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "vehiculosDB";

    public DataBaseStorage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Usuario.TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,USUARIO TEXT, CLAVE TEXT)");
        db.execSQL("CREATE TABLE " + Vehiculo.TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "placa TEXT, marca TEXT, fecFab TEXT, costo REAL, matriculado INTEGER, color TEXT, foto TEXT, estado INTEGER)");
        db.execSQL("CREATE TABLE " + Reserva.TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, celular TEXT, fechaPrestamo TEXT, fechaEntrega TEXT, valor REAL, vehiculo INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void clearData(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, "ID>?", new String[]{"0"});
    }
}
