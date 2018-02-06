package ec.edu.uce.storage.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ec.edu.uce.model.Usuario;
import ec.edu.uce.storage.DataBaseStorage;
import ec.edu.uce.storage.InterfazCRUD;

/**
 * <> by dacop on 03/02/2018.
 */

public class UsuarioStorage extends DataBaseStorage implements InterfazCRUD<Usuario> {

    private static UsuarioStorage instance = null;

    public static void init(Context context) {
        if (instance == null) {
            instance = new UsuarioStorage(context);
        }
    }

    private UsuarioStorage(Context context) {
        super(context);
    }

    public static UsuarioStorage getInstance() {
        return instance;
    }

    @Override
    public String crear(Usuario obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Usuario.KEY_USUARIO, obj.getUsuario());
        contentValues.put(Usuario.KEY_CLAVE, obj.getClave());
        long result = db.insert(Usuario.TABLE_NAME, null, contentValues);
        db.close();
        if (result == -1) {
            return "Usuario no registrado";
        } else {
            return "Usuario registrado";
        }
    }

    @Override
    public String actualizar(Usuario id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Usuario.KEY_USUARIO, id.getUsuario());
        contentValues.put(Usuario.KEY_CLAVE, id.getClave());

        int result = db.update(Usuario.TABLE_NAME, contentValues, Usuario.KEY_USUARIO + "=?", new String[]{id.getUsuario()});
        if (result > 0) {
            return "Actualizado";
        } else {
            return "No actualizado";
        }
    }

    @Override
    public boolean borrar(Object id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(Usuario.TABLE_NAME, Usuario.KEY_USUARIO + "=?", new String[]{id.toString()});
        return result > 0;
    }

    @Override
    public Usuario buscarPorParametro(Object parametro) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT USUARIO,CLAVE FROM USUARIO where usuario=?", new String[]{parametro.toString()});
        if (res.getCount() < 1) {
            return null;
        }

        res.moveToFirst();
        Usuario u = new Usuario(res.getString(0), res.getString(1));
        res.close();
        return u;
    }

    @Override
    public List<Usuario> listar() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT USUARIO,CLAVE FROM USUARIO", null);

        List<Usuario> usuarioList = new ArrayList<>();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                usuarioList.add(new Usuario(res.getString(0), res.getString(1)));
            }
            res.close();
        }
        return usuarioList;
    }
}
