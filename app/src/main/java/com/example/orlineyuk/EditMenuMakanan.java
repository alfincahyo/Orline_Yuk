package com.example.orlineyuk;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orlineyuk.Adapter.AdapterMenuMakanan;
import com.example.orlineyuk.model.MenuMakanan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditMenuMakanan extends AppCompatActivity{

    Dialog myDialog;
    private Button tmbhmmakanan;
    private EditText namaMknan;
    private EditText hrgaMknan;

    DatabaseReference databaseMakanan;
    DatabaseReference databaseList;
    ArrayList<MenuMakanan> menu;
    RecyclerView recyclerView;
    AdapterMenuMakanan Adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu_makanan);

        recyclerView = (RecyclerView) findViewById(R.id.recyclermenumakan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        menu = new ArrayList<MenuMakanan>();

        databaseMakanan = FirebaseDatabase.getInstance().getReference("MenuMakanan");
        databaseList = FirebaseDatabase.getInstance().getReference().child("MenuMakanan");
        databaseList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                menu = new ArrayList<MenuMakanan>();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    MenuMakanan m = dataSnapshot1.getValue(MenuMakanan.class);
                    menu.add(m);
                }
                Adapter = new AdapterMenuMakanan(EditMenuMakanan.this, menu);
                recyclerView.setAdapter(Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditMenuMakanan.this, "Oops Something Wrong", Toast.LENGTH_LONG).show();

            }
        });
        myDialog = new Dialog(this);



    }

    public void ShowPopup(View v) {
        TextView txtclose;
        myDialog.setContentView(R.layout.popup_menumakan);
        txtclose =(TextView) myDialog.findViewById(R.id.cancel);
        namaMknan = (EditText) myDialog.findViewById(R.id.namamakanan);
        hrgaMknan = (EditText) myDialog.findViewById(R.id.hargamakanan);
        tmbhmmakanan = (Button) myDialog.findViewById(R.id.addmenumkn);

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        tmbhmmakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TambahMenuMakan();
                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void TambahMenuMakan(){
        String nama = namaMknan.getText().toString().trim();
        String cekHarga = hrgaMknan.getText().toString().trim();

        if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(cekHarga)){
            Toast.makeText(this, "Isi Semua Form", Toast.LENGTH_LONG).show();
        }else {
            String id = databaseMakanan.push().getKey();
            Integer harga = Integer.valueOf(hrgaMknan.getText().toString().trim());

            MenuMakanan menu = new MenuMakanan(id, nama, harga);
            databaseMakanan.child(id).setValue(menu);
            Toast.makeText(this, "Menu ditambahkan", Toast.LENGTH_LONG).show();

        }
    }

}
