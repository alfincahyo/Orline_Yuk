package com.example.orlineyuk;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orlineyuk.Adapter.AdapterEditPesanan;
import com.example.orlineyuk.Adapter.AdapterListPesanan;
import com.example.orlineyuk.model.ModelPesanMakanan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.orlineyuk.Login.userEmail;

public class EditPesananAdmin extends AppCompatActivity {

    DatabaseReference databaselist;


    LinearLayoutManager layoutManager;
    ArrayList<ModelPesanMakanan> pesanmkn;
    RecyclerView recyclerView;
    AdapterEditPesanan Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pesanan_admin);

        recyclerView = (RecyclerView) findViewById(R.id.recyclereditpesan);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        databaselist = FirebaseDatabase.getInstance().getReference().child("ListPesanan");;
        databaselist.addValueEventListener(new ValueEventListener() {
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
                Adapter = new AdapterEditPesanan(EditPesananAdmin.this, pesanmkn);
                recyclerView.setAdapter(Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditPesananAdmin.this, "Oops Something Wrong", Toast.LENGTH_LONG).show();

            }
        });


    }


}
