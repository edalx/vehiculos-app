package ec.edu.uce.storage;

import android.util.Log;

import ec.edu.uce.util.CustomException;

import java.util.ArrayList;

/**
 * <> by dacopanCM on 15/11/2017.
 */

public class InMemoryStorage extends Storage {
    private static InMemoryStorage instance = null;

    private InMemoryStorage() {
        // Exists only to defeat instantiation.
    }

    public static InMemoryStorage getInstance() {
        if (instance == null) {
            instance = new InMemoryStorage();
            try {
                instance.load();
            } catch (CustomException ex) {
                Log.e(TAG, "Error al guardar", ex);
            }
        }
        return instance;
    }


    @Override
    public void load() throws CustomException {
        data = new ArrayList<>();
        addDefaultData();
    }

    @Override
    public void saveAll() throws CustomException {
        // in memory no necesario
    }
}
