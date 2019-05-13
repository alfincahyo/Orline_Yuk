package com.example.orlineyuk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class animreg extends AppCompatActivity {

    ImageView bgone;
    Button buttonget;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animreg);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            FirebaseUser user = firebaseAuth.getCurrentUser();
            finish();
            startActivity(new Intent(getApplicationContext(), Login.class));
        }

        bgone = (ImageView) findViewById(R.id.bgone);
        buttonget = (Button) findViewById(R.id.buttonget);

        bgone.animate().scaleX(2).scaleY(2).setDuration(5000).start();
        buttonget.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
                Intent a = new Intent(animreg.this, Login.class);
                startActivity(a);
            }
        });
    }
}
