package com.learn.dimdimasdim.bidfishnelayan.view;

import com.learn.dimdimasdim.bidfishnelayan.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AddBidActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, AddBidActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bid);
    }
}
