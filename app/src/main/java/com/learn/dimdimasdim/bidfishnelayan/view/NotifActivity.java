package com.learn.dimdimasdim.bidfishnelayan.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.learn.dimdimasdim.bidfishnelayan.R;
import com.learn.dimdimasdim.bidfishnelayan.adapter.NotifAdapter;
import com.learn.dimdimasdim.bidfishnelayan.data.model.Pricey;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotifActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_notif)
    RecyclerView rvNotif;

    public static String[][] data =  new String[][] {
            {"Aqis"},
            {"Lianda"},
            {"Velea"}
    };
    private ArrayList<Pricey> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);
        ButterKnife.bind(this);

        setterAdapter();
    }

    void setterAdapter(){

        rvNotif.setHasFixedSize(true);

        list = new ArrayList<>();
        list.addAll(getBidderData());

        NotifAdapter adapter = new NotifAdapter(this.getApplicationContext());
        adapter.setNotifArrayList(list);
        rvNotif.setAdapter(adapter);
        rvNotif.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));

    }

    public static ArrayList<Pricey> getBidderData() {
        Pricey bidPrice = null;
        ArrayList<Pricey> bidPriceList = new ArrayList<>();

        for (int i=0; i<data.length; i++){
            bidPrice = new Pricey();

            bidPrice.setBidderName(data[i][0]);

            bidPriceList.add(bidPrice);
        }

        return bidPriceList;
    }
}
