package com.example.orlineyuk.Adapter;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orlineyuk.R;
import com.example.orlineyuk.model.MenuMakanan;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterMenuMakanan extends RecyclerView.Adapter<AdapterMenuMakanan.myViewHolder> {

    Context context;
    ArrayList<MenuMakanan> menuMakanans;

    private Button updatemenu;
    private Button deletemenu;
    private EditText namaMknan;
    private EditText hrgaMknan;


    public AdapterMenuMakanan(Context c, ArrayList<MenuMakanan> menu){
        context = c;
        menuMakanans = menu;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new myViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recycler_menu_makanan,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, final int i) {
        final String id = menuMakanans.get(i).getId();
        holder.Namamkn.setText(menuMakanans.get(i).getNamaMakanan());
        holder.hargamkn.setText(String.valueOf(menuMakanans.get(i).getHargaMakanan()));

        holder.edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Dialog myDialog = new Dialog(context);
                TextView txtclose;
                myDialog.setContentView(R.layout.popup_menumakan);
                txtclose =(TextView) myDialog.findViewById(R.id.cancel);
                namaMknan = (EditText) myDialog.findViewById(R.id.namamakanan);
                hrgaMknan = (EditText) myDialog.findViewById(R.id.hargamakanan);
                updatemenu = (Button) myDialog.findViewById(R.id.addmenumkn);


                namaMknan.setText(menuMakanans.get(i).getNamaMakanan());
                hrgaMknan.setText(String.valueOf(menuMakanans.get(i).getHargaMakanan()));

                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });

                updatemenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nama = namaMknan.getText().toString().trim();
                        String cekharga = hrgaMknan.getText().toString().trim();
                        if (!TextUtils.isEmpty(nama) || !TextUtils.isEmpty(cekharga)){
                            Integer harga = Integer.valueOf(hrgaMknan.getText().toString().trim());
                            updateMenuMakanan(id, nama, harga);
                            myDialog.dismiss();
                        }else {
                            Toast.makeText(context.getApplicationContext(), "Harap Isi Semua Form", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(context); // Set the dialog title.
                myAlertBuilder.setTitle("Hapus Menu");  // Set the dialog message.
                myAlertBuilder.setMessage("Apakah Anda Yakin ?");

                myAlertBuilder.setPositiveButton("Ya", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {// User cancelled the dialog.
                                // You have to get the position like this, you can't hold a reference
                                deleteMenuMakanan(id);
                            }
                        });

                myAlertBuilder.setNegativeButton("Tidak", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) { // User clicked OK button.
                            }
                        });

                AlertDialog dialog = myAlertBuilder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuMakanans.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView Namamkn, hargamkn;
        Button edit, delete;
        public myViewHolder(View itemView){
            super(itemView);

            Namamkn = (TextView) itemView.findViewById(R.id.namamknan);
            hargamkn = (TextView) itemView.findViewById(R.id.hargamknan);
            edit = (Button) itemView.findViewById(R.id.editmkn);
            delete = (Button) itemView.findViewById(R.id.delmkn);
        }
    }

    private boolean updateMenuMakanan(String id, String nama, int harga){
        DatabaseReference menu = FirebaseDatabase.getInstance().getReference("MenuMakanan").child(id);

        MenuMakanan upmenu = new MenuMakanan(id, nama, harga);
        menu.setValue(upmenu);
        Toast.makeText(context.getApplicationContext(), "Menu Update", Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean deleteMenuMakanan(String id){
        DatabaseReference menu = FirebaseDatabase.getInstance().getReference("MenuMakanan").child(id);

        menu.removeValue();
        Toast.makeText(context.getApplicationContext(), "Menu Deleted", Toast.LENGTH_SHORT).show();
        return true;
    }





}
