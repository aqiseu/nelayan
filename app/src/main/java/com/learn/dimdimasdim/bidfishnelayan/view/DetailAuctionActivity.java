package com.learn.dimdimasdim.bidfishnelayan.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.learn.dimdimasdim.bidfishnelayan.R;
import com.learn.dimdimasdim.bidfishnelayan.adapter.BidderAdapter;
import com.learn.dimdimasdim.bidfishnelayan.data.model.Pricey;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailAuctionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_detail_auction)
    Toolbar toolbarDetailAuction;

    public static void start(Context context) {
        Intent starter = new Intent(context, DetailAuctionActivity.class);
        context.startActivity(starter);
    }

    private ImageView photoBid;
    private TextView tvType, tvLocation, tvTimeCatch, tvPrice, tvTimeBid;

    private FirebaseFirestore firebaseFirestore;

    public static String[][] data =  new String[][] {
            {"Rp 35000", "Aqis"},
            {"Rp 30000", "Lianda"},
            {"Rp 40000", "Velea"}
    };
    private ArrayList<Pricey> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_auction);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarDetailAuction);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setterAdapter();

        firebaseFirestore = FirebaseFirestore.getInstance();

        photoBid = findViewById(R.id.img_detail_fish);
        tvType = findViewById(R.id.tv_desc_auct);
        tvLocation = findViewById(R.id.tv_detail_location_fish);
        tvTimeCatch = findViewById(R.id.tv_detail_time_catching);
        tvPrice = findViewById(R.id.tv_detail_price_fish);
        tvTimeBid = findViewById(R.id.tv_detail_time_to_bid);

        Bundle bundle = getIntent().getExtras();
        Glide.with(getApplicationContext()).load(bundle.getString("photo")).into(photoBid);
        tvType.setText(bundle.getString("type"));
        tvLocation.setText(bundle.getString("location"));
        tvTimeCatch.setText(bundle.getString("timeCatch"));
        tvPrice.setText(bundle.getString("price"));
        tvTimeBid.setText(bundle.getString("timeBid"));

    }

    void setterAdapter(){

        RecyclerView rvBidderList = (RecyclerView) findViewById(R.id.rv_bidder_list);
        rvBidderList.setHasFixedSize(true);

        list = new ArrayList<>();
        list.addAll(getBidderData());

        BidderAdapter adapter = new BidderAdapter(this.getApplicationContext());
        adapter.setPriceArrayList(list);
        rvBidderList.setAdapter(adapter);
        rvBidderList.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));

    }

    public static ArrayList<Pricey> getBidderData() {
        Pricey bidPrice = null;
        ArrayList<Pricey> bidPriceList = new ArrayList<>();

        for (int i=0; i<data.length; i++){
            bidPrice = new Pricey();

            bidPrice.setBidderPrice(data[i][0]);
            bidPrice.setBidderName(data[i][1]);

            bidPriceList.add(bidPrice);
        }

        return bidPriceList;
    }
}
