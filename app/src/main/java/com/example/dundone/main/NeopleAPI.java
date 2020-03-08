package com.example.dundone.main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class NeopleAPI {
    private Context mContext;
    public NeopleAPI(Context context) {
        this.mContext = context;
    }
    public void toNeopleDeveloperSite(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("네오플 개발자 페이지로 가시겠습니까?")
                .setPositiveButton("네!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String neopleUrl = "https://developers.neople.co.kr";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(neopleUrl));
                        mContext.startActivity(intent);
                    }
                })
                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}
