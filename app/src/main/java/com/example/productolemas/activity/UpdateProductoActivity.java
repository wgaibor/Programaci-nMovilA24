package com.example.productolemas.activity;

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

import com.example.productolemas.LemasApplication;
import com.example.productolemas.R;
import com.example.productolemas.entity.ProductoEntity;
import com.example.productolemas.entity.ProductoRequestEntity;
import com.example.productolemas.entity.ResponseEntity;
import com.example.productolemas.interfaces.Ilemas;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProductoActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etIdProducto;
    EditText etMarca;
    EditText etModelo;
    EditText etDescripcion;
    EditText etPrecio;
    EditText etImagen;
    Button btnActualizar;

    int idProducto;
    String marca;
    String modelo;
    String descripcion;
    String precio;
    String estado;
    String imagen;

    Call<ResponseEntity> callActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etIdProducto = findViewById(R.id.etIdProducto);
        etMarca = findViewById(R.id.etMarca);
        etModelo = findViewById(R.id.etModelo);
        etDescripcion = findViewById(R.id.etDescripcion);
        etPrecio = findViewById(R.id.etPrecio);
        etImagen = findViewById(R.id.etImagen);
        btnActualizar = findViewById(R.id.btnGuardar);
        btnActualizar.setText("Actualizar");
        btnActualizar.setOnClickListener(this);
        etIdProducto.setVisibility(View.VISIBLE);
        etIdProducto.setEnabled(false);
        Bundle extras = getIntent().getExtras();

        idProducto = extras.getInt("idProducto", 9999);
        marca = extras.getString("marca");
        modelo = extras.getString("modelo");
        descripcion = extras.getString("descripcion");
        precio = extras.getString("precio");
        estado = extras.getString("estado");
        imagen = extras.getString("imagen");

        etIdProducto.setText(idProducto+"");
        etMarca.setText(marca);
        etModelo.setText(modelo);
        etDescripcion.setText(descripcion);
        etPrecio.setText(precio);
        etImagen.setText(imagen);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnGuardar){
            llamarWSActualizar();
        }
    }

    private void llamarWSActualizar() {
        Ilemas ilemas = LemasApplication.getmInstance().getRestAdapter().create(Ilemas.class);
        ProductoEntity entidadProducto = new ProductoEntity();
        entidadProducto.setIdProducto(Integer.valueOf(etIdProducto.getText().toString()));
        entidadProducto.setMarca(etMarca.getText().toString());
        entidadProducto.setModelo(etModelo.getText().toString());
        entidadProducto.setDescripcion(etDescripcion.getText().toString());
        entidadProducto.setEstado("Activo");
        entidadProducto.setPrecio(etPrecio.getText().toString());
        entidadProducto.setImagen(etImagen.getText().toString());

        ProductoRequestEntity request = new ProductoRequestEntity();
        request.setOp(getString(R.string.op_actualizarProducto));
        request.setData(entidadProducto);

        //----------------

        callActualizar = ilemas.saveItem(request);
        callActualizar.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if (response.isSuccessful()){
                    ResponseEntity respuesta = response.body();
                    Toast.makeText(UpdateProductoActivity.this, respuesta.getMensaje(), Toast.LENGTH_LONG).show();
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