package com.example.dundone;

import com.example.dundone.data.BaseDundoneResponse;
import com.example.dundone.data.character.ResCharSearch;
import com.example.dundone.data.character.ResCharStatus;
import com.example.dundone.data.character.ResRaidClearCounts;
import com.example.dundone.data.etc.ResDungeonList;
import com.example.dundone.data.etc.ResUpgradeCounts;
import com.example.dundone.data.etc.ResRecommendHellCh;
import com.example.dundone.data.item.ResGetCharEpicList;
import com.example.dundone.data.item.ResGetEpicDropList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Query;

public interface dundoneAPI {
    //0번 : 캐릭터의 레이드 상태 등을 리턴
    @GET("/timeline")
    Call<ResCharStatus> getCharStatus(@Query("serverId")String serverId, @Query("characterId")String charId);

    //1번 : 캐릭터 업데이트
    @PATCH("/updata")
    Call<BaseDundoneResponse> Update(@Query("serverId")String serverId, @Query("characterId")String charId);

    //2번 : 획득 에픽 리스트 반환
    @GET("/epics")
    Call<ResGetCharEpicList> getEpicList(@Query("serverId")String serverId, @Query("characterId")String charId);

    //9번 : 헬 채널 추천
    @GET("/hellch")
    Call<ResRecommendHellCh> getRecommendChannel(@Query("type")String type);

    //3번 : 서버와 캐릭터 이름으로 검색한 결과를 리턴
    @GET("/character")
    Call<ResCharSearch> getCharSearchRes(@Query("serverId")String serverId, @Query("characterName")String charName);

    @GET("/raid")
    Call<ResRaidClearCounts> getRaidClearCounts(@Query("serverId")String serverId, @Query("characterId") String charId);

    //캐릭터 강화 통계
    @GET("/reinforce/reinforcement")
    Call<ResUpgradeCounts> getUpgradeCountsReinforce(@Query("serverId")String serverId, @Query("characterId") String charId, @Query("min")int min, @Query("max")int max);

    //캐릭터 강화 통계
    @GET("/reinforce/amplify")
    Call<ResUpgradeCounts> getUpgradeCountsAmplify(@Query("serverId")String serverId, @Query("characterId") String charId, @Query("min")int min, @Query("max")int max);

    //캐릭터 강화 통계
    @GET("/reinforce/refine")
    Call<ResUpgradeCounts> getUpgradeCountsRefine(@Query("serverId")String serverId, @Query("characterId") String charId, @Query("min")int min, @Query("max")int max);

    // 10번 : 던전별 에픽 드랍 수
    @GET("/dungeon?limit=100")
    Call<ResDungeonList> getDungeonEpics();

    // 11번 : 에픽 드롭 목록
    @GET("/itemacquire")
    Call<ResGetEpicDropList> getEpicDropList(@Query("page")int page,@Query("limit")int limit,@Query("dungeonName")String dungeonName, @Query("itemName")String itemName);

    // 12번 : 에픽 추천
    @GET("/hellch")
    Call<ResRecommendHellCh> getHellChannel(@Query("itemName")String itemName);

}
