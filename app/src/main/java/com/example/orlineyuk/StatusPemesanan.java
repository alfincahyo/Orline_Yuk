package com.example.orlineyuk;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.orlineyuk.Adapter.AdapterListPesanan;
import com.example.orlineyuk.model.ModelPesanMakanan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.orlineyuk.Login.userEmail;

public class StatusPemesanan extends AppCompatActivity {

    DatabaseReference databaselist;

    ArrayList<ModelPesanMakanan> pesanmkn;
    RecyclerView recyclerView;
    AdapterListPesanan Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_pemesanan);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerlistpesanan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaselist = FirebaseDatabase.getInstance().getReference("ListPesanan");

        Query getlist = databaselist.orderByChild("email").equalTo(userEmail);


        getlist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pesanmkn = new ArrayList<ModelPesanMakanan>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String id = dataSnapshot1.child("id").getValue(String.class);
                    String nama = dataSnapshot1.child("nama").getValue(String.class);
                    Integer jumlah = dataSnapshot1.child("jumpesanan").getValue(Integer.class);
                    Integer harga = dataSnapshot1.child("harga").getValue(Integer.class);
                    String status = dataSnapshot1.child("statPesan").getValue(String.class);
                    String email = dataSnapshot1.child("email").getValue(String.class);

                    ModelPesanMakanan pesan = new ModelPesanMakanan(id, nama, jumlah, harga, status,email);
                    pesanmkn.add(pesan);
                }
                Adapter = new AdapterListPesanan(StatusPemesanan.this, pesanmkn);
                recyclerView.setAdapter(Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StatusPemesanan.this, "Oops Something Wrong", Toast.LENGTH_LONG).show();
            }
        });
    }


}

