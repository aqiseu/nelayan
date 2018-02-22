package com.learn.dimdimasdim.bidfishnelayan.view;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.learn.dimdimasdim.bidfishnelayan.R;
import com.learn.dimdimasdim.bidfishnelayan.data.model.BidFish;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddBidActivity extends AppCompatActivity {

    private ImageView inputPhoto;
    private EditText inputTypeFish, inputTimeCatching, inputLocation, inputPriceBid, inputTimeToBid;
    private Button inputBid;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Uri selectedImage;

    private String id, createdBid, typeFish, timeCathing, location, priceBid, timeBid, photoFish;

    private FirebaseFirestore dbFirestore;
    private FirebaseStorage storage;
    private StorageReference reference;
    private FirebaseUser user;

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private LayoutInflater inflater;
    private Button inputPhotoFromCamera, inputPhotoFromGallery;
    private ImageView addPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bid);

        dbFirestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference().child("Fishes");

        inputTypeFish = findViewById(R.id.edt_type_fish);
        inputTimeCatching = findViewById(R.id.edt_time_catching);
        inputLocation = findViewById(R.id.edt_location);
        inputPriceBid = findViewById(R.id.edt_price_bid_fish);
        inputTimeToBid = findViewById(R.id.edt_time_for_bid);
        inputPhoto = findViewById(R.id.img_upload_photo_bid);
        inputBid = findViewById(R.id.btn_send_bid);
        addPhoto = findViewById(R.id.ic_add_photo_bid);

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlertDialogUploadPhoto();
            }
        });

        inputBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBid();
            }
        });



    }

    //coding for datepicker

    private void setAlertDialogUploadPhoto (){
        builder = new AlertDialog.Builder(AddBidActivity.this);
        inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.alertdialog_upload_photo, null);
        builder.setCancelable(true);
        builder.setView(view);

        inputPhotoFromCamera = (Button)view.findViewById(R.id.btn_upload_from_camera);
        inputPhotoFromGallery = (Button)view.findViewById(R.id.btn_upload_from_gallery);

        alertDialog = builder.create();
        alertDialog.show();

        inputPhotoFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
                alertDialog.cancel();
                //finish();
            }
        });

        inputPhotoFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                alertDialog.cancel();
                //finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == SELECT_FILE)
                onSelectedFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onSelectedFromGalleryResult (Intent data){
        Bitmap bm = null;
        if (data != null){
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                selectedImage = data.getData();
                inputPhoto.setImageBitmap(bm);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    private void onCaptureImageResult(Intent data){
        Bitmap thumbnail = (Bitmap)data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis()+ ".jpg");
        selectedImage = Uri.fromFile(destination);
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputPhoto.setImageBitmap(thumbnail);
    }

    private String setCreatedBid(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        return dateFormat.format(date);
    }

    private void addBid(){

        user = FirebaseAuth.getInstance().getCurrentUser();
        id = user.getUid();

        typeFish = inputTypeFish.getText().toString();
        timeCathing = inputTimeCatching.getText().toString();
        location = inputLocation.getText().toString();
        priceBid = inputPriceBid.getText().toString();
        timeBid = inputTimeToBid.getText().toString();
        createdBid = setCreatedBid();


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait . .");
        progressDialog.show();

        StorageReference fishImage = reference.child(selectedImage.getLastPathSegment());
        fishImage.putFile(selectedImage)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        photoFish = downloadUrl.toString();

                        BidFish fish = new BidFish(id, createdBid, typeFish, timeCathing, location, priceBid, timeBid, photoFish);

                        dbFirestore.collection("auctioneer").document(id).collection("fishes")
                                .add(fish)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        progressDialog.dismiss();

                                        Toast.makeText(AddBidActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddBidActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
    }

}
