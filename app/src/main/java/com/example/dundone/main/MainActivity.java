package com.example.dundone.main;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dundone.auction.AuctionSearchActivity;
import com.example.dundone.character.CharacterListActivity;
import com.example.dundone.R;

public class MainActivity extends AppCompatActivity {

    private Button btnChar;
    private Button btnAuction;
    private void init(){
        btnChar = findViewById(R.id.select_char);
        btnAuction = findViewById(R.id.select_auction);
    }
    private void toAuctionSearchActivity(){
        Intent intent = new Intent(this, AuctionSearchActivity.class);
        startActivity(intent);
    }
    private void toCharacterListActivity(){
        Intent intent = new Intent(this, CharacterListActivity.class);
        startActivity(intent);
    }
    private void setOnListeners(){
        btnChar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCharacterListActivity();
            }
        });
        btnAuction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAuctionSearchActivity();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setOnListeners();
    }
}
