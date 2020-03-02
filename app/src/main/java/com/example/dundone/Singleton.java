package com.example.dundone;

import android.app.Application;
import android.content.Context;
import android.util.TypedValue;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singleton extends Application {
    private static volatile Singleton instance = null;
    private static class SingletonHolder {
        public static final Singleton INSTANCE = new Singleton();
    }

    public static int DpToPixel(Context context, float DP) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DP, context.getResources()
                .getDisplayMetrics());
        return (int) px;
    }

    public static Singleton getSingletonInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static Retrofit retrofit;
    public static dnfAPI dnfService;
    public static dundoneAPI dundoneService;

    @Override
    public void onCreate(){
        super.onCreate();
        instance= getSingletonInstance();

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.neople_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dnfService = retrofit.create(dnfAPI.class);
        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.dundone_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dundoneService = retrofit.create(dundoneAPI.class);

    }

}
