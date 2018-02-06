package ec.edu.uce.vista;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ec.edu.uce.model.Backup;
import ec.edu.uce.model.Reserva;
import ec.edu.uce.model.Usuario;
import ec.edu.uce.model.Vehiculo;
import ec.edu.uce.servicios.ReservaSerivce;
import ec.edu.uce.servicios.UsuarioService;
import ec.edu.uce.servicios.VehiculoService;
import ec.edu.uce.util.CustomException;

public class BackupActivity extends AppCompatActivity {

    private String TAG = "BackupActivity";

    UsuarioService usuarioService;
    VehiculoService vehiculoService;
    ReservaSerivce reservaSerivce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);

        usuarioService = new UsuarioService();
        vehiculoService = new VehiculoService();
        reservaSerivce = new ReservaSerivce();


        Button exportar = findViewById(R.id.exportar);
        Button importar = findViewById(R.id.importar);
        Button reset = findViewById(R.id.reset);

        exportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UsuarioService usuarioService = new UsuarioService();
                VehiculoService vehiculoService = new VehiculoService();
                ReservaSerivce reservaSerivce = new ReservaSerivce();
                Backup backup = new Backup();

                backup.setUsuarios(usuarioService.getData());
                backup.setVehiculos(vehiculoService.getData());
                backup.setReservas(reservaSerivce.getData());

                doExport(backup);

            }
        });

        importar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO seleccionar archivo
                String file = "";
                doImport(file);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetData();
            }
        });

    }

    private void doExport(Backup backup) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);

        String path = Environment.getExternalStorageDirectory() + "/optativa/";
        String FILE_PATH = path + "vehiculos_" + formatter.format(new Date()) + ".json";

        File f = new File(FILE_PATH);
        try {
            if (f.exists()) {
                if (!f.delete()) {
                    throw new CustomException("Error al guardar datos");
                }
            } else {
                if (!f.getParentFile().exists()) {
                    if (!f.getParentFile().mkdirs()) {
                        throw new CustomException("Error al guardar datos");
                    }
                }
                if (!f.createNewFile()) {
                    throw new CustomException("Error al guardar datos");
                }
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(f, backup);
            Toast.makeText(BackupActivity.this, "Exportado con éxito!", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.e(TAG, "Error al guardar", ex);
            Toast.makeText(BackupActivity.this, "Error al guardar: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void doImport(String filename) {
        File f = new File(filename);
        if (f.exists()) {
            //load data
            try {
                ObjectMapper mapper = new ObjectMapper();
                Backup data = mapper.readValue(f, new TypeReference<Backup>() {
                });

                resetData();

                //users
                List<Usuario> users = data.getUsuarios();
                for (Usuario u : users) {
                    try {
                        usuarioService.registar(u.getUsuario(), u.getClave());
                    } catch (CustomException e) {
                        e.printStackTrace();
                    }
                }

                List<Vehiculo> vehiculos = data.getVehiculos();
                for (Vehiculo v : vehiculos) {
                    try {
                        vehiculoService.add(v);
                    } catch (CustomException e) {
                        e.printStackTrace();
                    }
                }

                List<Reserva> reservas = data.getReservas();
                for (Reserva r : reservas) {
                    try {
                        reservaSerivce.add(r);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (IOException ex) {
                Log.e(TAG, "Error al leer", ex);
                Toast.makeText(BackupActivity.this, "Error al importar: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }

    private void resetData() {
        usuarioService.clearData();
        vehiculoService.clearData();
        reservaSerivce.clearData();
    }
}
