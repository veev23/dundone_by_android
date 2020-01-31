package com.example.dundone.auction;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dundone.auction.adapter.ItemListAdapter;
import com.example.dundone.common_class.CustomRecyclerDecoration;
import com.example.dundone.data.item.ResItemList;
import com.example.dundone.R;
import com.example.dundone.Singleton;
import com.example.dundone.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemSearchResultActivity extends AppCompatActivity {

    private String itemName;
    private Context context;
    private ItemListAdapter itemListAdapter;
    private RecyclerView rcItemListView;
    private TextView tvSearchString;

    private FrameLayout flBackButton;
    private FrameLayout flHomeButton;
    private void recieveIntent(){
        Intent intent = getIntent();
        itemName = intent.getExtras().getString("itemName");
        tvSearchString.setText(itemName);
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
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void reqSearchItem(){
        Call<ResItemList> data = Singleton.dnfService.getItemList(getString(R.string.apikey), itemName);
        data.enqueue(new Callback<ResItemList>() {
            @Override
            public void onResponse(Call<ResItemList> call, Response<ResItemList> response) {
                if(response.isSuccessful()){
                    ResItemList res = response.body();
                    itemListAdapter = new ItemListAdapter(res.getRows(), context);
                    rcItemListView.setAdapter(itemListAdapter);
                }
                else{
                    Toast.makeText(context, "code : " +response.code() + "\nmessage : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResItemList> call, Throwable t) {
                Toast.makeText(context, "error : " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void init(){
        context = this;
        rcItemListView = findViewById(R.id.item_search_list_rcview);
        rcItemListView.setLayoutManager(new LinearLayoutManager(context));
        rcItemListView.setHasFixedSize(true);
        rcItemListView.addItemDecoration(new CustomRecyclerDecoration(10));
        tvSearchString = findViewById(R.id.item_name_in_search_result);

        flBackButton = findViewById(R.id.back_in_item_search_result);
        flHomeButton = findViewById(R.id.home_in_item_search_result);

        recieveIntent();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_search_result);

        init();
        reqSearchItem();
        setOnListeners();
    }
}
