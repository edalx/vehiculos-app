package ec.edu.uce.storage;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import ec.edu.uce.model.Vehiculo;
import ec.edu.uce.util.CustomException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <>by dacopanCM on 18/11/2017.
 */

public class JsonStorage extends Storage {
    private static JsonStorage instance = null;

    private final String FILE_PATH;
    private static final String FILE_NAME = "vehiculos-storage.json";

    private JsonStorage(String directory) {
        this.FILE_PATH = directory + File.separator + FILE_NAME;
    }

    public static JsonStorage getInstance(Context context) {
        if (instance == null) {
            //instance = new JsonStorage(context.getFilesDir().getPath());
            instance = new JsonStorage(Environment.getExternalStorageDirectory() + "/optativa");
            try {
                instance.load();
            } catch (CustomException ex) {
                Log.e(TAG, "Error al guardar", ex);
            }
        }
        return instance;
    }

    public void load() throws CustomException {
        File f = new File(FILE_PATH);
        if (f.exists()) {
            //load data
            try {
                ObjectMapper mapper = new ObjectMapper();
                data = mapper.readValue(new File(FILE_PATH), new TypeReference<List<Vehiculo>>() {
                });

            } catch (IOException e) {
                Log.e(TAG, "Error al leer", e);
                throw new CustomException("Error al restaurar datos");
            }

        } else {
            //primera vez, create data
            data = new ArrayList<>();
            addDefaultData();
            saveAll();
        }
    }


    public void saveAll() throws CustomException {
        File f = new File(FILE_PATH);
        try {
            if (f.exists()) {
                if (!f.delete()) {
                    throw new CustomException("Error al guardar datos");
                }
            } else {
                if (!f.getParentFile().exists()) {
                    if (!f.getParentFile().mkdirs()) {
                        throw new CustomException("Error al guardar datos");
                    }
                }
                if (!f.createNewFile()) {
                    throw new CustomException("Error al guardar datos");
                }
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(f, data);
        } catch (Exception ex) {
            Log.e(TAG, "Error al guardar", ex);
            throw new CustomException("Error al guardar datos");
        }
    }


}
