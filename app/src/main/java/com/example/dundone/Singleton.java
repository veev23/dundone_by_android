package com.example.dundone;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singleton extends Application {
    private static volatile Singleton instance = null;

    private static class SingletonHolder {
        public static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getSingletonInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static Retrofit retrofit;
    public static dnfAPI dnfService;

    @Override
    public void onCreate(){
        super.onCreate();
        instance= getSingletonInstance();

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.neople_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dnfService = retrofit.create(dnfAPI.class);
    }

}
