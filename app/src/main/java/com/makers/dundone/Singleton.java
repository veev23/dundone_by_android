package com.makers.dundone;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    private static final Handler mHandler = new Handler();
    public static void runOnUiThread(Runnable runnable) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }
    public static Singleton getSingletonInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static Retrofit retrofit;
    public static dnfAPI dnfService;
    public static dundoneAPI dundoneService;


    public static Gson gson = new GsonBuilder().create();

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
