package ec.edu.uce.servicios;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import ec.edu.uce.model.Colors;
import ec.edu.uce.model.Vehiculo;
import ec.edu.uce.storage.db.VehiculoStorage;
import ec.edu.uce.util.CustomException;

/**
 * <> by dacop on 03/02/2018.
 */

public class VehiculoService {

    private final VehiculoStorage dao = VehiculoStorage.getInstance();

    public Vehiculo findById(String id) {
        return dao.buscarPorId(id);
    }

    public List<Vehiculo> getData() {
        return dao.listar();
    }

    public boolean remove(Vehiculo vehiculo) {
        return dao.borrar(vehiculo);
    }

    public void add(Vehiculo item) throws CustomException {
        Vehiculo v = dao.buscarPorParametro(item.getPlaca());

        if (v != null) {
            throw new CustomException("Vehiculo con placa ya existe");
        }
        dao.crear(item);
    }

    public void update(Vehiculo item, String oldKey) throws CustomException {
        Vehiculo v = dao.buscarPorParametro(item.getPlaca());

        if (v != null && !item.getId().equals(v.getId())) {// vechiculo con misma placa pero id distinto
            throw new CustomException("Vehiculo con placa ya existe");
        }
        dao.actualizar(item);
    }

    public void dummyData() {
        if (getData().size() < 1) {
            Calendar calendar = new GregorianCalendar(2016, 11, 28);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


            Vehiculo item = new Vehiculo();
            item.setId(1);
            item.setPlaca("PAA1234");
            item.setMarca("Ferrari");
            item.setFecFab("01-01-2017");
            item.setMatriculado(true);
            item.setCosto(68570.0);
            item.setColor(Colors.Otro.name());

            dao.crear(item);

            Vehiculo item2 = new Vehiculo();
            item2.setId(2);
            item2.setPlaca("PCD2879");
            item2.setMarca("Honda");
            item2.setFecFab("23-05-2015");
            item2.setMatriculado(false);
            item2.setCosto(34690.0);
            item2.setColor(Colors.Rojo.name());

            dao.crear(item2);
        }
    }

    public void clearData() {
        dao.clearData(Vehiculo.TABLE_NAME);
    }

    public Vehiculo findByPlaca(String placa) {
        return dao.buscarPorParametro(placa);
    }
}
