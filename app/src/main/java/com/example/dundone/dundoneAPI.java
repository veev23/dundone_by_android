package com.example.dundone;

import com.example.dundone.data.character.ResCharSearch;
import com.example.dundone.data.character.ResCharStatus;
import com.example.dundone.data.etc.ResRecommendHellCh;
import com.example.dundone.data.item.ResGetEpicList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface dundoneAPI {
    //0번 : 캐릭터의 레이드 상태 등을 리턴
    @GET("/timeline")
    Call<ResCharStatus> getCharStatus(@Query("serverId")String serverId, @Query("characterId")String charId);

    //1번 : 획득 에픽 리스트 반환
    @GET("/epics")
    Call<ResGetEpicList> getEpicList(@Query("characterId")String charId);

    //2번 : 헬 채널 추천
    @GET("/hellch")
    Call<ResRecommendHellCh> getRecommendChannel(@Query("type")String type);

    //3번 : 서버와 캐릭터 이름으로 검색한 결과를 리턴
    @GET("/character")
    Call<ResCharSearch> getCharSearchRes(@Query("serverId")String serverId, @Query("characterName")String charName);

}
