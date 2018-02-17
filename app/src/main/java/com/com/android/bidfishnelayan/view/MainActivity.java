package com.com.android.bidfishnelayan.view;

import com.com.android.bidfishnelayan.R;
import com.com.android.bidfishnelayan.adapter.BidFishAdapter;
import com.com.android.bidfishnelayan.data.model.BidFish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BidFishAdapter.OnItemBidClickListener, View.OnClickListener {

    private RecyclerView rvItemBid;

    private ArrayList<BidFish> bidFishes;

    private BidFishAdapter bidFishAdapter;

    private Button btnAddBidFish;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvItemBid = findViewById(R.id.rv_lelang_ikan);
        btnAddBidFish = findViewById(R.id.btn_add_bid_fish);
        btnAddBidFish.setOnClickListener(this);
        setupItemBidDummy();
        bidFishAdapter = new BidFishAdapter(bidFishes,MainActivity.this,this);
        rvItemBid.setLayoutManager(new LinearLayoutManager(this));
        rvItemBid.setAdapter(bidFishAdapter);

    }

    private void setupItemBidDummy() {
        bidFishes = new ArrayList<>();
        bidFishes.add(new BidFish("Tuna Merah", "Ancol", "R. 350.000"));
        bidFishes.add(new BidFish("Tuna Biru", "PIK", "R. 250.000"));
        bidFishes.add(new BidFish("Tuna Hijau", "Sunter", "R. 150.000"));
        bidFishes.add(new BidFish("Tuna Kuning", "Pondok Ranjeg", "R. 10.000"));
    }

    @Override
    public void onContainerClick(BidFish bidFish) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_bid_fish:
                AddBidActivity.start(this);
                break;
        }
    }
}