package com.example.dundone.auction;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dundone.R;

public class AuctionSearchActivity extends AppCompatActivity {

    private Context context;

    private FrameLayout flBackButton;
    private Button btnItemSearch;
    private EditText etItemName;

    private void init(){
        context = this;

        flBackButton = findViewById(R.id.back_in_auction_search);
        btnItemSearch = findViewById(R.id.item_search_btn);
        etItemName = findViewById(R.id.item_name_et);
    }
    private void setOnListeners(){
        flBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //검색버튼 눌렀을 때 검색
        btnItemSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    toItemSearchResultActivity();
            }
        });
        //완료버튼 눌렀을 때 검색
        etItemName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    toItemSearchResultActivity();
                }
                return false;
            }
        });
    }
    private void toItemSearchResultActivity() {
        String itemName = etItemName.getText().toString();
        if (itemName.equals("")) {
            Toast.makeText(context, "검색어를 작성해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(context, ItemSearchResultActivity.class);
            intent.putExtra("itemName", itemName);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_search);
        init();
        setOnListeners();
    }
}
