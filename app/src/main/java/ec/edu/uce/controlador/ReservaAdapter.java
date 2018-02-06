package ec.edu.uce.controlador;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ec.edu.uce.model.Reserva;
import ec.edu.uce.vista.R;

/**
 * <> by dacopanCM on 19/11/2017.
 */

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder> {

    private List<Reserva> datos;
    private Context context;

    public ReservaAdapter(List<Reserva> datos, Context context) {
        this.datos = datos;
        this.context = context;
    }

    @Override
    public ReservaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_reserva, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return new ReservaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReservaViewHolder viewHolder, int pos) {
        Reserva item = datos.get(pos);
        viewHolder.bindReserva(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    static class ReservaViewHolder extends RecyclerView.ViewHolder {

        private TextView id;
        private TextView celular;
        private TextView fechaPrestamo;
        private TextView fechaEntrega;
        private TextView valor;
        private TextView vehiculoTxt;


        ReservaViewHolder(View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.r_id);
            celular = itemView.findViewById(R.id.r_celular);
            fechaPrestamo = itemView.findViewById(R.id.r_fechaPrestamo);
            fechaEntrega = itemView.findViewById(R.id.r_fechaEntrega);
            valor = itemView.findViewById(R.id.r_valor);
            vehiculoTxt = itemView.findViewById(R.id.r_vehiculoTxt);

        }

        @SuppressLint("SetTextI18n")
        void bindReserva(Reserva t) {
            id.setText(t.getId()+"");
            celular.setText(t.getCelular());
            fechaPrestamo.setText(t.getFechaPrestamo());
            fechaEntrega.setText(t.getFechaEntrega());
            valor.setText(t.getValor().toString()+"  ");
            vehiculoTxt.setText(t.getVehiculoTxt());
        }
    }
}
