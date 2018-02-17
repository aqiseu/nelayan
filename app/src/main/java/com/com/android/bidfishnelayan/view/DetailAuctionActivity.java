package com.com.android.bidfishnelayan.view;

import com.com.android.bidfishnelayan.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailAuctionActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, DetailAuctionActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_auction);
    }
}
