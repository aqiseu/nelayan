package com.learn.dimdimasdim.bidfishnelayan.view;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.learn.dimdimasdim.bidfishnelayan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {


    private Button btnAddDepos, btnSeeReceipt, btnConf, btnCancelConf, btnVerify;
    private AlertDialog.Builder profBuilder, confBuilder;
    private AlertDialog profDialog, confDialog;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnSeeReceipt = (Button)findViewById(R.id.btn_see_receipt);
        btnSeeReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlertDialogReceipt();
            }
        });

        btnVerify = (Button) findViewById(R.id.btn_verify);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlertDialogVerify();
            }
        });



    }

    private void setAlertDialogVerify(){

        confBuilder = new AlertDialog.Builder(ProfileActivity.this);
        inflater = getLayoutInflater();

        View v = inflater.inflate(R.layout.alert_confirm_verif, null);
        confBuilder.setCancelable(true);
        confBuilder.setView(v);

        confDialog = confBuilder.create();
        confDialog.show();

        btnConf = (Button) v.findViewById(R.id.btn_confirm);
        btnCancelConf = (Button) v.findViewById(R.id.btn_cancel_confirm);

        btnConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnVerify.setVisibility(View.INVISIBLE);
                confDialog.cancel();
            }
        });

        btnCancelConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confDialog.cancel();
            }
        });
    }

    private void setAlertDialogReceipt() {

        profBuilder = new AlertDialog.Builder(ProfileActivity.this);
        inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.alert_inputdepo, null);
        profBuilder.setCancelable(true);
        profBuilder.setView(view);

        profDialog = profBuilder.create();
        profDialog.show();

        btnAddDepos = (Button) view.findViewById(R.id.btn_add_depos);

        btnAddDepos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profDialog.cancel();
            }
        });

    }



}
