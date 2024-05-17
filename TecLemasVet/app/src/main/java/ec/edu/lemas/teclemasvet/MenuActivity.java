package ec.edu.lemas.teclemasvet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ec.edu.lemas.teclemasvet.activity.AnimalRegisterActivity;
import ec.edu.lemas.teclemasvet.activity.ListPetsActivity;
import ec.edu.lemas.teclemasvet.activity.UploadImageActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnListaM;
    Button btnRegistrarM;
    Button btnSubirImagenM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnListaM = findViewById(R.id.btnListarMascota);
        btnRegistrarM = findViewById(R.id.btnRegistrarMascota);
        btnSubirImagenM = findViewById(R.id.btnSubirImagenes);

        btnListaM.setOnClickListener(this);
        btnRegistrarM.setOnClickListener(this);
        btnSubirImagenM.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnListarMascota) {
            actividadListarMascota();
        } else if(view.getId() == R.id.btnRegistrarMascota) {
            actividadDeRegistrarMascota();
        } else if (view.getId() == R.id.btnSubirImagenes) {
            actividadSubirImagen();
        }
    }

    private void actividadSubirImagen() {
        Intent intento = new Intent(this, UploadImageActivity.class);
        startActivity(intento);
    }

    private void actividadListarMascota() {
        Intent intento = new Intent(this, ListPetsActivity.class);
        startActivity(intento);
    }

    private void actividadDeRegistrarMascota() {
        Intent intento = new Intent(this, AnimalRegisterActivity.class);
        startActivity(intento);
    }
}