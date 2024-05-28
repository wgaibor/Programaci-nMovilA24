package ec.edu.lemas.teclemasvet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import ec.edu.lemas.teclemasvet.R;

public class DetailsItemsActivity extends AppCompatActivity {

    ImageView imgMascota;
    TextView tvNombreMascota;

    TextView tvTipoMascota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_items);
        imgMascota = findViewById(R.id.imgDetailsItems);
        tvNombreMascota = findViewById(R.id.tvTitulo);
        tvTipoMascota = findViewById(R.id.tvSubtitulo);
        Intent intento = getIntent();
        Glide.with(this)
                .load(intento.getStringExtra("imgUrl"))
                .into(imgMascota);
        tvNombreMascota.setText(intento.getStringExtra("nombreMascota"));
        tvTipoMascota.setText(intento.getStringExtra("tipoMascota"));
    }
}