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
import com.learn.dimdimasdim.bidfishnelayan.data.model.Pricey;
import com.learn.dimdimasdim.bidfishnelayan.view.ProfileActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by amaliaqis on 02/03/2018.
 */

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.NotifViewHolder> {

    private Context context;
    private ClickListener listener;
    //  private FirebaseFirestore firebaseFirestore;
    private ArrayList<Pricey> notifArrayList;

    public NotifAdapter(Context context) {
        this.context = context;
    }

    public NotifAdapter(ArrayList<Pricey> listNotifFish, ClickListener listener) {
        this.notifArrayList = listNotifFish;
        this.listener = listener;
    }

    public ArrayList<Pricey> getNotifArrayList() {
        return notifArrayList;
    }


    public void setNotifArrayList(ArrayList<Pricey> listNotifFish) {
        this.notifArrayList = listNotifFish;
    }

    @Override
    public NotifAdapter.NotifViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notif, parent, false);
        return new NotifAdapter.NotifViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(NotifAdapter.NotifViewHolder holder, int position) {
        final int itemPosition = position;
        final Pricey pricey = getNotifArrayList().get(itemPosition);

        holder.tvNotifName.setText(pricey.getBidderName());
//        holder.btnDetailNot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ProfileActivity.class);
//                context.startActivity(intent);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return getNotifArrayList().size();
    }

    public class NotifViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvNotifName;
        CardView cvContainerBidder;
        Button btnDetailNot;
        WeakReference<ClickListener> listenerRef;


        public NotifViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            context = itemView.getContext();


            listenerRef = new WeakReference<>(listener);
            tvNotifName = itemView.findViewById(R.id.tv_notif_name);
            btnDetailNot = itemView.findViewById(R.id.btn_detail_notif);
            cvContainerBidder = itemView.findViewById(R.id.cv_container_item_notif);

            itemView.setOnClickListener(this);
            btnDetailNot.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (v.getId() == btnDetailNot.getId()) {
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