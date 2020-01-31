package com.example.dundone;

import com.example.dundone.data.item.ResItemList;
import com.example.dundone.data.character.ResCharSearch;
import com.example.dundone.data.server.ResServerList;
import com.example.dundone.data.timeline.ResTimeline;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface dnfAPI {
    //1번 : 서버 목록 불러오기
    @GET("/df/servers")
    Call<ResServerList> getServerList(@Query("apikey") String apikey);

    //2번 : 캐릭터 검색
    @GET("/df/servers/{serverId}/characters?wordType=front&limit=30")
    Call<ResCharSearch> getCharSearch(@Query("apikey") String apikey, @Path("serverId") String serverId,
                                      @Query("characterName") String characterName);

    //3번 : 캐릭터 기본 정보 조회
    @GET("/df/servers/{serverId}/characters/{characterId}")
    Call<ResCharSearch> getCharBaseData(@Query("apikey") String apikey, @Path("serverId") String serverId,
                                      @Path("characterId") String characterId);


    /*
    4번 : 캐릭터 타임라인 조회
    next : 최초로 부를 때 "", 다음 레코드를 부르려면 ResTimeline.timelineResult.next의 값을 넣어줌
    timelineCode : https://developers.neople.co.kr/contents/guide/pages/all#던파-타임라인-코드
    */
    @GET("https://api.neople.co.kr/df/servers/{serverId}/characters/{characterId}/timelineResult?limit=100")
    Call<ResTimeline> getTimeline(@Query("apikey") String apikey, @Path("serverId") String serverId, @Path("characterId") String characterId,
                                  @Query("startDate") String startDate, @Query("endDate") String endDate,
                                  @Query("code") String timelineCode, @Query("next") String next);

    //17번 : 아이템 검색
    @GET("/df/items?q=trade:true&wordType=front&limit=30")
    Call<ResItemList> getItemList(@Query("apikey") String apikey, @Query("itemName") String itemName);

}
