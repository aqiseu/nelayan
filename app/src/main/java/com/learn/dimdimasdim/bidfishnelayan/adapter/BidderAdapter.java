package com.learn.dimdimasdim.bidfishnelayan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.learn.dimdimasdim.bidfishnelayan.R;
import com.learn.dimdimasdim.bidfishnelayan.data.model.BidFish;
import com.learn.dimdimasdim.bidfishnelayan.data.model.Pricey;
import com.learn.dimdimasdim.bidfishnelayan.view.DetailAuctionActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amaliaqis on 24/02/2018.
 */

public class BidderAdapter extends RecyclerView.Adapter<BidderAdapter.BidderViewHolder> {

   private Context context;
  //  private FirebaseFirestore firebaseFirestore;
    private ArrayList<Pricey> priceyArrayList;

    public BidderAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Pricey> getPriceArrayList(){
        return priceyArrayList;
    }



    public void setPriceArrayList (ArrayList<Pricey> listPriceyFish){
        this.priceyArrayList = listPriceyFish;
    }

    @Override
    public BidderAdapter.BidderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prices, parent, false);
        return new BidderAdapter.BidderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BidderAdapter.BidderViewHolder holder, int position) {
        final int itemPosition = position;
        final Pricey pricey = getPriceArrayList().get(itemPosition);

        holder.tvBidName.setText(pricey.getBidderName());
        holder.tvBidPrice.setText(pricey.getBidderPrice());


    }

    @Override
    public int getItemCount() {
        return getPriceArrayList().size();
    }

    public class BidderViewHolder extends RecyclerView.ViewHolder {


        TextView tvBidName, tvBidPrice;
        CardView cvContainerBidder;

        public BidderViewHolder(View itemView) {
            super(itemView);

            tvBidName = itemView.findViewById(R.id.tv_name_pricelist);
            tvBidPrice = itemView.findViewById(R.id.tv_bid_pricelist);

            cvContainerBidder = itemView.findViewById(R.id.cv_container_item_prices);
        }
    }

}