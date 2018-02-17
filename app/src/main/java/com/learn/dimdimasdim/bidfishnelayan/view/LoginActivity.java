package com.learn.dimdimasdim.bidfishnelayan.view;

import com.learn.dimdimasdim.bidfishnelayan.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;

    private LinearLayout llToRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        llToRegister = findViewById(R.id.ll_to_register);
        btnLogin.setOnClickListener(this);
        llToRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_login:
                MainActivity.start(this);
                break;

            case R.id.ll_to_register:
                RegisterActivity.start(this);
                break;

        }

    }
}
