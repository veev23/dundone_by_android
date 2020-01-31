package com.example.dundone.character;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.dundone.R;
import com.example.dundone.main.MainActivity;

public class CharacterSearchResultActivity extends AppCompatActivity {

    private Context context;
    private FrameLayout flBackButton;
    private FrameLayout flHomeButton;
    private void init(){
        context = this;
        flBackButton = findViewById(R.id.back_in_character_search_result);
        flHomeButton = findViewById(R.id.home_in_character_search_result);
    }
    private void setOnListeners(){
        flBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        flHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainActivity();
            }
        });
    }
    private void toMainActivity(){
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_search_result);
        init();
        setOnListeners();
    }
}
