package ec.edu.uce.servicios;

import android.content.Context;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import java.util.List;

import ec.edu.uce.model.Reserva;
import ec.edu.uce.model.Vehiculo;
import ec.edu.uce.storage.db.ReservaStorage;
import ec.edu.uce.storage.db.VehiculoStorage;

/**
 * <> by dacop on 03/02/2018.
 */

public class ReservaSerivce {
    private final ReservaStorage dao = ReservaStorage.getInstance();
    private final VehiculoStorage daoVehiculo = VehiculoStorage.getInstance();

    public List<Reserva> getData() {
        return dao.listar();
    }

    public void clearData() {
        dao.clearData(Reserva.TABLE_NAME);
    }

    public void add(Reserva r) {
        dao.crear(r);
    }

    public void sendNotify(final Context context, Reserva r) {

        Vehiculo v = daoVehiculo.buscarPorId(r.getVehiculo());

        String txt = "Su reserva del vehiculo Marca: " + v.getMarca() + " color " + v.getColor() + " con placa " + v.getPlaca()
                + " ha sido exitosa, para el día " + r.getFechaPrestamo() + " a " + r.getFechaEntrega() + " con un valor de: "
                + r.getValor();

        BackgroundMail.newBuilder(context)
                .withUsername("ray1187basket@gmail.com")
                .withPassword("rayricardo1187")
                .withMailto(r.getEmail())
                .withType(BackgroundMail.TYPE_PLAIN)
                .withSubject("Reserva Vehiculo UCE")
                .withBody(txt)
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(context, "Notificación enviada", Toast.LENGTH_LONG).show();
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {

                    }
                })
                .send();
    }
}
