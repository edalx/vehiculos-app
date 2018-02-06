package ec.edu.uce.storage.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ec.edu.uce.model.Reserva;
import ec.edu.uce.storage.DataBaseStorage;
import ec.edu.uce.storage.InterfazCRUD;

/**
 * <> by dacop on 03/02/2018.
 */
public class ReservaStorage extends DataBaseStorage implements InterfazCRUD<Reserva> {

    private static ReservaStorage instance = null;

    public static void init(Context context) {
        if (instance == null) {
            instance = new ReservaStorage(context);
        }
    }

    private ReservaStorage(Context context) {
        super(context);
    }

    public static ReservaStorage getInstance() {
        return instance;
    }

    @Override
    public String crear(Reserva obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = getContentValues(obj);

        long result = db.insert(Reserva.TABLE_NAME, null, contentValues);
        db.close();
        if (result == -1) {
            return "Reserva realizada";
        } else {
            return "Reserva No registrado";
        }
    }

    @Override
    public String actualizar(Reserva obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = getContentValues(obj);

        int result = db.update(Reserva.TABLE_NAME, contentValues, "id=?", new String[]{obj.getId().toString()});
        if (result > 0) {
            return "Actualizado";
        } else {
            return "No actualizado";
        }
    }

    @Override
    public boolean borrar(Object obj) {
        Reserva v = (Reserva) obj;
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(Reserva.TABLE_NAME, "id=?", new String[]{v.getId().toString()});
        return result > 0;
    }

    @Override
    public Reserva buscarPorParametro(Object placa) {
        return null;
    }

    public Reserva buscarPorId(Object id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT ID, email, celular, fechaPrestamo, fechaEntrega, valor, vehiculo where ID=" + id.toString(), null);
        if (res.getCount() < 1) {
            return null;
        }

        res.moveToFirst();
        Reserva u = constructReserva(res);
        res.close();
        return u;
    }

    @Override
    public List<Reserva> listar() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT ID, email, celular, fechaPrestamo, fechaEntrega, valor, vehiculo FROM " + Reserva.TABLE_NAME, null);

        List<Reserva> usuarioList = new ArrayList<>();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                usuarioList.add(constructReserva(res));
            }
            res.close();
        }
        return usuarioList;
    }

    private ContentValues getContentValues(Reserva obj) {
        ContentValues contentValues = new ContentValues();
        if (obj.getId() != null) {
            contentValues.put("ID", obj.getId());
        }
        contentValues.put("email", obj.getEmail());
        contentValues.put("celular", obj.getCelular());
        contentValues.put("fechaPrestamo", obj.getFechaPrestamo());
        contentValues.put("fechaEntrega", obj.getFechaPrestamo());
        contentValues.put("valor", obj.getValor());
        contentValues.put("vehiculo", obj.getVehiculo());
        return contentValues;
    }

    private Reserva constructReserva(Cursor res) {
        Reserva v = new Reserva();
        v.setId(res.getInt(0));
        v.setEmail(res.getString(1));
        v.setCelular(res.getString(2));
        v.setFechaPrestamo(res.getString(3));
        v.setFechaEntrega(res.getString(4));
        v.setValor(res.getDouble(5));
        v.setVehiculo(res.getInt(6));
        return v;
    }

}
