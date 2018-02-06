package ec.edu.uce.controlador;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ec.edu.uce.model.Vehiculo;
import ec.edu.uce.vista.R;

/**
 * <> by dacopanCM on 19/11/2017.
 */

public class VehiculosAdapter extends RecyclerView.Adapter<VehiculosAdapter.VehiculosViewHolder> {

    private List<Vehiculo> datos;
    private Context context;

    public VehiculosAdapter(List<Vehiculo> datos, Context context) {
        this.datos = datos;
        this.context = context;
    }

    @Override
    public VehiculosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_vehiculo, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return new VehiculosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VehiculosViewHolder viewHolder, int pos) {
        Vehiculo item = datos.get(pos);
        viewHolder.bindVehiculo(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    static class VehiculosViewHolder extends RecyclerView.ViewHolder {

        private TextView placa;
        private TextView marca;
        private TextView fecFab;
        private TextView costo;
        private TextView matriculado;
        private TextView color;

        VehiculosViewHolder(View itemView) {
            super(itemView);

            placa = itemView.findViewById(R.id.car_placa);
            marca = itemView.findViewById(R.id.car_marca);
            fecFab = itemView.findViewById(R.id.car_fecFab);
            costo = itemView.findViewById(R.id.car_costo);
            matriculado = itemView.findViewById(R.id.car_matriculado);
            color = itemView.findViewById(R.id.car_color);

        }

        @SuppressLint("SetTextI18n")
        void bindVehiculo(Vehiculo t) {
            placa.setText(t.getPlaca());
            marca.setText(t.getMarca());
            fecFab.setText(t.getFecFab());
            costo.setText("$" + t.getCosto().toString());
            matriculado.setText(t.isMatriculado() ? "Matriculado: Si" : "Matriculado: No");
            color.setText(t.getColor() + " | " + (t.isEstado() ? "Reservado" : ""));

        }
    }
}
