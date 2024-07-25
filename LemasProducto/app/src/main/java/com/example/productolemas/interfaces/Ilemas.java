package com.example.productolemas.interfaces;

import com.example.productolemas.entity.ProductoRequestEntity;
import com.example.productolemas.entity.ProductoResponseEntity;
import com.example.productolemas.entity.ResponseEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Ilemas {

    @POST("lemas.php")
    Call<ResponseEntity> saveItem(@Body ProductoRequestEntity request);

    @POST("lemas.php")
    Call<ProductoResponseEntity> listItem(@Body ProductoRequestEntity request);
}
