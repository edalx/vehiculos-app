package ec.edu.uce.vista;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.uce.model.Reserva;
import ec.edu.uce.servicios.UsuarioService;
import ec.edu.uce.storage.db.ReservaStorage;
import ec.edu.uce.storage.db.UsuarioStorage;
import ec.edu.uce.storage.db.VehiculoStorage;
import ec.edu.uce.util.CustomException;

public class LoginActivity extends AppCompatActivity {

    UsuarioService usuarioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init services and storage
        UsuarioStorage.init(this);
        VehiculoStorage.init(this);
        ReservaStorage.init(this);
        usuarioService = new UsuarioService();

        final EditText pass = findViewById(R.id.password);
        Button reset = findViewById(R.id.limpiar);

        final EditText user = findViewById(R.id.email);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String res = usuarioService.registar(user.getText().toString(), pass.getText().toString());
                    Toast.makeText(LoginActivity.this, res, Toast.LENGTH_LONG).show();
                } catch (CustomException e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        user.setText("eacuji");
        pass.setText("eacuji");
    }


    public void validarLogin(View view) {
        EditText user = findViewById(R.id.email);
        EditText pass = findViewById(R.id.password);
        try {
            usuarioService.login(user.getText().toString(), pass.getText().toString());
            Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_LONG).show();
            Intent menu = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(menu);
        } catch (CustomException e) {
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}


