package com.com.android.bidfishnelayan.adapter;

import com.com.android.bidfishnelayan.R;
import com.com.android.bidfishnelayan.data.model.BidFish;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by dimdimasdim on 16/02/2018.
 */

public class BidFishAdapter extends RecyclerView.Adapter<BidFishAdapter.BidFishViewHolder> {

    private ArrayList<BidFish> bidFishes;

    private Context context;

    private OnItemBidClickListener onItemClickListener;

    public BidFishAdapter(
        ArrayList<BidFish> bidFishes, Context context,
        OnItemBidClickListener onItemClickListener) {
        this.bidFishes = bidFishes;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemBidClickListener{
        void onContainerClick(BidFish bidFish);
    }
    
    public OnItemBidClickListener getOnItemBidClickListener(){
        return onItemClickListener;
    }

    @Override
    public BidFishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_bid, parent, false);
        return new BidFishViewHolder(itemView, getOnItemBidClickListener());
    }

    @Override
    public void onBindViewHolder(BidFishViewHolder holder, int position) {
        BidFish bidFish = bidFishes.get(position);
        holder.nameBid.setText(bidFish.getBidFishName());
        holder.locationBid.setText(bidFish.getLocation());
        holder.priceBid.setText(bidFish.getPriceBid());
    }

    @Override
    public int getItemCount() {
        return bidFishes.size();
    }

    public class BidFishViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nameBid, priceBid, locationBid;

        private RelativeLayout containerItemBid;

        private OnItemBidClickListener listener;

        public BidFishViewHolder(View itemView, OnItemBidClickListener listener) {
            super(itemView);

            this.listener = listener;

            nameBid = itemView.findViewById(R.id.tv_name_bid);

            priceBid = itemView.findViewById(R.id.tv_price);

            locationBid = itemView.findViewById(R.id.tv_location_bid);

            containerItemBid = itemView.findViewById(R.id.rl_container_item_bid);

            containerItemBid.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (listener != null && view.getId() == R.id.rl_container_item_bid){
                Toast.makeText(context, "ke klik aku", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
