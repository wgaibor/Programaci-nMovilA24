package com.example.productos.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.productos.ProductoApplication;
import com.example.productos.R;
import com.example.productos.entity.ItemEntity;
import com.example.productos.entity.ProductoRequestEntity;
import com.example.productos.entity.ResponseEntity;
import com.example.productos.interfaces.IProducto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearActivity extends AppCompatActivity implements View.OnClickListener {

    EditText marca;
    EditText modelo;
    EditText descripcion;
    EditText precio;
    EditText imagen;
    Button guardar;

    Call<ResponseEntity> entityCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);
        marca = findViewById(R.id.edtMarca);
        modelo = findViewById(R.id.edtModelo);
        descripcion = findViewById(R.id.edtDescripcion);
        precio = findViewById(R.id.edtPrecio);
        imagen = findViewById(R.id.edtImagen);
        guardar = findViewById(R.id.btnAlmacenar);
        guardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAlmacenar){
            llamarWSGuardar();
        }
    }

    private void llamarWSGuardar() {
        IProducto iProducto = ProductoApplication.getmInstance().getRestAdapter().create(IProducto.class);

        ItemEntity item = new ItemEntity();
        item.setMarca(marca.getText().toString());
        item.setModelo(modelo.getText().toString());
        item.setDescripcion(descripcion.getText().toString());
        item.setPrecio(precio.getText().toString());
        item.setImagen(imagen.getText().toString());

        ProductoRequestEntity productoRequest = new ProductoRequestEntity();
        productoRequest.setOp(getString(R.string.op_guardar));
        productoRequest.setData(item);

        entityCall = iProducto.requestItem(productoRequest);
        entityCall.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if (response.isSuccessful()){
                    ResponseEntity objeto = response.body();
                    Toast.makeText(CrearActivity.this, objeto.getMensaje(), Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}