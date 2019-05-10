package com.example.orlineyuk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button signin;
    private EditText email;
    private EditText pass;
    private TextView reg;
    static String userEmail;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            FirebaseUser user = firebaseAuth.getCurrentUser();
            userEmail = user.getEmail();
            finish();
            startActivity(new Intent(getApplicationContext(), MainMenu.class));
        }



        signin = (Button) findViewById(R.id.signin);
        email = (EditText) findViewById(R.id.emaillog);
        pass = (EditText) findViewById(R.id.passlog);
        reg = (TextView) findViewById(R.id.goreg);

        signin.setOnClickListener(this);
        reg.setOnClickListener(this);




    }

    public void UserLogin(){
        String emaillog = email.getText().toString().trim();
        String passlog = pass.getText().toString().trim();

        if(TextUtils.isEmpty(emaillog)){
            Toast.makeText(this, "Masukkan email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(passlog)){
            Toast.makeText(this, "Masukkan password", Toast.LENGTH_SHORT).show();
        }

        firebaseAuth.signInWithEmailAndPassword(emaillog, passlog)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            finish();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            userEmail = user.getEmail();
                            startActivity(new Intent(getApplicationContext(), MainMenu.class));
                        }else {
                            Toast.makeText(Login.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }

    @Override
    public void onClick(View v) {

        if (v == signin){
            UserLogin();
        }
        if (v == reg){
            startActivity(new Intent(getApplicationContext(), Register.class));
        }

    }
}
