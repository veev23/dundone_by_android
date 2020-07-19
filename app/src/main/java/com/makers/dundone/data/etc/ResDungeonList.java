package com.makers.dundone.data.etc;

import com.makers.dundone.data.BaseDundoneResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResDungeonList extends BaseDundoneResponse {
    public class DungeonEpic{
        @SerializedName("dungeonName")
        private String dungeonName;
        @SerializedName("cnt")
        private int cnt;

        public String getDungeonName() {
            return dungeonName;
        }

        public int getCnt() {
            return cnt;
        }

        public DungeonEpic(String dungeonName, int cnt) {
            this.dungeonName = dungeonName;
            this.cnt = cnt;
        }
    }
    @SerializedName("result")
    private ArrayList<DungeonEpic> dungeonEpicList;

    public ArrayList<DungeonEpic> getDungeonEpicList() {
        return dungeonEpicList;
    }

    public ResDungeonList(boolean isSuccess, int code, String message, ArrayList<DungeonEpic> dungeonEpicList) {
        super(isSuccess, code, message);
        this.dungeonEpicList = dungeonEpicList;
    }
}
