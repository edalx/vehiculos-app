package ec.edu.uce.storage;

import ec.edu.uce.model.Colors;
import ec.edu.uce.model.Vehiculo;
import ec.edu.uce.util.CustomException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * <> by dacop on 19/11/2017.
 */

public abstract class Storage {

    protected static String TAG = "vh-storage";
    protected List<Vehiculo> data;

    public abstract void load() throws CustomException;

    public abstract void saveAll() throws CustomException;

    public List<Vehiculo> getData() {
        return data;
    }

    void addDefaultData() {
        Calendar calendar = new GregorianCalendar(2016, 11, 28);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        Vehiculo item = new Vehiculo();
        item.setPlaca("PBQ-1234");
        item.setMarca("Ferrari");
        item.setFecFab("01-01-2017");
        item.setMatriculado(true);
        item.setCosto(68570.0);
        item.setColor(Colors.Otro.name());

        data.add(item);

        Vehiculo item2 = new Vehiculo();
        item2.setPlaca("PCD-2879");
        item2.setMarca("Honda");
        item2.setFecFab("23-05-2015");
        item2.setMatriculado(false);
        item2.setCosto(34690.0);
        item2.setColor(Colors.Rojo.name());

        data.add(item2);


        try {
            saveAll();
        } catch (CustomException ignored) {

        }


    }


    public void add(Vehiculo item) throws CustomException {
        data.add(item);
        saveAll();
    }

    public boolean remove(Vehiculo item) {
        return item != null && item.getPlaca() != null && data.remove(item);
    }

    public boolean remove(int position) {
        return data.remove(position) != null;
    }

    public void update(Vehiculo item, int pos) throws CustomException {
        data.set(pos, item);
        saveAll();
    }
}
