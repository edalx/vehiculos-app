package ec.edu.uce.vista;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.DecimalMin;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.Calendar;
import java.util.List;

import ec.edu.uce.model.Colors;
import ec.edu.uce.model.Vehiculo;
import ec.edu.uce.servicios.VehiculoService;
import ec.edu.uce.util.CustomException;

public class CreateActivity extends AppCompatActivity implements Validator.ValidationListener {

    private VehiculoService storage;
    protected Validator validator;
    protected boolean validated;
    private Vehiculo item;
    private Integer oldKey;


    @Pattern(regex = "^[A-Z]{3}-?\\d{3,4}$", message = "Placa Inválida (AAA-999(9))")
    private EditText placafield;
    @NotEmpty
    private EditText marcafield;

    private EditText fecFabfield;
    @DecimalMin(value = 1500, message = "Costo Mínimo $1500")
    private EditText costofield;

    private Spinner colorfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        storage = new VehiculoService();// cambiar storage
        setContentView(R.layout.activity_create);
        //Cargar el enumerado en el Spinner
        Spinner mySpinner = findViewById(R.id.color);
        mySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Colors.values()));

        validator = new Validator(this);
        validator.setValidationListener(this);

        Bundle b = getIntent().getExtras();
        if (b != null && b.containsKey("key")) {
            oldKey = b.getInt("key");
            initView();
        } else {
            oldKey = null;
            initView();
        }
    }

    /**
     * Inciializa los controles y el fragment para mostrar el selector de fecha
     */
    @SuppressLint("SetTextI18n")
    private void initView() {
        placafield = findViewById(R.id.placa);
        marcafield = findViewById(R.id.marca);
        fecFabfield = findViewById(R.id.fecFab);
        costofield = findViewById(R.id.costo);
        colorfield = findViewById(R.id.color);

        fecFabfield.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // +1 because january is zero
                        final String selectedDate = day + " / " + (month + 1) + " / " + year;
                        fecFabfield.setText(selectedDate);
                    }
                });
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        //Reset
        Button clean = findViewById(R.id.limpiar);
        //
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placafield.setText("");
                marcafield.setText("");
                fecFabfield.setText("");
                costofield.setText("");
            }
        });

        // restore data car selected
        if (oldKey != null) {
            item = storage.findById(oldKey.toString());
            CheckBox matricul = findViewById(R.id.matriculado);
            CheckBox reservado = findViewById(R.id.reservado);

            placafield.setText(item.getPlaca());
            marcafield.setText(item.getMarca());
            fecFabfield.setText(item.getFecFab());
            costofield.setText(item.getCosto().toString());
            reservado.setChecked(item.isEstado());

            Colors[] colors = Colors.values();
            for (int i = 0; i < colors.length; i++) {
                if (colors[i].toString().equals(item.getColor())) {
                    colorfield.setSelection(i);
                }
            }

            matricul.setChecked(item.isMatriculado());
        } else {
            item = new Vehiculo();
        }
    }

    public void IngresarV(View view) {
        validator.validate();
        if (!validated) {
            return;
        }
        //Leer los campos del formulario
        String placa = placafield.getText().toString();
        String marca = marcafield.getText().toString();
        String fecFab = fecFabfield.getText().toString();
        String costo = costofield.getText().toString();
        String color = colorfield.getSelectedItem().toString();
        CheckBox matricul = findViewById(R.id.matriculado);
        CheckBox reservado = findViewById(R.id.reservado);
        boolean matriculado = matricul.isChecked();

        /*Creacion del Vehiculo*/
        try {
            item.setPlaca(placa);
            item.setMarca(marca);
            item.setFecFab(fecFab);
            item.setCosto(Double.parseDouble(costo));
            item.setColor(color);
            item.setMatriculado(matriculado);
            item.setEstado(reservado.isChecked());
            if (oldKey == null) {
                storage.add(item);
            } else {
                storage.update(item, null);
            }
            Toast.makeText(getApplicationContext(), "Vehículo " + (oldKey == null ? "Ingresado" : "Actualizado"), Toast.LENGTH_LONG).show();
            finish();
        } catch (CustomException ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
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

    public void deleteVehiculo(View view) {
        try {
            if (storage.remove(item)) {
                Toast.makeText(getApplicationContext(), "Vehiculo Eliminado", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Vehiculo No Eliminado", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Vehiculo No Eliminado", Toast.LENGTH_LONG).show();
        }


        finish();
    }

    public static class DatePickerFragment extends DialogFragment {
        private DatePickerDialog.OnDateSetListener listener;

        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }
    }
}
