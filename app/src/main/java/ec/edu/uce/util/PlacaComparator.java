package ec.edu.uce.util;

import java.util.Comparator;

import ec.edu.uce.model.Vehiculo;

/**
 * <> by dacop on 29/11/2017.
 */
public class PlacaComparator implements Comparator<Vehiculo> {

    private int order = -1;

    public PlacaComparator(int order) {
        this.order = order;
    }

    @Override
    public int compare(Vehiculo v1, Vehiculo v2) {
        return order * v1.getPlaca().compareTo(v2.getPlaca());
    }

}
