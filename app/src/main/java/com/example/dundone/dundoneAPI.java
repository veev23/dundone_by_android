package com.example.dundone;

import com.example.dundone.data.character.ResCharSearch;
import com.example.dundone.data.character.ResCharStatus;

import retrofit2.Call;
import retrofit2.http.GET;

public interface dundoneAPI {
    @GET("/timeline")
    Call<ResCharStatus> getCharStatus();
    @GET("")
    Call<ResCharSearch> getCharSearchRes();
}
