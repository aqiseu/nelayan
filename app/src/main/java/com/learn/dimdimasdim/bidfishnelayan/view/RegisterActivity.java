package com.learn.dimdimasdim.bidfishnelayan.view;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.learn.dimdimasdim.bidfishnelayan.R;
import com.learn.dimdimasdim.bidfishnelayan.data.model.Auctioneer;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputName;
    private EditText inputGender;
    private EditText inputAddress;
    private EditText inputPhone;
    private EditText inputPassword;
    private ImageView inputPhoto;

    private String id, email, password, name, gender, address, phone, photo;
    private int REQUEST_CAMERA = 0, SELECT_FILE =1;
    private Uri selectedImage;
    private Button btnRegister;

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private LayoutInflater inflater;
    private Button inputPhotoFromCamera, inputPhotoFromGallery;
    private ImageView addPhoto;


    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore dbAuctioneer;
    private FirebaseStorage firebaseStorage;
    private StorageReference reference;


    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        dbAuctioneer = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        reference = firebaseStorage.getReference().child("auctioneers");

        inputEmail = (EditText)findViewById(R.id.edt_email);
        inputName = (EditText)findViewById(R.id.edt_full_name);
        inputGender = (EditText)findViewById(R.id.edt_gender);
        inputAddress = (EditText)findViewById(R.id.edt_address);
        inputPhone = (EditText)findViewById(R.id.edt_phone);
        inputPassword = (EditText)findViewById(R.id.edt_password);
        btnRegister = (Button)findViewById(R.id.btn_register);
        inputPhoto = (ImageView)findViewById(R.id.img_register_upload_photo);
        addPhoto = (ImageView)findViewById(R.id.ic_register_add_photo_bid);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAuthentication();
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlertDialogUploadPhoto();
            }
        });

    }

    private void setAlertDialogUploadPhoto (){
        builder = new AlertDialog.Builder(RegisterActivity.this);
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

    public void registerAuthentication(){

        email = inputEmail.getText().toString();
        password = inputPassword.getText().toString();
        name = inputName.getText().toString();
        gender = inputGender.getText().toString();
        address = inputAddress.getText().toString();
        phone = inputPhone.getText().toString();

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait . . .");
        dialog.show();
        dialog.setCancelable(false);


        //autentikasi user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Gagal untuk mendaftar" + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(RegisterActivity.this, "Pendaftaran berhasil",
                                    Toast.LENGTH_SHORT).show();

                            //menyimpan foto user
                            StorageReference profileImage = reference.child(selectedImage.getLastPathSegment());
                            profileImage.putFile(selectedImage)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Uri downlodUrl = taskSnapshot.getDownloadUrl();
                                            photo = downlodUrl.toString();

                                            user = FirebaseAuth.getInstance().getCurrentUser();
                                            id = String.valueOf(user.getUid());

                                            Auctioneer data = new Auctioneer(id, email, name, gender, address, phone, password, photo);

                                            //menyimpan data ke firestore
                                            dbAuctioneer.collection("auctioneer").document(id).set(data)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(RegisterActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();

                                                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                            dialog.dismiss();
                                                            finish();
                                                        }
                                                    });

                                        }
                                    });

                        }

                    }
                });
    }

    private void viewHomeAuctioneer(Auctioneer auctioneer){
        Intent home = new Intent(getApplicationContext(), MainActivity.class);
        home.putExtra("photo", auctioneer.getPhotoUrl());
        home.putExtra("name", auctioneer.getName());
        home.putExtra("email", auctioneer.getEmail());
        startActivity(home);
    }


}
