package com.example.orlineyuk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orlineyuk.model.ModelPesanMakanan;
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

public class MainMenuAdmin extends AppCompatActivity implements View.OnClickListener {

    private CardView MenuMakan;
    private CardView editstatpes;
    private CardView logout;
    private ImageView gosettt;
    private TextView namaus;

    ArrayList<model_user> users;
    DatabaseReference databaselist;
    String nama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_admin);

        MenuMakan = (CardView) findViewById(R.id.menumakan);
        editstatpes = (CardView) findViewById(R.id.editpsnmkn);
        gosettt = (ImageView) findViewById(R.id.gosettad);
        namaus = (TextView) findViewById(R.id.namauser);
        logout = (CardView) findViewById(R.id.signoutadmin);

        logout.setOnClickListener(this);
        MenuMakan.setOnClickListener(this);
        editstatpes.setOnClickListener(this);
        gosettt.setOnClickListener(this);

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
        if (v == MenuMakan){
            startActivity(new Intent(getApplicationContext(), EditMenuMakanan.class));
        }


        if (v == editstatpes){
            startActivity(new Intent(getApplicationContext(), EditPesananAdmin.class));
        }

        if (v == gosettt){
            startActivity(new Intent(getApplicationContext(), SettingProfileAdmin.class));
        }

        if (v == logout){
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
