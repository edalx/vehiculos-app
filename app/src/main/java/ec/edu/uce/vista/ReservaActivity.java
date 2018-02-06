package ec.edu.uce.vista;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ec.edu.uce.model.Reserva;
import ec.edu.uce.model.Vehiculo;
import ec.edu.uce.servicios.ReservaSerivce;
import ec.edu.uce.servicios.VehiculoService;

public class ReservaActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    @Pattern(regex = "^[A-Z]{3}-?\\d{3,4}$", message = "Placa Inválida (AAA-999(9))")
    private EditText placa;
    @NotEmpty
    private EditText email;
    @NotEmpty
    @Pattern(regex = "^\\d{7,10}$", message = "Celular Inválida (9999999999)")
    private EditText celular;
    @NotEmpty
    private EditText fechaPrestamo;
    @NotEmpty
    private EditText fechaEntrega;

    protected Validator validator;
    protected boolean validated;
    private TextView vehiculoTxt;
    private TextView valor;
    private Vehiculo vehiculo;

    private GregorianCalendar fechaPrestamoD;
    private GregorianCalendar fechaEntregaD;

    private VehiculoService vehiculoService;
    private ReservaSerivce reservaSerivce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);

        vehiculoService = new VehiculoService();
        reservaSerivce = new ReservaSerivce();

        final LinearLayout placaV = findViewById(R.id.placaV);
        final LinearLayout detalleV = findViewById(R.id.detalle);
        placa = findViewById(R.id.placa);
        email = findViewById(R.id.email);
        celular = findViewById(R.id.celular);
        fechaPrestamo = findViewById(R.id.fechaPrestamo);
        fechaEntrega = findViewById(R.id.fechaEntrega);
        vehiculoTxt = findViewById(R.id.vehiculo);
        valor = findViewById(R.id.valor);

        Button validar = findViewById(R.id.validar);
        Button reservar = findViewById(R.id.reservar);

        fechaPrestamo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CreateActivity.DatePickerFragment newFragment = CreateActivity.DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // +1 because january is zero
                        final String selectedDate = day + "/" + (month + 1) + "/" + year;
                        fechaPrestamo.setText(selectedDate);
                        fechaPrestamoD = new GregorianCalendar(year, month, day);
                        calcValor();
                    }
                });
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        fechaEntrega.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CreateActivity.DatePickerFragment newFragment = CreateActivity.DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // +1 because january is zero
                        final String selectedDate = day + "/" + (month + 1) + "/" + year;
                        fechaEntrega.setText(selectedDate);
                        fechaEntregaD = new GregorianCalendar(year, month, day);
                        calcValor();
                    }
                });
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        placaV.setVisibility(View.VISIBLE);
        detalleV.setVisibility(View.INVISIBLE);
        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
                if (!validated) {
                    return;
                }
                vehiculo = vehiculoService.findByPlaca(placa.getText().toString());
                if (vehiculo == null) {
                    Toast.makeText(getApplicationContext(), "Vehiculo de placa " + placa.getText().toString() + " no existe", Toast.LENGTH_LONG).show();
                    placaV.setVisibility(View.VISIBLE);
                    detalleV.setVisibility(View.INVISIBLE);
                } else if (vehiculo.isEstado()) {
                    Toast.makeText(getApplicationContext(), "Vehiculo de placa " + placa.getText().toString() + " ya reservado", Toast.LENGTH_LONG).show();
                    placaV.setVisibility(View.VISIBLE);
                    detalleV.setVisibility(View.INVISIBLE);
                } else {
                    vehiculoTxt.setText(vehiculo.toDisplay());
                    placaV.setVisibility(View.INVISIBLE);
                    detalleV.setVisibility(View.VISIBLE);
                }
            }
        });

        reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
                if (!validated) {
                    return;
                }
                doReserva();
            }
        });

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    private void doReserva() {
        if (fechaEntregaD.before(fechaPrestamoD)) {
            Toast.makeText(getApplicationContext(), "Fecha de entrega no puede ser inferior ", Toast.LENGTH_LONG).show();
            return;
        }
        Reserva r = new Reserva();

        r.setEmail(email.getText().toString());
        r.setCelular(celular.getText().toString());
        r.setFechaPrestamo(fechaPrestamo.getText().toString());
        r.setFechaEntrega(fechaEntrega.getText().toString());
        r.setVehiculo(vehiculo.getId());
        double valor = 80 * daysBetween(fechaPrestamoD.getTime(), fechaEntregaD.getTime());
        r.setValor(valor);

        try {
            reservaSerivce.add(r);
            vehiculo.setEstado(true);
            vehiculoService.update(vehiculo, null);
            reservaSerivce.sendNotify(this, r);
            Toast.makeText(getApplicationContext(), "Reserva exitosa", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    @SuppressLint("SetTextI18n")
    private void calcValor() {
        try {
            double val = 80 * daysBetween(fechaPrestamoD.getTime(), fechaEntregaD.getTime());
            valor.setText(val + "");
        } catch (Exception ignored) {

        }
    }

    @Override
    public void onValidationSucceeded() {
        validated = true;
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        validated = false;

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof TextView) {
                TextView et = (TextView) view;
                et.setError(message);
            }
        }
    }
}
