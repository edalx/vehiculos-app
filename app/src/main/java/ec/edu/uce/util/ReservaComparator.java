package ec.edu.uce.util;

import java.util.Comparator;

import ec.edu.uce.model.Reserva;
import ec.edu.uce.model.Vehiculo;

/**
 * <> by dacop on 29/11/2017.
 */
public class ReservaComparator implements Comparator<Reserva> {

    private int order = -1;

    public ReservaComparator(int order) {
        this.order = order;
    }

    @Override
    public int compare(Reserva v1, Reserva v2) {
        return order * v1.getId().compareTo(v2.getId());
    }

}
