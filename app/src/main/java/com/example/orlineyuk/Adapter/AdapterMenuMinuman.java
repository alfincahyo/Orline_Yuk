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
import com.example.orlineyuk.model.MenuMinuman;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterMenuMinuman extends RecyclerView.Adapter<AdapterMenuMinuman.myViewHolder2> {

    Context context;
    ArrayList<MenuMinuman> menuMinuman;

    private Button updatemenu;
    private Button deletemenu;
    private EditText NamaMinum;
    private EditText HargaMinum;

    public AdapterMenuMinuman(Context c, ArrayList<MenuMinuman> menu){
        context = c;
        menuMinuman = menu;
    }

    @NonNull
    @Override
    public myViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new myViewHolder2(LayoutInflater.from(context)
                .inflate(R.layout.recycler_menu_minuman,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder2 holder, final int i) {
        final String id = menuMinuman.get(i).getId();
        holder.nmaminum.setText(menuMinuman.get(i).getNamaMinuman());
        holder.hrgminum.setText(String.valueOf(menuMinuman.get(i).getHargaMinuman()));

        holder.editmin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Dialog myDialog = new Dialog(context);
                TextView txtclose;
                myDialog.setContentView(R.layout.popup_menuminuman);
                txtclose =(TextView) myDialog.findViewById(R.id.cancel);
                NamaMinum = (EditText) myDialog.findViewById(R.id.namaminuman);
                HargaMinum = (EditText) myDialog.findViewById(R.id.hargaminuman);
                updatemenu = (Button) myDialog.findViewById(R.id.addmenuminum);


                NamaMinum.setText(menuMinuman.get(i).getNamaMinuman());
                HargaMinum.setText(String.valueOf(menuMinuman.get(i).getHargaMinuman()));

                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });

                updatemenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nama = NamaMinum.getText().toString().trim();
                        String cekharga = HargaMinum.getText().toString().trim();
                        if (!TextUtils.isEmpty(nama) || !TextUtils.isEmpty(cekharga)){
                            Integer harga = Integer.valueOf(HargaMinum.getText().toString().trim());

                            updateMenuMinuman(id, nama, harga);
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

        holder.deletemin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(context); // Set the dialog title.
                myAlertBuilder.setTitle("Hapus Menu");  // Set the dialog message.
                myAlertBuilder.setMessage("Apakah Anda Yakin ?");

                myAlertBuilder.setPositiveButton("Ya", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {// User cancelled the dialog.
                                // You have to get the position like this, you can't hold a reference
                                deleteMenuMinuman(id);
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
        return menuMinuman.size();
    }

    class myViewHolder2 extends RecyclerView.ViewHolder{
        TextView nmaminum, hrgminum;
        Button editmin, deletemin;
        public myViewHolder2(View itemView){
            super(itemView);

            nmaminum = (TextView) itemView.findViewById(R.id.namaminrec);
            hrgminum = (TextView) itemView.findViewById(R.id.hargaminrec);
            editmin = (Button) itemView.findViewById(R.id.editminum);
            deletemin = (Button) itemView.findViewById(R.id.delminum);
        }
    }

    private boolean updateMenuMinuman(String id, String nama, int harga){
        DatabaseReference menu = FirebaseDatabase.getInstance().getReference("MenuMinuman").child(id);

        MenuMinuman upmenu = new MenuMinuman(id, nama, harga);
        menu.setValue(upmenu);
        Toast.makeText(context.getApplicationContext(), "Menu Update", Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean deleteMenuMinuman(String id){
        DatabaseReference menu = FirebaseDatabase.getInstance().getReference("MenuMinuman").child(id);

        menu.removeValue();
        Toast.makeText(context.getApplicationContext(), "Menu Deleted", Toast.LENGTH_SHORT).show();
        return true;
    }

}
