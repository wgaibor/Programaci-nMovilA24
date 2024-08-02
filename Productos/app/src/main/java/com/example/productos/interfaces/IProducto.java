package com.example.productos.interfaces;

import com.example.productos.entity.ProductoRequestEntity;
import com.example.productos.entity.ResponseEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IProducto {

    @POST("lemas.php")
    Call<ResponseEntity> requestItem(@Body ProductoRequestEntity peticion);
}
