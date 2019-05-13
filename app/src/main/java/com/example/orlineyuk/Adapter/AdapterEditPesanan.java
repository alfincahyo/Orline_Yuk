package com.example.orlineyuk.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orlineyuk.R;
import com.example.orlineyuk.model.ModelPesanMakanan;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterEditPesanan extends RecyclerView.Adapter<AdapterEditPesanan.myViewHolder4>{

    private String pilihstatus;
    private Button editbut;
    private RadioButton rad1, rad2, rad3, rad4;


    class myViewHolder4 extends RecyclerView.ViewHolder{
        TextView shnama, shjum, shbiaya, shstatus, shemail;
        Spinner spinner;
        Button edit;

        public myViewHolder4(View itemView){
            super(itemView);
            shemail = (TextView) itemView.findViewById(R.id.pemesan);
            shnama = (TextView) itemView.findViewById(R.id.namapesad);
            shjum = (TextView) itemView.findViewById(R.id.jumpesad);
            shbiaya = (TextView) itemView.findViewById(R.id.biayastatad);
            shstatus = (TextView) itemView.findViewById(R.id.statuspesad);
            edit = (Button) itemView.findViewById(R.id.editpes);
        }
    }

    Context context;
    ArrayList<ModelPesanMakanan> pesan;

    public AdapterEditPesanan(Context c, ArrayList<ModelPesanMakanan> listpesan){
        context = c;
        pesan = listpesan;
    }

    @NonNull
    @Override
    public myViewHolder4 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new myViewHolder4(LayoutInflater.from(context)
                .inflate(R.layout.recycler_edit_pesanan,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder4 holder, int i) {
        holder.shemail.setText(String.valueOf(pesan.get(i).getEmail()));
        holder.shbiaya.setText(String.valueOf(pesan.get(i).getHarga()));
        holder.shjum.setText(String.valueOf(pesan.get(i).getJumpesanan()));
        holder.shnama.setText(pesan.get(i).getNama());
        holder.shstatus.setText(pesan.get(i).getStatPesan());
        final String nama = pesan.get(i).getNama();
        final Integer jum = pesan.get(i).getJumpesanan();
        final Integer harga = pesan.get(i).getHarga();
        final String cekstatus = pesan.get(i).getStatPesan();
        final String email = pesan.get(i).getEmail();
        final String id = pesan.get(i).getId();

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog myDialog = new Dialog(context);
                TextView txtclose;
                myDialog.setContentView(R.layout.popup_edit_status);

                txtclose =(TextView) myDialog.findViewById(R.id.cancel);
                editbut = (Button) myDialog.findViewById(R.id.addeditstat);
                rad1 = (RadioButton) myDialog.findViewById(R.id.stat1);
                rad2 = (RadioButton) myDialog.findViewById(R.id.stat2);
                rad3 = (RadioButton) myDialog.findViewById(R.id.stat3);
                rad4 = (RadioButton) myDialog.findViewById(R.id.stat4);

                rad1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rad1.isChecked()){
                            pilihstatus = "Sedang Mengantri";
                        }
                    }
                });
                rad2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rad2.isChecked()){
                            pilihstatus = "Dalam Proses Pembuatan";
                        }
                    }
                });
                rad3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rad3.isChecked()){
                            pilihstatus = "Pesanan Siap Diambil";
                        }
                    }
                });
                rad4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rad4.isChecked()){
                            pilihstatus = "Transaksi Selesai";
                        }
                    }
                });


                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();

                    }
                });

                editbut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(pilihstatus)){
                            updateStatusPesanan(id, nama, harga, jum, pilihstatus, email);
                            Toast.makeText(context, "Status Saved", Toast.LENGTH_SHORT).show();
                            myDialog.dismiss();
                        }else {
                            Toast.makeText(context, "Pilih Status", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return pesan.size();
    }

    private boolean updateStatusPesanan(String id, String nama, int harga, int jumpesan, String stat, String Email){
        DatabaseReference menu = FirebaseDatabase.getInstance().getReference("ListPesanan").child(id);

        ModelPesanMakanan upmenu = new ModelPesanMakanan(id, nama, jumpesan, harga, stat, Email);
        menu.setValue(upmenu);
        return true;
    }


}
