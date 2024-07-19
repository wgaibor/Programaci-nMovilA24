package com.example.productolemas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.productolemas.MainActivity;
import com.example.productolemas.R;

public class MenuActiviy extends AppCompatActivity implements View.OnClickListener {

    Button btnGuardarProducto;
    Button btnListarProducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_activiy);
        btnGuardarProducto = findViewById(R.id.btnSaveProduct);
        btnListarProducto = findViewById(R.id.btnListProduct);
        btnGuardarProducto.setOnClickListener(this);
        btnListarProducto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnSaveProduct){
            llamarALaActividadGuardar();
        } else if (view.getId() == R.id.btnListProduct) {
            llamarALaActividadListar();
        }
    }

    private void llamarALaActividadListar() {
        Intent intent = new Intent(this, ListarProductoActivity.class);
        startActivity(intent);
    }

    private void llamarALaActividadGuardar() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}