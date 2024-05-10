package ec.edu.lemas.teclemasvet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnListaM;
    Button btnRegistrarM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnListaM = findViewById(R.id.btnListarMascota);
        btnRegistrarM = findViewById(R.id.btnRegistrarMascota);

        btnListaM.setOnClickListener(this);
        btnRegistrarM.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnListarMascota) {
            // Llamar a la actividad que lista las mascotas
        } else if(view.getId() == R.id.btnRegistrarMascota) {
            actividadDeRegistrarMascota();
        }
    }

    private void actividadDeRegistrarMascota() {
        Intent intento = new Intent(this, AnimalRegisterActivity.class);
        startActivity(intento);
    }
}