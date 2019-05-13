package com.example.orlineyuk.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orlineyuk.R;
import com.example.orlineyuk.model.ModelPesanMakanan;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterRiwayatPemesanan extends RecyclerView.Adapter<AdapterRiwayatPemesanan.myViewHolder4> {

    Context context;
    ArrayList<ModelPesanMakanan> pesan;

    public AdapterRiwayatPemesanan(Context c, ArrayList<ModelPesanMakanan> listpesan){
        context = c;
        pesan = listpesan;
    }

    class myViewHolder4 extends RecyclerView.ViewHolder{
        TextView shnama, shjum, shbiaya, shstatus;
        Button delete;

        public myViewHolder4(View itemView){
            super(itemView);

            shnama = (TextView) itemView.findViewById(R.id.namapesriw);
            shjum = (TextView) itemView.findViewById(R.id.jumpesriw);
            shbiaya = (TextView) itemView.findViewById(R.id.biayastatriw);
            shstatus = (TextView) itemView.findViewById(R.id.statuspesriw);
            delete = (Button) itemView.findViewById(R.id.delete);
        }
    }

    @NonNull
    @Override
    public AdapterRiwayatPemesanan.myViewHolder4 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AdapterRiwayatPemesanan.myViewHolder4(LayoutInflater.from(context)
                .inflate(R.layout.recycler_riwayat_pemesanan,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRiwayatPemesanan.myViewHolder4 holder, final int i) {
        final String id = pesan.get(i).getId();
        holder.shbiaya.setText(String.valueOf(pesan.get(i).getHarga()));
        holder.shjum.setText(String.valueOf(pesan.get(i).getJumpesanan()));
        holder.shnama.setText(pesan.get(i).getNama());
        holder.shstatus.setText(pesan.get(i).getStatPesan());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(context); // Set the dialog title.
                myAlertBuilder.setTitle("Hapus Riwayat Pemesanan");  // Set the dialog message.
                myAlertBuilder.setMessage("Apakah Anda Yakin ?");

                myAlertBuilder.setPositiveButton("Ya", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {// User cancelled the dialog.
                                // You have to get the position like this, you can't hold a reference
                                deleteRiwayatPemesanan(id);
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
        return pesan.size();
    }


    private boolean deleteRiwayatPemesanan(String id){
        DatabaseReference menu = FirebaseDatabase.getInstance().getReference("ListPesanan").child(id);

        menu.removeValue();
        Toast.makeText(context.getApplicationContext(), "Riwayatx` Deleted", Toast.LENGTH_SHORT).show();
        return true;
    }

}
