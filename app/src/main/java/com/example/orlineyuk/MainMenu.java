package com.example.orlineyuk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orlineyuk.model.model_user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.orlineyuk.Login.userEmail;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    private CardView pesanmakan;
    private CardView statuspsn;
    private CardView riwayat;
    private CardView signout;
    private CardView maps;
    private ImageView gosett;
    private TextView namaus;

    ArrayList<model_user> users;
    DatabaseReference databaselist;
    String nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        pesanmakan = (CardView) findViewById(R.id.pesan_makan);
        statuspsn = (CardView) findViewById(R.id.statmenu);
        riwayat = (CardView) findViewById(R.id.riwayatmenu);
        gosett = (ImageView) findViewById(R.id.goset);
        namaus = (TextView) findViewById(R.id.username);
        signout = (CardView) findViewById(R.id.signout);
        maps = (CardView) findViewById(R.id.menumaps);

        pesanmakan.setOnClickListener(this);
        statuspsn.setOnClickListener(this);
        riwayat.setOnClickListener(this);
        gosett.setOnClickListener(this);
        signout.setOnClickListener(this);
        maps.setOnClickListener(this);


        databaselist = FirebaseDatabase.getInstance().getReference("TabelUser");

        Query getlist = databaselist.orderByChild("email").equalTo(userEmail);

        getlist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users = new ArrayList<model_user>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    nama = dataSnapshot1.child("nama").getValue(String.class);
                    namaus.setText(nama);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == pesanmakan){
            startActivity(new Intent(getApplicationContext(), PesanMakanan.class));
        }
        if(v == statuspsn){
            startActivity(new Intent(getApplicationContext(), StatusPemesanan.class));
        }
        if(v == riwayat){
            startActivity(new Intent(getApplicationContext(), Riwayat_Pemesanan.class));
        }

        if (v == maps){
            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        }

        if (v == gosett){
            startActivity(new Intent(getApplicationContext(), SettingProfile.class));
        }

        if (v == signout){
            AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(this); // Set the dialog title.
            myAlertBuilder.setTitle("LOGOUT");  // Set the dialog message.
            myAlertBuilder.setMessage("Apakah Anda Yakin ?");

            myAlertBuilder.setPositiveButton("Ya", new
                    DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {// User cancelled the dialog.
                            // You have to get the position like this, you can't hold a reference
                            FirebaseAuth.getInstance().signOut();
                            finish();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }
                    });

            myAlertBuilder.setNegativeButton("Tidak", new
                    DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // User clicked OK button.
                            dialog.dismiss();
                        }
                    });

            AlertDialog dialog = myAlertBuilder.create();
            dialog.show();
        }

    }
}
