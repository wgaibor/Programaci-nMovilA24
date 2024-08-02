package com.example.productos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.productos.activity.CrearActivity;
import com.example.productos.activity.ListarActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button crear;
    Button listar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        crear = findViewById(R.id.btnCrear);
        listar = findViewById(R.id.btnListar);
        crear.setOnClickListener(this);
        listar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnCrear) {
            llamarActividadCrear();
        } else if (view.getId() == R.id.btnListar) {
            llamarActividadListar();
        }
    }

    private void llamarActividadListar() {
        Intent walther = new Intent(this, ListarActivity.class);
        startActivity(walther);
    }

    private void llamarActividadCrear() {
        Intent cambiar = new Intent(this, CrearActivity.class);
        startActivity(cambiar);
    }
}