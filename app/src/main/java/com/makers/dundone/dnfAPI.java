package com.makers.dundone;

import com.makers.dundone.data.character.ResCharBaseDataFromDNF;
import com.makers.dundone.data.item.ResGetAuctionItems;
import com.makers.dundone.data.item.ResGetItemIds;
import com.makers.dundone.data.server.ResServerList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface dnfAPI {
    //1번 : 서버 목록 불러오기
    @GET("/df/servers")
    Call<ResServerList> getServerList(@Query("apikey") String apikey);
    //3번 : 캐릭터 기본 정보 불러오기
    @GET("/df/servers/{server}/characters/{charId}")
    Call<ResCharBaseDataFromDNF> getCharBaseData(@Path("server") String serverId, @Path("charId") String charId,@Query("apikey") String apikey);

    //17번 : 아이템 검색
    @GET("/df/items?wordType=front&limit=30&q=trade:false")
    Call<ResGetItemIds> getAuctionItemIds(@Query("itemName")String itemName,@Query("apikey")String apiKey);

    //15번 : 경매장 아이템 검색
    @GET("df/auction")
    Call<ResGetAuctionItems> getAuctionItemList(@Query("itemId")String itemId, @Query("apikey")String apikey);
}
