package ec.edu.uce.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.Collections;
import java.util.List;

import ec.edu.uce.controlador.ReservaAdapter;
import ec.edu.uce.model.Reserva;
import ec.edu.uce.servicios.ReservaSerivce;
import ec.edu.uce.servicios.VehiculoService;
import ec.edu.uce.util.ReservaComparator;

public class ReservaListActivity extends AppCompatActivity {

    private ReservaSerivce storage;
    private RecyclerView recView;
    List<Reserva> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_list);

        storage = new ReservaSerivce();
        //Inicialización RecyclerView
        recView = findViewById(R.id.RecView);
        recView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recView.setLayoutManager(linearLayoutManager);

        FloatingActionButton addBtn = findViewById(R.id.addReserva);
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReservaListActivity.this, ReservaActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        fillData();
    }

    private void fillData() {
        VehiculoService vehiculoService = new VehiculoService();
        data = storage.getData();
        for (Reserva r : data) {
            r.setVehiculoTxt(vehiculoService.findById(r.getVehiculo().toString()).toDisplay());
        }
        ReservaComparator pc = new ReservaComparator(-1);
        Collections.sort(data, pc);
        final ReservaAdapter adaptador = new ReservaAdapter(data, this);
        recView.setAdapter(adaptador);
    }
}
