package ec.edu.uce.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageButton carros = findViewById(R.id.carros);
        ImageButton reservar = findViewById(R.id.reservar);
        ImageButton backup = findViewById(R.id.backup);

        carros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ReservaListActivity.class);
                startActivity(intent);
            }
        });

        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, BackupActivity.class);
                startActivity(intent);
            }
        });
    }
}
