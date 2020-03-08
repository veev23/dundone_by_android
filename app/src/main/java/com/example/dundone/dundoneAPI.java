package com.example.dundone;

import com.example.dundone.data.character.ResCharSearch;
import com.example.dundone.data.character.ResCharStatus;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface dundoneAPI {
    //0번 : 캐릭터의 레이드 상태 등을 리턴
    @GET("/timeline")
    Call<ResCharStatus> getCharStatus(@Query("serverId")String serverId, @Query("characterId")String charId);
    //3번 : 서버와 캐릭터 이름으로 검색한 결과를 리턴
    @GET("/character")
    Call<ResCharSearch> getCharSearchRes(@Query("serverId")String serverId, @Query("characterName")String charName);
}
