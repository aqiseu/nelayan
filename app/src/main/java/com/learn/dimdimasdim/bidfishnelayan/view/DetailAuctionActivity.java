package com.learn.dimdimasdim.bidfishnelayan.view;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.learn.dimdimasdim.bidfishnelayan.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailAuctionActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, DetailAuctionActivity.class);
        context.startActivity(starter);
    }

    private ImageView photoBid;
    private TextView tvType, tvLocation, tvTimeCatch, tvPrice, tvTimeBid;

    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_auction);

        firebaseFirestore = FirebaseFirestore.getInstance();

        photoBid = findViewById(R.id.img_detail_fish);
        tvType = findViewById(R.id.tv_detail_type_fish);
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
}
