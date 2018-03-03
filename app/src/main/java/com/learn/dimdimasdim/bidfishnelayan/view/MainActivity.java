package com.learn.dimdimasdim.bidfishnelayan.view;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.learn.dimdimasdim.bidfishnelayan.R;
import com.learn.dimdimasdim.bidfishnelayan.adapter.BidFishAdapter;
import com.learn.dimdimasdim.bidfishnelayan.data.model.Auctioneer;
import com.learn.dimdimasdim.bidfishnelayan.data.model.BidFish;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

public class MainActivity extends AppCompatActivity{

    private ImageView ivPhoto;
    private TextView tvName, tvEmail;
    private String id;
    private RecyclerView rvBid;

    private static final String name = "name";
    private static final String email = "email";
    private static final String photoUrl = "photoUrl";

    private FirebaseFirestore dbFirestore;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private ListenerRegistration firestoreListener;

    private BidFishAdapter rvAdapter;
    private Button btnAddBidFish;
    private Toolbar toolbar;

    MenuItem prevMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("HOME");

        btnAddBidFish = findViewById(R.id.btn_add_bid_fish);
        ivPhoto = findViewById(R.id.profile_image);
        tvName = findViewById(R.id.tv_profile_fullname);
        tvEmail = findViewById(R.id.tv_profile_email);
        rvBid = findViewById(R.id.rv_lelang_ikan);

        dbFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        id = String.valueOf(user.getUid());

        setProfile();
        setListenerBid();
        setBidCreatedList();

        btnAddBidFish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddBidActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem prevMenuItem){
        switch (prevMenuItem.getItemId()) {
            case R.id.menu_cust:
                goRequest();
                return true;
            case R.id.menu_logout:
                signOut();
                return true;
            case R.id.menu_notif:
                goNotif();
                return true;
            default:
                return super.onOptionsItemSelected(prevMenuItem);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void setProfile(){

        DocumentReference profile = dbFirestore.collection("auctioneer").document(id);
        profile.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        DocumentSnapshot doc = task.getResult();

                        StringBuilder fieldName = new StringBuilder();
                        fieldName.append(doc.get(name));
                        tvName.setText(fieldName.toString());

                        StringBuilder fieldEmail = new StringBuilder();
                        fieldEmail.append(doc.get(email));
                        tvEmail.setText(fieldEmail.toString());

                        Glide.with(getApplicationContext()).load(doc.get(photoUrl)).into(ivPhoto);
                    }
                })

        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Tidak dapat membaca data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setListenerBid(){

        firestoreListener = dbFirestore.collection("auctioneer").document(id).collection("fishes")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        List<BidFish> bidFishList = new ArrayList<>();

                        for (DocumentSnapshot doc : documentSnapshots){
                            BidFish bidFish = doc.toObject(BidFish.class);
                            bidFish.setId(doc.getId());
                            bidFishList.add(bidFish);
                        }

                        rvAdapter = new BidFishAdapter(bidFishList, getApplicationContext(), dbFirestore);
                        rvBid.setAdapter(rvAdapter);
                    }
                });
    }

    private void setBidCreatedList () {

        dbFirestore.collection("auctioneer").document(id).collection("fishes").orderBy(FieldPath.of("createdBid"), Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<BidFish> bidFishList = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()){
                                BidFish bidFish = doc.toObject(BidFish.class);
                                bidFish.setId(doc.getId());
                                bidFishList.add(bidFish);
                            }

                            rvAdapter = new BidFishAdapter(bidFishList, getApplicationContext(), dbFirestore);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rvBid.setLayoutManager(mLayoutManager);
                            rvBid.setAdapter(rvAdapter);
                        } else {
                            Toast.makeText(getApplicationContext(), "there is not data", Toast.LENGTH_SHORT).show();
                            task.getException();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        firestoreListener.remove();
    }

    public void signOut(){
        auth.signOut();

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void goNotif(){
        Intent notif = new Intent(MainActivity.this, NotifActivity.class);
        startActivity(notif);
    }

    public void goRequest(){
        Intent req = new Intent(MainActivity.this, RequestActivity.class);
        startActivity(req);
    }
}