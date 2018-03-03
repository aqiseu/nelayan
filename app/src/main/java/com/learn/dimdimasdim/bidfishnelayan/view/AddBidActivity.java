package com.learn.dimdimasdim.bidfishnelayan.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBidActivity extends AppCompatActivity {

    @BindView(R.id.spn_time_picker)
    Spinner spnTimePicker;
    @BindView(R.id.edt_time_catching)
    TextView edtTimeCatching;

    private int mYear, mMonth, mDay;
    private ImageView inputPhoto;
    private EditText inputTypeFish, inputLocation, inputPriceBid;

    private Button inputBid;

    public TimePickerDialog.Builder timePicker;

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
    private ImageView addPhoto, inputPhotoFromCamera, inputPhotoFromGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bid);
        ButterKnife.bind(this);

        dbFirestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference().child("Fishes");

        inputTypeFish = findViewById(R.id.edt_type_fish);

        spnTimePicker = findViewById(R.id.spn_time_picker);

        inputLocation = findViewById(R.id.edt_location);
        inputPriceBid = findViewById(R.id.edt_price_bid_fish);

        inputPhoto = findViewById(R.id.img_upload_photo_bid);
        inputBid = findViewById(R.id.btn_send_bid);
        addPhoto = findViewById(R.id.ic_add_photo_bid);

        List<String> list = new ArrayList<String>();
        list.add("Set Auction Time");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        list.add("15");
        list.add("16");
        list.add("17");
        list.add("18");
        list.add("19");
        list.add("20");
        list.add("21");
        list.add("22");
        list.add("23");
        list.add("24");
        list.add("25");
        list.add("26");
        list.add("27");
        list.add("28");
        list.add("29");
        list.add("30");
        list.add("31");
        list.add("32");
        list.add("33");
        list.add("34");
        list.add("35");
        list.add("36");
        list.add("37");
        list.add("38");
        list.add("39");
        list.add("40");
        list.add("41");
        list.add("42");
        list.add("43");
        list.add("44");
        list.add("45");
        list.add("46");
        list.add("47");
        list.add("48");
        list.add("49");
        list.add("50");
        list.add("51");
        list.add("52");
        list.add("53");
        list.add("54");
        list.add("55");
        list.add("56");
        list.add("57");
        list.add("58");
        list.add("59");
        list.add("60");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnTimePicker.setAdapter(adapter);

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

    private void setAlertDialogUploadPhoto() {
        builder = new AlertDialog.Builder(AddBidActivity.this);
        inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.alertdialog_upload_photo, null);
        builder.setCancelable(true);
        builder.setView(view);

        inputPhotoFromCamera = (ImageView) view.findViewById(R.id.btn_upload_from_camera);
        inputPhotoFromGallery = (ImageView) view.findViewById(R.id.btn_upload_from_gallery);

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
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectedFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onSelectedFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                selectedImage = data.getData();
                inputPhoto.setImageBitmap(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
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

    private String setCreatedBid() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        return dateFormat.format(date);
    }

    private void addBid() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        id = user.getUid();

        typeFish = inputTypeFish.getText().toString();
        timeCathing = edtTimeCatching.getText().toString();
        timeBid = spnTimePicker.getSelectedItem().toString();
        location = inputLocation.getText().toString();
        priceBid = inputPriceBid.getText().toString();
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
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AddBidActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }
        });;
    }

    @OnClick(R.id.edt_time_catching)
    public void onViewClicked() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        edtTimeCatching.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
