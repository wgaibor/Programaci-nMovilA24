package com.example.productolemas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.productolemas.entity.ProductoRequestEntity;
import com.example.productolemas.entity.ProductoEntity;
import com.example.productolemas.entity.ResponseEntity;
import com.example.productolemas.interfaces.Ilemas;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtMarca;
    EditText edtModelo;
    EditText edtDescripcion;
    EditText edtPrecio;
    EditText edtImagen;
    Button btnGuardar;

    Call<ResponseEntity> entityCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtMarca = findViewById(R.id.etMarca);
        edtModelo = findViewById(R.id.etModelo);
        edtDescripcion = findViewById(R.id.etDescripcion);
        edtPrecio = findViewById(R.id.etPrecio);
        edtImagen = findViewById(R.id.etImagen);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnGuardar){
            consumirWS();
        }
    }

    private void consumirWS() {
        Ilemas ilemas = LemasApplication.getmInstance().getRestAdapter().create(Ilemas.class);

        ProductoEntity item = new ProductoEntity();
        item.setMarca(edtMarca.getText().toString());
        item.setModelo(edtModelo.getText().toString());
        item.setDescripcion(edtDescripcion.getText().toString());
        item.setPrecio(edtPrecio.getText().toString());
        item.setImagen(edtImagen.getText().toString());
        item.setEstado(getString(R.string.estado_activo));

        ProductoRequestEntity producto = new ProductoRequestEntity();
        producto.setOp(getString(R.string.op_guardarProducto));
        producto.setData(item);

        //
        entityCall = ilemas.saveItem(producto);
        entityCall.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if(response.isSuccessful()){
                    ResponseEntity respuesta = response.body();
                    Toast.makeText(MainActivity.this, respuesta.getMensaje(), Toast.LENGTH_LONG).show();
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