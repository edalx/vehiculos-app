package ec.edu.uce.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import ec.edu.uce.controlador.VehiculosAdapter;
import ec.edu.uce.model.Vehiculo;
import ec.edu.uce.servicios.VehiculoService;
import ec.edu.uce.util.PlacaComparator;
import ec.edu.uce.util.RecyclerTouchListener;


public class ListActivity extends AppCompatActivity {

    private VehiculoService storage;
    private RecyclerView recView;
    List<Vehiculo> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = new VehiculoService();// cambiar storage
        storage.dummyData();
        setContentView(R.layout.activity_list);

        //Inicialización RecyclerView
        recView = findViewById(R.id.RecView);
        recView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recView.setLayoutManager(linearLayoutManager);

        recView.addOnItemTouchListener(
                new RecyclerTouchListener(this, recView,
                        new RecyclerTouchListener.OnTouchActionListener() {
                            @Override
                            public void onClick(View view, int position) {
                                Intent intent = new Intent(ListActivity.this, CreateActivity.class);
                                Bundle b = new Bundle();
                                b.putInt("key", data.get(position).getId()); //Your id
                                intent.putExtras(b); //Put your id to your next Intent
                                startActivity(intent);
                            }

                            @Override
                            public void onRightSwipe(View view, int position) {
                                if (storage.remove(data.get(position))) {
                                    try {
                                        fillData();
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "Vehiculo No Eliminado", Toast.LENGTH_LONG).show();
                                    }
                                    Toast.makeText(getApplicationContext(), "Vehiculo Eliminado", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Vehiculo No Eliminado", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onLeftSwipe(View view, int position) {
                            }
                        }));

    }

    @Override
    protected void onStart() {
        super.onStart();
        fillData();
    }

    private void fillData() {
        data = storage.getData();
        PlacaComparator pc = new PlacaComparator(leerPreferencias());
        Collections.sort(data, pc);
        final VehiculosAdapter adaptador = new VehiculosAdapter(data, this);
        recView.setAdapter(adaptador);
    }

    public void linkCreate(View view) {
        Intent list = new Intent(ListActivity.this, CreateActivity.class);
        startActivity(list);
    }

    /**
     * @return 1 si se ordena ascendente, otro descendente
     */
    public int leerPreferencias() {
        //TODO
        return 1;
    }
}
