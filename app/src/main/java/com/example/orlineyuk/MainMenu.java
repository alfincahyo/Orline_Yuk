package com.example.orlineyuk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.orlineyuk.Login.userEmail;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    private CardView pesanmakan;
    private CardView statuspsn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        pesanmakan = (CardView) findViewById(R.id.pesan_makan);
        statuspsn = (CardView) findViewById(R.id.statmenu);
        pesanmakan.setOnClickListener(this);
        statuspsn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == pesanmakan){
            startActivity(new Intent(getApplicationContext(), PesanMakanan.class));
        }
        if(v == statuspsn){
            startActivity(new Intent(getApplicationContext(), StatusPemesanan.class));
        }
    }
}
