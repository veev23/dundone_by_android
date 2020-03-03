package com.example.dundone;

import com.example.dundone.data.character.ResCharSearch;
import com.example.dundone.data.character.ResCharStatus;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface dundoneAPI {
    @GET("/timeline/")
    Call<ResCharStatus> getCharStatus(@Query("characterId")String charId);
    @GET("/character")
    Call<ResCharSearch> getCharSearchRes(@Query("serverId")String serverId, @Query("characterName")String charName);
}
