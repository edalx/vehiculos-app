package ec.edu.uce.model;

import java.util.List;

/**
 * <> by dacop on 03/02/2018.
 */

public class Backup {

    private List<Vehiculo> vehiculos;
    private List<Usuario> usuarios;
    private List<Reserva> reservas;

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}
