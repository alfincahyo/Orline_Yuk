package com.example.orlineyuk;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orlineyuk.Adapter.AdapterMenuMakanan;
import com.example.orlineyuk.model.MenuMakanan;
import com.example.orlineyuk.model.ModelPesanMakanan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.orlineyuk.Login.userEmail;

public class PesanMakanan extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    DatabaseReference databaselistmenu;
    DatabaseReference databasepesanan;
    private ArrayList<MenuMakanan> menu;
    private AdapterMenuMakanan Adapter;
    private Spinner spinner;
    private Dialog myDialog;


    private String menuspinner;
    private Integer harga;
    private Integer jumfix;
    private Integer jumtot;

    private TextView hrgapes;
    private EditText jumpes;
    private Button pesan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_makanan);

        jumpes = (EditText) findViewById(R.id.JumPesanan);
        hrgapes = (TextView) findViewById(R.id.hargamkn);

        spinner = findViewById(R.id.spinnerListMenu);
        databasepesanan = FirebaseDatabase.getInstance().getReference("ListPesanan");

        //Memuncukan data dari firebase ke spinner
        databaselistmenu = FirebaseDatabase.getInstance().getReference().child("MenuMakanan");
        databaselistmenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> menu = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String nama = dataSnapshot1.child("namaMakanan").getValue(String.class);
                    menu.add(nama);
                }
                ArrayAdapter<String> menuAdapter = new ArrayAdapter<String>
                        (PesanMakanan.this, android.R.layout.simple_spinner_item, menu);
                menuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(menuAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (spinner != null){
            spinner.setOnItemSelectedListener(this);
        }


        myDialog = new Dialog(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        menuspinner = parent.getItemAtPosition(position).toString();
        String cekjum = jumpes.getText().toString();

        if(menuspinner == null || cekjum == null){
            hrgapes.setText("0");
            jumpes.setText("0");
        }else {
            Query cariharga = FirebaseDatabase.getInstance().getReference("MenuMakanan")
                    .orderByChild("namaMakanan").equalTo(menuspinner);
            cariharga.addValueEventListener(Event);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    ValueEventListener Event = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    harga = snapshot.child("hargaMakanan").getValue(Integer.class);
                    hrgapes.setText(String.valueOf(harga));
                }

            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public Integer hargatotal(int harga, int jumlah){
        int total;
        total = harga * jumlah;
        return total;
    }

    public void ShowPopupPesan(View v) {
        TextView txtclose;
        TextView total;
        Button book;
        String cekjum = jumpes.getText().toString().trim();
        Integer hargafix = harga;
        final String nama = menuspinner;


        if (!TextUtils.isEmpty(cekjum)){
            myDialog.setContentView(R.layout.popup_pemesanan);

            jumfix = Integer.valueOf(cekjum);
            jumtot = hargatotal(hargafix, jumfix);
            txtclose =(TextView) myDialog.findViewById(R.id.cancel);
            total = (TextView) myDialog.findViewById(R.id.showtotal);
            book = (Button) myDialog.findViewById(R.id.bookmkn);
            String status = "Sedang Mengantri";

            total.setText("Total Harga : Rp. "+ jumtot);
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });

            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = databasepesanan.push().getKey();
                    String status = "Sedang Mengantri";
                    ModelPesanMakanan pesan = new ModelPesanMakanan(id, nama, jumfix, jumtot, status,userEmail );
                    databasepesanan.child(id).setValue(pesan);
                    Toast.makeText(PesanMakanan.this, "Berhasil Di Pesan", Toast.LENGTH_LONG).show();
                    myDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), MainMenu.class));
                }
            });

            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }
        else {
            Toast.makeText(this, "Isi Semua Form", Toast.LENGTH_SHORT).show();
        }


    }

}
