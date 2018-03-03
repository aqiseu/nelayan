package com.learn.dimdimasdim.bidfishnelayan.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.learn.dimdimasdim.bidfishnelayan.R;
import com.learn.dimdimasdim.bidfishnelayan.adapter.NotifAdapter;
import com.learn.dimdimasdim.bidfishnelayan.adapter.RequestAdapter;
import com.learn.dimdimasdim.bidfishnelayan.data.model.Pricey;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity {

    public static String[][] data =  new String[][] {
            {"Aqis"},
            {"Lianda"},
            {"Velea"}
    };
    private ArrayList<Pricey> list;
    RecyclerView rvReqList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        setterAdapter();
    }

    void setterAdapter(){

        rvReqList = (RecyclerView) findViewById(R.id.rv_request);
        rvReqList.setHasFixedSize(true);

        list = new ArrayList<>();
        list.addAll(getBidderData());

        RequestAdapter adapter = new RequestAdapter(this.getApplicationContext());
        adapter.setReqArrayList(list);
        rvReqList.setAdapter(adapter);
        rvReqList.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));

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


