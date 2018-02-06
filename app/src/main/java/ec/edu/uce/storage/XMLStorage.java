package ec.edu.uce.storage;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ec.edu.uce.model.Vehiculo;
import ec.edu.uce.util.CustomException;

/**
 * <> by dacopanCM on 23/11/2017.
 */
public class XMLStorage extends Storage {
    private static XMLStorage instance = null;

    private final String FILE_PATH;
    private static final String FILE_NAME = "vehiculos-storage.xml";

    private XMLStorage(String directory) {
        this.FILE_PATH = directory + File.separator + FILE_NAME;
    }

    public static XMLStorage getInstance(Context context) {
        if (instance == null) {
            //instance = new JsonStorage(context.getFilesDir().getPath());
            instance = new XMLStorage(Environment.getExternalStorageDirectory() + "/optativa");
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
                XmlMapper mapper = new XmlMapper();
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
            XmlMapper mapper = new XmlMapper();
            mapper.writeValue(f, data);
        } catch (Exception ex) {
            Log.e(TAG, "Error al guardar", ex);
            throw new CustomException("Error al guardar datos");
        }
    }


}
