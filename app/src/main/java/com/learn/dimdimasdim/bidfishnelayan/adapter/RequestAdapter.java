package com.learn.dimdimasdim.bidfishnelayan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.learn.dimdimasdim.bidfishnelayan.R;
import com.learn.dimdimasdim.bidfishnelayan.data.model.BidFish;
import com.learn.dimdimasdim.bidfishnelayan.data.model.Pricey;
import com.learn.dimdimasdim.bidfishnelayan.view.DetailAuctionActivity;
import com.learn.dimdimasdim.bidfishnelayan.view.ProfileActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by amaliaqis on 02/03/2018.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private Context context;
    private ClickListener listener;

    //  private FirebaseFirestore firebaseFirestore;
    private ArrayList<Pricey> reqArrayList;

    public RequestAdapter(Context context) {
        this.context = context;
    }

    public RequestAdapter(ArrayList<Pricey> listReqFish, ClickListener listener){
        this.reqArrayList = listReqFish;
        this.listener = listener;
    }

    public ArrayList<Pricey> getReqArrayList() {
        return reqArrayList;
    }


    public void setReqArrayList(ArrayList<Pricey> listReqFish) {
        this.reqArrayList = listReqFish;
    }

    @Override
    public RequestAdapter.RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_requests, parent, false);
        return new RequestAdapter.RequestViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RequestAdapter.RequestViewHolder holder, int position) {
        final int itemPosition = position;
        final Pricey pricey = getReqArrayList().get(itemPosition);

        holder.tvReqName.setText(pricey.getBidderName());
//        holder.btnDetailReq.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ProfileActivity.class);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return getReqArrayList().size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvReqName;
        CardView cvContainerReq;
        Button btnDetailReq;
        WeakReference<ClickListener> listenerRef;


        public RequestViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            context = itemView.getContext();


            listenerRef = new WeakReference<>(RequestAdapter.this.listener);
            tvReqName = itemView.findViewById(R.id.tv_name_request);
            btnDetailReq = itemView.findViewById(R.id.btn_detail_req);
            cvContainerReq = itemView.findViewById(R.id.cv_container_item_req);

            itemView.setOnClickListener(this);
            btnDetailReq.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (v.getId() == btnDetailReq.getId()) {
                Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                v.getContext().startActivity(intent);
            }

            listenerRef.get().onPositionClicked(getAdapterPosition());
        }

    }

    public interface ClickListener {

        void onPositionClicked(int position);

    }

}