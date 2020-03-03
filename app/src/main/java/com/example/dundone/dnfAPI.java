package com.example.dundone;

import com.example.dundone.data.item.ResItemList;
import com.example.dundone.data.character.ResCharSearch;
import com.example.dundone.data.server.ResServerList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface dnfAPI {
    //1번 : 서버 목록 불러오기
    @GET("/df/servers")
    Call<ResServerList> getServerList(@Query("apikey") String apikey);


    //17번 : 아이템 검색
    @GET("/df/items?q=trade:true&wordType=front&limit=30")
    Call<ResItemList> getItemList(@Query("apikey") String apikey, @Query("itemName") String itemName);

}
