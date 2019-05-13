package com.example.orlineyuk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orlineyuk.model.model_user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.orlineyuk.Login.userEmail;

public class SettingProfileAdmin extends AppCompatActivity {

    DatabaseReference databaselist;
    ArrayList<model_user> users;

    EditText namasett;
    EditText nosett;
    Button save;

    String id;
    String nama;
    String nohp;
    String email;
    String ceknama;
    String cekno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile);

        namasett = (EditText) findViewById(R.id.setnama);
        nosett = (EditText) findViewById(R.id.setno);
        save = (Button) findViewById(R.id.saveset);

        databaselist = FirebaseDatabase.getInstance().getReference("TabelUser");

        Query getlist = databaselist.orderByChild("email").equalTo(userEmail);

        getlist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users = new ArrayList<model_user>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    id = dataSnapshot1.child("id").getValue(String.class);
                    email = dataSnapshot1.child("email").getValue(String.class);
                    nama = dataSnapshot1.child("nama").getValue(String.class);
                    nohp = dataSnapshot1.child("nohp").getValue(String.class);
                    namasett.setText(nama);
                    nosett.setText(nohp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SettingProfileAdmin.this, "Oops Something Wrong", Toast.LENGTH_LONG).show();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ceknama = namasett.getText().toString().trim();
                cekno = nosett.getText().toString().trim();
                if (!TextUtils.isEmpty(ceknama) && !TextUtils.isEmpty(cekno)){
                    update_setting(id, ceknama, cekno, email);
                    startActivity(new Intent(getApplicationContext(), MainMenuAdmin.class));
                }else {
                    Toast.makeText(SettingProfileAdmin.this, "Isi Semua Form", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean update_setting(String id, String nama, String nohp, String email){
        DatabaseReference setuser = FirebaseDatabase.getInstance().getReference("TabelUser").child(id);

        model_user user = new model_user(id, nama, nohp, email);
        setuser.setValue(user);
        Toast.makeText(this, "Setting Update", Toast.LENGTH_SHORT).show();
        return true;
    }
}
