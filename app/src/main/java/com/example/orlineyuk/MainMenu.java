package com.example.orlineyuk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    private Button pesanmakan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        pesanmakan = (Button) findViewById(R.id.pesan_makan);

    }

    @Override
    public void onClick(View v) {
        if (v == pesanmakan){
            startActivity(new Intent(getApplicationContext(), PesanMakanan.class));
        }
    }
}
