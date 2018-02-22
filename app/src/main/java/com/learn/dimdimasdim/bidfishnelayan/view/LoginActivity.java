package com.learn.dimdimasdim.bidfishnelayan.view;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.learn.dimdimasdim.bidfishnelayan.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText loginEmail, loginPassword;
    private LinearLayout llToRegister;
    private FirebaseAuth auth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.btn_login);
        llToRegister = findViewById(R.id.ll_to_register);
        loginEmail = findViewById(R.id.edt_login_email);
        loginPassword = findViewById(R.id.edt_login_password);

        btnLogin.setOnClickListener(this);
        llToRegister.setOnClickListener(this);

        if (auth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_login:
                loginAuthentication();
                break;

            case R.id.ll_to_register:
                RegisterActivity.start(this);
                break;

        }

    }

    private void loginAuthentication() {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Please Wait ..");
        dialog.setCancelable(false);
        dialog.show();

        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();

                        } else {
                            dialog.dismiss();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }
}
