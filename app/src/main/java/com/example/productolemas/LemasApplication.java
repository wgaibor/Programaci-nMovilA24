package com.example.productolemas;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LemasApplication extends Application {

    private Retrofit restAdapter;
    private static LemasApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        restAdapter = retrofitLemas();
    }

    private Retrofit retrofitLemas() {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(getString(R.string.url_base))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return client;
    }

    public Retrofit getRestAdapter() {
        return restAdapter;
    }

    public static LemasApplication getmInstance() {
        return mInstance;
    }
}
