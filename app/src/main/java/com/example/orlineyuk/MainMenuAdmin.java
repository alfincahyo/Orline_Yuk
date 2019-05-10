package com.example.orlineyuk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

public class MainMenuAdmin extends AppCompatActivity implements View.OnClickListener {

    private CardView MenuMakan;
    private CardView MenuMinuman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_admin);

        MenuMakan = (CardView) findViewById(R.id.menumakan);
        MenuMinuman = (CardView) findViewById(R.id.menuminum);
        MenuMakan.setOnClickListener(this);
        MenuMinuman.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == MenuMakan){
            startActivity(new Intent(getApplicationContext(), EditMenuMakanan.class));
        }

        if (v == MenuMinuman) {
            startActivity(new Intent(getApplicationContext(), EditMenuMinuman.class));
        }
    }
}
