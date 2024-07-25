package com.example.productolemas.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.productolemas.LemasApplication;
import com.example.productolemas.R;
import com.example.productolemas.adapter.ProductoAdapter;
import com.example.productolemas.entity.ProductoEntity;
import com.example.productolemas.entity.ProductoRequestEntity;
import com.example.productolemas.entity.ProductoResponseEntity;
import com.example.productolemas.interfaces.Ilemas;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarProductoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView rvListado;
    Call<ProductoResponseEntity> productoCall;
    ProductoAdapter productoAdapter;
    SwipeRefreshLayout refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_producto);
        rvListado = findViewById(R.id.rv_listadoProducto);
        refresh = findViewById(R.id.contenedor);
        refresh.setOnRefreshListener(this);
        consumirWSObtenerProducto();
    }

    private void consumirWSObtenerProducto() {
        Ilemas ilemas = LemasApplication.getmInstance().getRestAdapter().create(Ilemas.class);
        ProductoRequestEntity request = new ProductoRequestEntity();
        request.setOp(getString(R.string.op_obtenerProducto));

        productoCall = ilemas.listItem(request);
        productoCall.enqueue(new Callback<ProductoResponseEntity>() {
            @Override
            public void onResponse(Call<ProductoResponseEntity> call, Response<ProductoResponseEntity> response) {
                if (response.isSuccessful()){
                    ProductoResponseEntity productos = response.body();
                    llenarRecycler(productos.getData());
                }
            }

            @Override
            public void onFailure(Call<ProductoResponseEntity> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void llenarRecycler(List<ProductoEntity> data) {
        rvListado.setLayoutManager(new LinearLayoutManager(this));
        productoAdapter = new ProductoAdapter(this, data);
        rvListado.setHasFixedSize(true);
        rvListado.setAdapter(productoAdapter);
    }

    @Override
    public void onRefresh() {
        consumirWSObtenerProducto();
        refresh.setRefreshing(false);
    }
}