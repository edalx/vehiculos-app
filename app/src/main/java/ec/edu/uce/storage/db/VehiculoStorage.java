package ec.edu.uce.storage.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ec.edu.uce.model.Vehiculo;
import ec.edu.uce.storage.DataBaseStorage;
import ec.edu.uce.storage.InterfazCRUD;

/**
 * <> by dacop on 03/02/2018.
 */
public class VehiculoStorage extends DataBaseStorage implements InterfazCRUD<Vehiculo> {

    private static VehiculoStorage instance = null;

    public static void init(Context context) {
        if (instance == null) {
            instance = new VehiculoStorage(context);
        }
    }

    private VehiculoStorage(Context context) {
        super(context);
    }

    public static VehiculoStorage getInstance() {
        return instance;
    }

    @Override
    public String crear(Vehiculo obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = getContentValues(obj);

        long result = db.insert(Vehiculo.TABLE_NAME, null, contentValues);
        db.close();
        if (result == -1) {
            return "Vehiculo registrado";
        } else {
            return "Vehiculo No registrado";
        }
    }

    @Override
    public String actualizar(Vehiculo obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = getContentValues(obj);

        int result = db.update(Vehiculo.TABLE_NAME, contentValues, "id=?", new String[]{obj.getId().toString()});
        if (result > 0) {
            return "Actualizado";
        } else {
            return "No actualizado";
        }
    }

    @Override
    public boolean borrar(Object obj) {
        Vehiculo v = (Vehiculo) obj;
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(Vehiculo.TABLE_NAME, "id=?", new String[]{v.getId().toString()});
        return result > 0;
    }

    @Override
    public Vehiculo buscarPorParametro(Object placa) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT ID, placa, marca, fecFab, costo, matriculado, color, foto, estado FROM " + Vehiculo.TABLE_NAME + " where placa=?", new String[]{placa.toString()});
        if (res.getCount() < 1) {
            return null;
        }

        res.moveToFirst();
        Vehiculo u = constructVehiculo(res);
        res.close();
        return u;
    }

    public Vehiculo buscarPorId(Object id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT ID, placa, marca, fecFab, costo, matriculado, color, foto, estado FROM " + Vehiculo.TABLE_NAME + " where ID=" + id.toString(), null);
        if (res.getCount() < 1) {
            return null;
        }

        res.moveToFirst();
        Vehiculo u = constructVehiculo(res);
        res.close();
        return u;
    }

    @Override
    public List<Vehiculo> listar() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT ID, placa, marca, fecFab, costo, matriculado, color, foto, estado FROM " + Vehiculo.TABLE_NAME, null);

        List<Vehiculo> usuarioList = new ArrayList<>();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                usuarioList.add(constructVehiculo(res));
            }
            res.close();
        }
        return usuarioList;
    }

    private ContentValues getContentValues(Vehiculo obj) {
        ContentValues contentValues = new ContentValues();
        if (obj.getId() != null) {
            contentValues.put("ID", obj.getId());
        }
        contentValues.put("placa", obj.getPlaca());
        contentValues.put("marca", obj.getMarca());
        contentValues.put("fecFab", obj.getFecFab());
        contentValues.put("costo", obj.getCosto());
        contentValues.put("matriculado", obj.isMatriculado());
        contentValues.put("color", obj.getColor());
        contentValues.put("foto", obj.getFoto());
        contentValues.put("estado", obj.isEstado());
        return contentValues;
    }

    private Vehiculo constructVehiculo(Cursor res) {
        Vehiculo v = new Vehiculo();
        v.setId(res.getInt(0));
        v.setPlaca(res.getString(1));
        v.setMarca(res.getString(2));
        v.setFecFab(res.getString(3));
        v.setCosto(res.getDouble(4));
        v.setMatriculado(res.getInt(5) > 0);
        v.setColor(res.getString(6));
        v.setFoto(res.getString(7));
        v.setEstado(res.getInt(8) > 0);
        return v;
    }

}
