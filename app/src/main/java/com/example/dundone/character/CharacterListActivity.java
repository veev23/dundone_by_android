package com.example.dundone.character;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.dundone.R;
import com.example.dundone.Singleton;
import com.example.dundone.character.adapter.CharacterListAdapter;
import com.example.dundone.common_class.CustomRecyclerDecoration;
import com.example.dundone.data.character.CharacterData;
import com.example.dundone.data.timeline.ResTimeline;
import com.example.dundone.data.timeline.Timeline;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CharacterListActivity extends AppCompatActivity {

    private SharedPreferences spfChar;
    private Gson gson;

    private ArrayList<CharacterData> characterDataList;
    private RecyclerView rvCharListView;
    private CharacterListAdapter characterListAdapter;

    private FloatingActionButton fabAddButton;
    private FrameLayout flBackButton;

    private interface CharacterRecord{
        void modifyByTimeline(int i, ArrayList<Timeline> timelineList);
    }
    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddThhmm");
    private String nowTime;
    private void init(){
        context = this;
        spfChar = context.getSharedPreferences(getString(R.string.spfCharData), Context.MODE_PRIVATE);
        gson = new GsonBuilder().create();

        rvCharListView = findViewById(R.id.char_list_in_char_list);
        fabAddButton = findViewById(R.id.char_add_button_in_char_list);
        flBackButton = findViewById(R.id.back_in_character_list);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
        calendar.add(Calendar.MINUTE, -1);
        nowTime = dateFormat.format(calendar.getTime());
    }
    private void getLocalData(){
        String jsonCharDataList = spfChar.getString(getString(R.string.spfCharData), "{}");
        characterDataList = gson.fromJson(jsonCharDataList, new TypeToken<ArrayList<CharacterData>>() {
        }.getType());
    }
    private void bindRecyclerView(){
        rvCharListView.setLayoutManager(new LinearLayoutManager(context));
        rvCharListView.setHasFixedSize(true);
        rvCharListView.addItemDecoration(new CustomRecyclerDecoration(10));
        characterListAdapter = new CharacterListAdapter(characterDataList, context);
        rvCharListView.setAdapter(characterListAdapter);
    }
    private void setOnListeners(){
        flBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fabAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCharacterSearchActivity();
            }
        });
    }
    private void toCharacterSearchActivity(){
        Intent intent = new Intent(context, CharacterSearchActivity.class);
        startActivity(intent);
    }
    //i번째 캐릭터의 타임라인을 조회
    private void reqGetTimeline(int i, String startTime, String timelineCode, String next, CharacterRecord record){
        final CharacterData charData = characterDataList.get(i);
        Call<ResTimeline> timelineCall =
                Singleton.dnfService.getTimeline(getString(R.string.apikey), charData.getServer().getServerId(),
                        charData.getCharId(), startTime, nowTime, timelineCode, next);
        timelineCall.enqueue(new Callback<ResTimeline>() {
            @Override
            public void onResponse(Call<ResTimeline> call, Response<ResTimeline> response) {
                if(response.isSuccessful()) {
                        ArrayList<Timeline> timelineList = response.body().timelineResult.timelineList;
                        if(!timelineList.isEmpty()){
                            record.modifyByTimeline(i, timelineList);
                            reqGetTimeline(i, startTime, timelineCode, next, record);
                        }
                }
                else{
                    Toast.makeText(context, "code : " +response.code() + "\nmessage : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResTimeline> call, Throwable t) {
                Toast.makeText(context, "error : " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        init();
        getLocalData();
        bindRecyclerView();
        setOnListeners();
    }
}
