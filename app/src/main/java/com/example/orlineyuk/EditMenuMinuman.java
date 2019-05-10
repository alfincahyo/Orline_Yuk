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

import com.example.orlineyuk.Adapter.AdapterMenuMinuman;
import com.example.orlineyuk.model.MenuMinuman;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditMenuMinuman extends AppCompatActivity {

    Dialog myDialog2;
    private Button tmbhmminuman;
    private EditText namaMinuman;
    private EditText hrgaMinuman;

    DatabaseReference databaseMinuman;
    DatabaseReference databaseListminuman;
    private ArrayList<MenuMinuman> minumen;
    RecyclerView recyclerView;
    AdapterMenuMinuman Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu_minuman);

        recyclerView = (RecyclerView) findViewById(R.id.recyclermenuminum);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        minumen = new ArrayList<MenuMinuman>();

        databaseMinuman = FirebaseDatabase.getInstance().getReference("MenuMinuman");
        databaseListminuman = FirebaseDatabase.getInstance().getReference().child("MenuMinuman");
        databaseListminuman.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data) {
                minumen = new ArrayList<MenuMinuman>();
                for (DataSnapshot dataSnapshot1: data.getChildren()){
                    MenuMinuman m = dataSnapshot1.getValue(MenuMinuman.class);
                    minumen.add(m);
                }
                Adapter = new AdapterMenuMinuman(EditMenuMinuman.this, minumen);
                recyclerView.setAdapter(Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditMenuMinuman.this, "Oops Something Wrong", Toast.LENGTH_LONG).show();

            }
        });
        myDialog2 = new Dialog(this);
    }

    public void ShowPopupMinum(View v) {
        TextView txtclose;
        myDialog2.setContentView(R.layout.popup_menuminuman);
        txtclose =(TextView) myDialog2.findViewById(R.id.cancel);
        namaMinuman = (EditText) myDialog2.findViewById(R.id.namaminuman);
        hrgaMinuman = (EditText) myDialog2.findViewById(R.id.hargaminuman);
        tmbhmminuman = (Button) myDialog2.findViewById(R.id.addmenuminum);

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog2.dismiss();
            }
        });

        tmbhmminuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TambahMenuMinuman();
                myDialog2.dismiss();
            }
        });

        myDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog2.show();
    }

    public void TambahMenuMinuman(){
        String nama = namaMinuman.getText().toString().trim();
        String cekharga = hrgaMinuman.getText().toString().trim();


        if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(cekharga)){
            Toast.makeText(this, "Isi Semua Form", Toast.LENGTH_LONG).show();
        }else {
            String id = databaseMinuman.push().getKey();
            Integer harga = Integer.valueOf(hrgaMinuman.getText().toString().trim());

            MenuMinuman menu = new MenuMinuman(id, nama, harga);
            databaseMinuman.child(id).setValue(menu);
            Toast.makeText(this, "Menu ditambahkan", Toast.LENGTH_LONG).show();

        }
    }
}
