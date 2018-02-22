package com.learn.dimdimasdim.bidfishnelayan.adapter;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.learn.dimdimasdim.bidfishnelayan.R;
import com.learn.dimdimasdim.bidfishnelayan.data.model.BidFish;
import com.learn.dimdimasdim.bidfishnelayan.view.DetailAuctionActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dimdimasdim on 16/02/2018.
 */

public class BidFishAdapter extends RecyclerView.Adapter<BidFishAdapter.BidFishViewHolder> {

    private List<BidFish> bidFishList;
    private Context context;
    private FirebaseFirestore firebaseFirestore;

    public BidFishAdapter (List<BidFish> bidFishList, Context context, FirebaseFirestore firebaseFirestore){
        this.bidFishList = bidFishList;
        this.context = context;
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public BidFishAdapter.BidFishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bid, parent, false);
        return new BidFishAdapter.BidFishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BidFishAdapter.BidFishViewHolder holder, int position) {
        final int itemPosition = position;
        final BidFish bidFish = bidFishList.get(itemPosition);

        holder.tvName.setText(bidFish.getBidFishName());
        holder.tvPrice.setText(bidFish.getPriceBid());
        holder.tvLocation.setText(bidFish.getLocation());
        Glide.with(context).load(bidFish.getUrlImgBid()).into(holder.ivPhoto);

        holder.cvContainerBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDetailAuction(bidFish);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bidFishList.size();
    }

    public class BidFishViewHolder extends RecyclerView.ViewHolder{

        ImageView ivPhoto;
        TextView tvName, tvPrice, tvLocation;
        CardView cvContainerBid;

        public BidFishViewHolder(View itemView){
            super(itemView);

            ivPhoto = itemView.findViewById(R.id.img_bid_fish);
            tvName = itemView.findViewById(R.id.tv_name_bid);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvLocation = itemView.findViewById(R.id.tv_location_bid);
            cvContainerBid = itemView.findViewById(R.id.cv_container_item_bid);
        }
    }

    private void viewDetailAuction (BidFish bidFish){
        Intent intent = new Intent(context, DetailAuctionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("photo", bidFish.getUrlImgBid());
        intent.putExtra("type", bidFish.getBidFishName());
        intent.putExtra("timeCatch", bidFish.getTimeCatchingFish());
        intent.putExtra("location", bidFish.getLocation());
        intent.putExtra("price", bidFish.getPriceBid());
        intent.putExtra("timeBid", bidFish.getTimeBid());
        context.startActivity(intent);
    }
}
