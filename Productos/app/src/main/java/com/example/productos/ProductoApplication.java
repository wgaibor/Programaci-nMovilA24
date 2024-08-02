package com.example.productos;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductoApplication extends Application {

    private Retrofit restAdapter;
    private static ProductoApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        restAdapter = retrofitProducto();
    }

    private Retrofit retrofitProducto() {
        Retrofit cliente = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return cliente;
    }

    public Retrofit getRestAdapter() {
        return restAdapter;
    }

    public static ProductoApplication getmInstance() {
        return mInstance;
    }
}
